package com.griddynamics.reactive.course.userinfoservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.reactive.course.userinfoservice.configuration.WebClientConfiguration;
import com.griddynamics.reactive.course.userinfoservice.service.OrderSearchService;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
import com.griddynamics.reactive.course.userinfoservice.vo.OrderVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderSearchServiceImpl implements OrderSearchService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WebClient webClient;

    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier(WebClientConfiguration.ORDER_SEARCH_WEBCLIENT)
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Flux<OrderVo> findOrdersByPhone(String phoneNumber) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("phoneNumber", phoneNumber)
                        .build())
                .exchangeToFlux(response -> {
                    if (response.statusCode().equals(HttpStatus.OK)) {
                        return response.bodyToFlux(OrderVo.class);
                    } else {
                        return response.createException().flatMapMany(Mono::error);
                    }
                })
                .doOnEach(signal -> {
                    if (signal.isOnNext()) {
                        MDCUtil.logWithContext(signal.getContextView(), () -> {
                            try {
                                log.debug("Order received from order service: " + objectMapper.writeValueAsString(signal.get()));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                });
    }
}
