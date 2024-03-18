package com.hc.mall.search.controller;

import com.hc.mall.search.service.MallSearchService;
import com.hc.mall.search.vo.SearchParam;
import com.hc.mall.search.vo.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SearchController {

    @Autowired
    MallSearchService mallSearchService;

    @GetMapping(value = {"/search.html","/"})
    public  String getSearchPage(SearchParam searchParam, Model model, HttpServletRequest request){
        searchParam.set_queryString(request.getQueryString());
        SearchResult result=mallSearchService.getSearchResult(searchParam);
        model.addAttribute("result", result);
        return "search";
    }

}
