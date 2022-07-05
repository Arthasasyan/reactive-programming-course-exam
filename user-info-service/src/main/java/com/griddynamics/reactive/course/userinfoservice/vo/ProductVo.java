package com.griddynamics.reactive.course.userinfoservice.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVo {
    private final String productId;
    private final String productCode;
    private final String productName;
    private final double score;

    @JsonCreator
    private ProductVo(@JsonProperty("productId") String productId,
                      @JsonProperty("productCode") String productCode,
                      @JsonProperty("productName") String productName,
                      @JsonProperty("score") double score) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.score = score;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public double getScore() {
        return score;
    }

    public static final class Builder {
        private String productId;
        private String productCode;
        private String productName;
        private double score;

        private Builder() {
        }

        public Builder productId(String productId) {
            this.productId = productId;
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

        public Builder score(double score) {
            this.score = score;
            return this;
        }

        public ProductVo build() {
            return new ProductVo(productId, productCode, productName, score);
        }
    }
}
