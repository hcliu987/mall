package com.hc.mall.order.web;

import com.hc.mall.order.service.OrderService;
import com.hc.mall.order.vo.OrderConfirmVo;
import com.hc.mall.order.vo.OrderSubmitVo;
import com.hc.mall.order.vo.SubmitOrderResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.concurrent.ExecutionException;

@Controller
public class OrderWebController {


    @Autowired
    private OrderService orderService;
    @GetMapping("/toTrade")
    public  String toTrade(Model model) throws ExecutionException, InterruptedException {
        OrderConfirmVo confirmVo =orderService.confirmOrder();
        model.addAttribute("OrderConfirmData    ",confirmVo);
        return "confirm";
    }


    @RequestMapping("/submitOrder")
    public String submitOrder(OrderSubmitVo vo, Model model, RedirectAttributes redirectAttributes){
        SubmitOrderResponseVo responseVo=orderService.submitOrder(vo);
        return null;
    }



}
