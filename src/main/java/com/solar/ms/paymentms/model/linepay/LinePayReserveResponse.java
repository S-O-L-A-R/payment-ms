package com.solar.ms.paymentms.model.linepay;

import lombok.Data;

@Data
public class LinePayReserveResponse {
    private String returnCode;
    private String returnMessage;
    private LinePayReserveInfo info;
}
