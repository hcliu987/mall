package com.hc.mall.product;

import com.hc.mall.product.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MallProductApplicationTests {

    @Autowired
    CategoryService categoryService;
    @Test
    void contextLoads() {
        System.out.println(categoryService.findCatelogPath(225L));
    }

}
