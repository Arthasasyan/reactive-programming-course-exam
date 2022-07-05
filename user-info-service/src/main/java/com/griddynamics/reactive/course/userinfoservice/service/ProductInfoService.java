package com.griddynamics.reactive.course.userinfoservice.service;

import com.griddynamics.reactive.course.userinfoservice.vo.ProductVo;
import reactor.core.publisher.Flux;

public interface ProductInfoService {
    Flux<ProductVo> findProductsByCode(String productCode);
}
