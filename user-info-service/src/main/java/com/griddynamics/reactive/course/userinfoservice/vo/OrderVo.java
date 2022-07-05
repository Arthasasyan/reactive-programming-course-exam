package com.griddynamics.reactive.course.userinfoservice.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderVo {
    private final String phoneNumber;
    private final String orderNumber;
    private final String productCode;

    @JsonCreator
    private OrderVo(@JsonProperty("phoneNumber") String phoneNumber,
                    @JsonProperty("orderNumber") String orderNumber,
                    @JsonProperty("productCode") String productCode) {
        this.phoneNumber = phoneNumber;
        this.orderNumber = orderNumber;
        this.productCode = productCode;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProductCode() {
        return productCode;
    }

    public static final class Builder {
        private String phoneNumber;
        private String orderNumber;
        private String productCode;

        private Builder() {
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder productCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public OrderVo build() {
            return new OrderVo(phoneNumber, orderNumber, productCode);
        }
    }
}
