package com.hc.mall.cart.service;

import com.hc.mall.cart.vo.CartItemVo;
import com.hc.mall.cart.vo.CartVo;

import java.util.List;

public interface CartService {
    CartVo getCart();

    CartItemVo addCartItem(Long skuId, Integer num);

    CartItemVo getCartItem(Long skuId);

    void checkCart(Long skuId, Integer isChecked);

    void changeItemCount(Long skuId, Integer num);

    void deleteItem(Long skuId);

    List<CartItemVo> getCheckedItems();
}
