package com.solar.ms.paymentms.model.linepay;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LinePayReserveRequest {
    private String productName;
    private BigDecimal amount;
    private String orderId;
    private String currency;
    private String confirmUrl;
    private String langCd;
    private String confirmUrlType;
    private String productImageUrl;
}
