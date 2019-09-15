package com.solar.ms.paymentms.model.linepay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LinePayConfirmRequest {
    private BigDecimal amount;
    private String currency;
}
