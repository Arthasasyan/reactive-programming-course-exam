package com.griddynamics.reactive.course.userinfoservice.service;

import com.github.tomakehurst.wiremock.matching.EqualToPattern;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
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
public class ProductInfoServiceTest {

    private static final String PRODUCT_CODE = "7894";
    private static final String MOCK_RESPONSE = "[\n" +
            "  {\n" +
            "    \"productId\": \"111\",\n" +
            "    \"productCode\": \"7894\",\n" +
            "    \"productName\": \"IceCream\",\n" +
            "    \"score\": 4197.73\n" +
            "  },\n" +
            "  {\n" +
            "    \"productId\": \"222\",\n" +
            "    \"productCode\": \"7894\",\n" +
            "    \"productName\": \"Milk\",\n" +
            "    \"score\": 1257.95\n" +
            "  },\n" +
            "  {\n" +
            "    \"productId\": \"333\",\n" +
            "    \"productCode\": \"7894\",\n" +
            "    \"productName\": \"Meal\",\n" +
            "    \"score\": 9396.82\n" +
            "  },\n" +
            "  {\n" +
            "    \"productId\": \"444\",\n" +
            "    \"productCode\": \"7894\",\n" +
            "    \"productName\": \"Apple\",\n" +
            "    \"score\": 286.16\n" +
            "  }\n" +
            "]";
    @Autowired
    ProductInfoService productInfoService;

    @Test
    public void testFindByProductCode() {
        stubFor(get(urlPathEqualTo("/productInfoService/product/names"))
                .withQueryParam("productCode", new EqualToPattern(PRODUCT_CODE))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeader.CONTENT_TYPE.asString(), MediaType.APPLICATION_JSON_VALUE)
                        .withBody(MOCK_RESPONSE)));

        StepVerifier.create(productInfoService.findProductsByCode(PRODUCT_CODE).contextWrite(it -> it.put(MDCUtil.REQUEST_ID_MDC_VALUE, "123")))
                .expectNextCount(4)
                .recordWith(ArrayList::new)
                .thenConsumeWhile(x -> true)
                .expectRecordedMatches(collection -> collection.stream().allMatch(it -> it.getProductCode().equals(PRODUCT_CODE)))
                .verifyComplete();
    }

    @Test
    public void testFindByProductCodeTimeout() {
        stubFor(get(urlPathEqualTo("/productInfoService/product/names"))
                .withQueryParam("productCode", new EqualToPattern(PRODUCT_CODE))
                .willReturn(aResponse()
                        .withStatus(408)));

        StepVerifier.create(productInfoService.findProductsByCode(PRODUCT_CODE).contextWrite(it -> it.put(MDCUtil.REQUEST_ID_MDC_VALUE, "123")))
                .expectComplete()
                .verify();
    }
}