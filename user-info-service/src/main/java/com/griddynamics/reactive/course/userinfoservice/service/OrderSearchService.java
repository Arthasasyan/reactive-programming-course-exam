package com.griddynamics.reactive.course.userinfoservice.service;

import com.griddynamics.reactive.course.userinfoservice.vo.OrderVo;
import reactor.core.publisher.Flux;

public interface OrderSearchService {
    Flux<OrderVo> findOrdersByPhone(String phoneNumber);
}
