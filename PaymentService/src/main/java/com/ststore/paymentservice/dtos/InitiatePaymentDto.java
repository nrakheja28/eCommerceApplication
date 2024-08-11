package com.ststore.paymentservice.dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePaymentDto {

    private Long amount;
    private String name;
    private String phone;
    private String orderId;

}
