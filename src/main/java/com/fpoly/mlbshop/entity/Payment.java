package com.fpoly.mlbshop.entity;

import lombok.Builder;
import lombok.ToString;

@ToString
public abstract class Payment {
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
