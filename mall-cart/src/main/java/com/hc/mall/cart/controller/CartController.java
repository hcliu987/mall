package com.hc.mall.cart.controller;

import com.hc.mall.cart.service.CartService;
import com.hc.mall.cart.vo.CartItemVo;
import com.hc.mall.cart.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CartController {
    @Autowired
    CartService cartService;
    @GetMapping("/cart.html")
    public String cartListPage(Model model){
        CartVo cartVo= cartService.getCart();
        return  "cartList";
    }

    @GetMapping("/success.html")
    public String addToCart(){
        return "success";
    }

    @RequestMapping("/addCartItem")
    public  String addCartItem(@RequestParam("skuId") Long skuId,@RequestParam("num") Integer num, RedirectAttributes attributes){
        cartService.addCartItem(skuId, num);
        attributes.addAttribute("skuId", skuId);
        return "redirect:http://cart.gulimall.com/addCartItemSuccess";
    }

    @RequestMapping("/addCartItemSuccess")
    public String addCartItemSuccess(@RequestParam("skuId") Long skuId,Model model) {
        CartItemVo cartItemVo = cartService.getCartItem(skuId);
        model.addAttribute("cartItem", cartItemVo);
        return "success";
    }


    @RequestMapping("/checkCart")
    public String checkCart(@RequestParam("isChecked") Integer isChecked,@RequestParam("skuId")Long skuId) {
        cartService.checkCart(skuId, isChecked);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @RequestMapping("/countItem")
    public String changeItemCount(@RequestParam("skuId") Long skuId, @RequestParam("num") Integer num) {
        cartService.changeItemCount(skuId, num);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @RequestMapping("/deleteItem")
    public String deleteItem(@RequestParam("skuId") Long skuId) {
        cartService.deleteItem(skuId);
        return "redirect:http://cart.gulimall.com/cart.html";
    }

    @ResponseBody
    @RequestMapping("/getCheckedItems")
    public List<CartItemVo> getCheckedItems() {
        return cartService.getCheckedItems();

    }
}
