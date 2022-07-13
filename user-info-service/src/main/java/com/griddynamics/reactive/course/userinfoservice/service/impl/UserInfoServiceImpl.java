package com.griddynamics.reactive.course.userinfoservice.service.impl;

import com.griddynamics.reactive.course.userinfoservice.repository.UserRepository;
import com.griddynamics.reactive.course.userinfoservice.service.OrderSearchService;
import com.griddynamics.reactive.course.userinfoservice.service.ProductInfoService;
import com.griddynamics.reactive.course.userinfoservice.service.UserInfoService;
import com.griddynamics.reactive.course.userinfoservice.vo.UserInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;

    private final OrderSearchService orderSearchService;

    private final ProductInfoService productInfoService;


    @Autowired
    public UserInfoServiceImpl(UserRepository userRepository, OrderSearchService orderSearchService, ProductInfoService productInfoService) {
        this.userRepository = userRepository;
        this.orderSearchService = orderSearchService;
        this.productInfoService = productInfoService;
    }

    @Override
    public Flux<UserInfoVo> findUserInfoByUserId(String userId) {
        return userRepository.findById(userId)
                .flatMapMany(user -> orderSearchService.findOrdersByPhone(user.getPhone())
                        .map(order -> UserInfoVo.newBuilder()
                                .orderNumber(order.getOrderNumber())
                                .productCode(order.getProductCode())
                                .phoneNumber(user.getPhone())
                                .userName(user.getName())
                                .build()
                        ))
                .flatMap(userInfo -> productInfoService.findProductsByCode(userInfo.getProductCode())
                        .reduce((o1, o2) -> o1.getScore() > o2.getScore() ? o1 : o2)
                        .flatMap(product -> Mono.just(UserInfoVo.newBuilder()
                                .orderNumber(userInfo.getOrderNumber())
                                .productCode(userInfo.getProductCode())
                                .phoneNumber(userInfo.getPhoneNumber())
                                .userName(userInfo.getUserName())
                                .productId(product.getProductId())
                                .productName(product.getProductName())
                                .build())
                        )
                        .switchIfEmpty(Mono.just(userInfo))
                );
    }
}
