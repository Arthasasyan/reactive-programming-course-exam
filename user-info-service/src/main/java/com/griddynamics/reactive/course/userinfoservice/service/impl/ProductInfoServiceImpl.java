package com.griddynamics.reactive.course.userinfoservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.griddynamics.reactive.course.userinfoservice.configuration.WebClientConfiguration;
import com.griddynamics.reactive.course.userinfoservice.service.ProductInfoService;
import com.griddynamics.reactive.course.userinfoservice.util.MDCUtil;
import com.griddynamics.reactive.course.userinfoservice.vo.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private WebClient webClient;

    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier(WebClientConfiguration.PRODUCT_INFO_WEBCLIENT)
    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @Override
    public Flux<ProductVo> findProductsByCode(String productCode) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("productCode", productCode)
                        .build())
                .retrieve()
                .bodyToFlux(ProductVo.class)
                .doOnEach(signal -> {
                    if (signal.isOnNext()) {
                        MDCUtil.logWithContext(signal.getContextView(), () -> {
                            try {
                                log.debug("Product received from product service: " + objectMapper.writeValueAsString(signal.get()));
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }
                })
                .onErrorResume(it -> Flux.empty());
    }

}
