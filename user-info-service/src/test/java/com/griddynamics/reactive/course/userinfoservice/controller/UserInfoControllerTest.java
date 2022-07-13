package com.griddynamics.reactive.course.userinfoservice.controller;

import com.griddynamics.reactive.course.userinfoservice.service.UserInfoService;
import com.griddynamics.reactive.course.userinfoservice.vo.UserInfoVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWebTestClient
@DirtiesContext
public class UserInfoControllerTest {

    private static final String USER_ID = "123";

    private static final String USER_NAME = "John Doe";

    private static final String PHONE_NUMBER = "+79999999999";

    private static final String ORDER_NUMBER = "123";

    private static final String PRODUCT_CODE = "Product Code";

    private static final String PRODUCT_NAME = "Name";

    private static final String PRODUCT_ID = "12345";

    private static final double SCORE = 5;

    @MockBean
    UserInfoService service;

    @Autowired
    UserInfoController controller;

    @Autowired
    WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        Mockito.when(service.findUserInfoByUserId(Mockito.eq(USER_ID))).thenReturn(
                Flux.just(UserInfoVo.newBuilder()
                        .userName(USER_NAME)
                        .productId(PRODUCT_ID)
                        .orderNumber(ORDER_NUMBER)
                        .productCode(PRODUCT_CODE)
                        .phoneNumber(PHONE_NUMBER)
                        .productName(PRODUCT_NAME)
                        .build())
        );
    }

    @Test
    public void getUserInfo() {
        StepVerifier.create(webTestClient.get()
                        .uri(builder -> builder
                                .path("/userInfoService/userInfo/userId")
                                .queryParam("userId", "123")
                                .build())
                        .accept(MediaType.APPLICATION_NDJSON)
                        .header("requestId", "123")
                        .exchange()
                        .expectStatus().isOk()
                        .expectHeader().contentType(MediaType.APPLICATION_NDJSON_VALUE)
                        .returnResult(UserInfoVo.class)
                        .getResponseBody())
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
