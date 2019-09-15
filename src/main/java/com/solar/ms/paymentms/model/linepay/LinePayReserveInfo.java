package com.solar.ms.paymentms.model.linepay;

import lombok.Data;

@Data
public class LinePayReserveInfo {
    private LinePayUrl paymentUrl;
    private String transactionId;
    private String paymentAccessToken;
}
