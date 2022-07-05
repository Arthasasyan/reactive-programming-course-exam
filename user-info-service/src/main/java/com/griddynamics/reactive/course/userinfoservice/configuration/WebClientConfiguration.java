package com.griddynamics.reactive.course.userinfoservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class WebClientConfiguration {
    public static final String ORDER_SEARCH_WEBCLIENT = "orderSearchWebClient";

    public static final String PRODUCT_INFO_WEBCLIENT = "productInfoWebClient";

    @Value("${orderSearch.baseUrl}")
    public String orderSearchBaseUrl;

    @Value("${productInfo.baseUrl}")
    public String productInfoBaseUrl;

    @Bean(ORDER_SEARCH_WEBCLIENT)
    public WebClient orderSearchWebClient() {
        return WebClient.builder()
                .baseUrl(orderSearchBaseUrl)
                .build();
    }

    @Bean(PRODUCT_INFO_WEBCLIENT)
    public WebClient productInfoWebClient() {
        final HttpClient client = HttpClient.create()
                .responseTimeout(Duration.of(5, ChronoUnit.SECONDS));
        return WebClient.builder()
                .baseUrl(productInfoBaseUrl)
                .clientConnector(new ReactorClientHttpConnector(client))
                .build();
    }
}
