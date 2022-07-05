package com.griddynamics.reactive.course.userinfoservice.service;

import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import wiremock.org.eclipse.jetty.http.HttpHeader;

import java.util.ArrayList;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureWireMock
public class OrderSearchServiceTest {

    private static final String PHONE_NUMBER = "123";
    private static final String MOCK_RESPONSE = "{\"phoneNumber\":\"123\",\"orderNumber\":\"Order_0\",\"productCode\":\"3852\"}\n" +
            "{\"phoneNumber\":\"123\",\"orderNumber\":\"Order_1\",\"productCode\":\"5256\"}\n" +
            "{\"phoneNumber\":\"123\",\"orderNumber\":\"Order_2\",\"productCode\":\"7894\"}\n" +
            "{\"phoneNumber\":\"123\",\"orderNumber\":\"Order_3\",\"productCode\":\"9822\"}";
    @Autowired
    OrderSearchService searchService;

    @BeforeAll
    public static void setUp() {
        stubFor(get(urlPathEqualTo("/orderSearchService/order/phone"))
                .withQueryParam("phoneNumber", new EqualToPattern(PHONE_NUMBER))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeader.CONTENT_TYPE.asString(), MediaType.APPLICATION_NDJSON_VALUE)
                        .withBody(MOCK_RESPONSE)));
    }

    @Test
    public void testFindByPhoneNumber() {
        StepVerifier.create(searchService.findOrdersByPhone(PHONE_NUMBER).contextWrite(it -> it.put(MDCUtil.REQUEST_ID_MDC_VALUE, "123")))
                .expectNextCount(4)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .expectRecordedMatches(collection -> collection.stream().allMatch(it -> it.getPhoneNumber().equals(PHONE_NUMBER)))
                .verifyComplete();
    }
}
