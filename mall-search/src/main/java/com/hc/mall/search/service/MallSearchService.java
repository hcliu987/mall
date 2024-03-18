package com.hc.mall.search.service;

import com.hc.mall.search.vo.SearchParam;
import com.hc.mall.search.vo.SearchResult;

public interface MallSearchService {
    SearchResult getSearchResult(SearchParam searchParam);
}
