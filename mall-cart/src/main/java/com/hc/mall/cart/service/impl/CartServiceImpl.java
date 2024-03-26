package com.hc.mall.cart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.hc.mall.cart.feign.ProductFeignService;
import com.hc.mall.cart.interceptor.CartInterceptor;
import com.hc.mall.cart.service.CartService;
import com.hc.mall.cart.to.UserInfoTo;
import com.hc.mall.cart.vo.CartItemVo;
import com.hc.mall.cart.vo.CartVo;
import com.hc.mall.cart.vo.SkuInfoVo;
import com.hc.mall.common.constant.CartConstant;
import com.hc.mall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProductFeignService productFeignService;
    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public CartVo getCart() {
        CartVo cartVo = new CartVo();
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        //用户登陆，通过user-key获取临时数据库
        List<CartItemVo> tempCart = getCartKey(userInfoTo.getUserKey());
        if (StringUtils.isEmpty(userInfoTo.getUserId())) {
            List<CartItemVo> cartItemVos = tempCart;
            cartVo.setItems(cartItemVos);
        } else {
            //2 用户登录
            //2.1 查询userId对应的购物车
            List<CartItemVo> userCart = getCartKey(userInfoTo.getUserId().toString());
            //2.2 查询user-key对应的临时购物车，并和用户购物车合并
            if (tempCart != null && tempCart.size() > 0) {
                BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(CartConstant.CART_PREFIX + userInfoTo.getUserId());
                for (CartItemVo cartItemVo : tempCart) {
                    userCart.add(cartItemVo);
                    //2.3 在redis中更新数据
                    addCartItem(cartItemVo.getSkuId(), cartItemVo.getCount());
                }
            }
            cartVo.setItems(userCart);
            //2.4 删除临时购物车数据
            stringRedisTemplate.delete(CartConstant.CART_PREFIX + userInfoTo.getUserKey());
        }
        return cartVo;
    }

    @Override
    public CartItemVo addCartItem(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> ops = getCartItemOps();
        String cartJson = (String) ops.get(skuId.toString());
        // 1 已经存在购物车，将数据取出并添加商品数量
        if (!StringUtils.isEmpty(cartJson)) {
            //1.1 将json转为对象并将count+
            CartItemVo cartItemVo = JSON.parseObject(cartJson, CartItemVo.class);
            cartItemVo.setCount(cartItemVo.getCount() + num);
            //1.2 将更新后的对象转为json并存入redis

            String jsonString = JSON.toJSONString(cartItemVo);
            ops.put(skuId.toString(), jsonString);
            return cartItemVo;
        } else {
            CartItemVo cartItemVo = new CartItemVo();
            // 2 未存在购物车，则添加新商品
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                // 2.1 查询sku信息
                R info = productFeignService.info(skuId);
                if (info.getCode() == 0) {
                    SkuInfoVo skuInfo = info.getData("skuInfo", new TypeReference<SkuInfoVo>() {
                    });
                    cartItemVo.setCheck(true);
                    cartItemVo.setCount(num);
                    cartItemVo.setImage(skuInfo.getSkuDefaultImg());
                    cartItemVo.setPrice(skuInfo.getPrice());
                    cartItemVo.setSkuId(skuId);
                    cartItemVo.setTitle(skuInfo.getSkuTitle());
                }
            }, executor);


            //2.2 远程查询sku属性组合信息
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                List<String> attrValuesAsString = productFeignService.getSkuSaleAttrValuesAsString(skuId);
                cartItemVo.setSkuAttrValues(attrValuesAsString);
            }, executor);
            try {
                CompletableFuture.allOf(future1, future).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            //2.3 将该属性封装并存入redis,登录用户使用userId为key,否则使用user-key
            String jsonString = JSON.toJSONString(cartItemVo);
            ops.put(skuId.toString(), jsonString);
            return cartItemVo;
        }
    }

    @Override
    public CartItemVo getCartItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getCartItemOps();
        String s = (String) ops.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(s, CartItemVo.class);
        return cartItemVo;

    }

    @Override
    public void checkCart(Long skuId, Integer isChecked) {
        BoundHashOperations<String, Object, Object> ops = getCartItemOps();
        String cartJson = (String) ops.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(cartJson, CartItemVo.class);
        cartItemVo.setCheck(isChecked==1);
        ops.put(skuId.toString(),JSON.toJSONString(cartItemVo));
    }

    @Override
    public void changeItemCount(Long skuId, Integer num) {
        BoundHashOperations<String, Object, Object> ops = getCartItemOps();
        String cartJson = (String) ops.get(skuId.toString());
        CartItemVo cartItemVo = JSON.parseObject(cartJson, CartItemVo.class);
        cartItemVo.setCount(num);
        ops.put(skuId.toString(),JSON.toJSONString(cartItemVo));
    }

    @Override
    public void deleteItem(Long skuId) {
        BoundHashOperations<String, Object, Object> ops = getCartItemOps();
        ops.delete(skuId.toString());
    }

    @Override
    public List<CartItemVo> getCheckedItems() {
        return null;
    }


    private List<CartItemVo> getCartKey(String userKey) {
        BoundHashOperations<String, Object, Object> ops = stringRedisTemplate.boundHashOps(CartConstant.CART_PREFIX + userKey);
        List<Object> values = ops.values();
        if (values != null && values.size() > 0) {
            List<CartItemVo> collect = values.stream().map(obj -> {
                String json = (String) obj;
                return JSON.parseObject(json, CartItemVo.class);
            }).collect(Collectors.toList());
            return collect;
        }
        return null;

    }

    private BoundHashOperations<String, Object, Object> getCartItemOps() {
        //判断是否已经登陆
        UserInfoTo userInfoTo = CartInterceptor.threadLocal.get();
        if (!StringUtils.isEmpty(userInfoTo.getUserId())) {
            return stringRedisTemplate.boundHashOps(CartConstant.CART_PREFIX + userInfoTo.getUserId());
        } else {
            //1.2 未登录使用user-key操作redis
            return stringRedisTemplate.boundHashOps(CartConstant.CART_PREFIX + userInfoTo.getUserKey());
        }
    }

}