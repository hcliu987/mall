package com.hc.mall.product.web;

import com.hc.mall.product.entity.CategoryEntity;
import com.hc.mall.product.service.CategoryService;
import com.hc.mall.product.vo.Catalog2Vo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;


    @GetMapping({"/","index.html"})
    public  String indexPage(Model model){

        //参数是springMvc提供的接口，Model类，给这个类的对象里放的数据就会存到页面请求域中
        // TODO 1、查出所有1级分类
        List<CategoryEntity> categoryEntitys =  categoryService.getLevel1Categorys();
        model.addAttribute("categories",categoryEntitys);
        //视图解析器进行拼串，前缀classpath:/templates/返回值.html
        return "index";//相当于return "classpath:/templates/index.html"; 拦截GetMapping路径后转到首页
    }

    @GetMapping("/index/json/catelog.json")
    @ResponseBody
    public Map<String,List<Catalog2Vo>> getCategoryMap(){
        return categoryService.getCatelogJson();
    }
}
