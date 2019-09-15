package com.solar.ms.paymentms.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConfirmPaymentRequest {
    private String transactionId;
    private BigDecimal amount;
}
