package com.griddynamics.reactive.course.userinfoservice.service;

import com.griddynamics.reactive.course.userinfoservice.entity.User;
import com.griddynamics.reactive.course.userinfoservice.repository.UserRepository;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
import com.griddynamics.reactive.course.userinfoservice.vo.OrderVo;
import com.griddynamics.reactive.course.userinfoservice.vo.ProductVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserInfoServiceTest {

    private static final String USER_ID = "123";

    private static final String USER_NAME = "John Doe";

    private static final String PHONE_NUMBER = "+79999999999";

    private static final String ORDER_NUMBER = "123";

    private static final String PRODUCT_CODE = "Product Code";

    private static final String PRODUCT_NAME = "Name";

    private static final String PRODUCT_ID = "12345";

    private static final double SCORE = 5;

    @MockBean
    UserRepository userRepository;

    @MockBean
    OrderSearchService orderSearchService;

    @MockBean
    ProductInfoService productInfoService;

    @Autowired
    UserInfoService userInfoService;

    @Test
    public void testFindByUserId() {
        Mockito.when(userRepository.findById(Mockito.eq(USER_ID)))
                .thenReturn(Mono.just(new User(USER_ID, USER_NAME, PHONE_NUMBER)));

        Mockito.when(orderSearchService.findOrdersByPhone(Mockito.eq(PHONE_NUMBER)))
                .thenReturn(Flux.just(OrderVo.newBuilder()
                        .phoneNumber(PHONE_NUMBER)
                        .orderNumber(ORDER_NUMBER)
                        .productCode(PRODUCT_CODE)
                        .build()));

        Mockito.when(productInfoService.findProductsByCode(Mockito.eq(PRODUCT_CODE)))
                .thenReturn(Flux.just(ProductVo.newBuilder()
                        .productCode(PRODUCT_CODE)
                        .productName(PRODUCT_NAME)
                        .score(SCORE)
                        .productId(PRODUCT_ID)
                        .build()));

        StepVerifier.create(userInfoService.findUserInfoByUserId(USER_ID).contextWrite(it -> it.put(MDCUtil.REQUEST_ID_MDC_VALUE, "123")))
                .expectNextCount(1)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .consumeRecordedWith(collection -> collection.forEach(it -> {
                    assertEquals(USER_NAME, it.getUserName());
                    assertEquals(PHONE_NUMBER, it.getPhoneNumber());
                    assertEquals(ORDER_NUMBER, it.getOrderNumber());
                    assertEquals(PRODUCT_CODE, it.getProductCode());
                    assertEquals(PRODUCT_NAME, it.getProductName());
                    assertEquals(PRODUCT_ID, it.getProductId());
                }))
                .verifyComplete();
    }
}
