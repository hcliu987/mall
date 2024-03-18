package com.hc.mall.search.service;

import com.hc.mall.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
