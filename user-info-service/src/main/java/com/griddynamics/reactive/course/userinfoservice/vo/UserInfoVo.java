package com.griddynamics.reactive.course.userinfoservice.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoVo {
    private final String orderNumber;
    private final String userName;
    private final String phoneNumber;
    private final String productCode;
    private final String productName;
    private final String productId;

    @JsonCreator
    private UserInfoVo(@JsonProperty("orderNumber") String orderNumber,
                       @JsonProperty("userName") String userName,
                       @JsonProperty("phoneNumber") String phoneNumber,
                       @JsonProperty("productCode") String productCode,
                       @JsonProperty("productName") String productName,
                       @JsonProperty("productId") String productId) {
        this.orderNumber = orderNumber;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.productCode = productCode;
        this.productName = productName;
        this.productId = productId;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    @JsonGetter("orderNumber")
    public String getOrderNumber() {
        return orderNumber;
    }

    @JsonGetter("userName")
    public String getUserName() {
        return userName;
    }

    @JsonGetter("phoneNumber")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonGetter("productCode")
    public String getProductCode() {
        return productCode;
    }

    @JsonGetter("productName")
    public String getProductName() {
        return productName;
    }

    @JsonGetter("productId")
    public String getProductId() {
        return productId;
    }

    public static final class Builder {
        private String orderNumber;
        private String userName;
        private String phoneNumber;
        private String productCode;
        private String productName;
        private String productId;

        private Builder() {
        }

        public Builder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder productCode(String productCode) {
            this.productCode = productCode;
            return this;
        }

        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public UserInfoVo build() {
            return new UserInfoVo(orderNumber, userName, phoneNumber, productCode, productName, productId);
        }
    }
}
