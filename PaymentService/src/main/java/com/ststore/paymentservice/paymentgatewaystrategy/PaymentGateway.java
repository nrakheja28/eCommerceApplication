package com.ststore.paymentservice.paymentgatewaystrategy;

public interface PaymentGateway {
    String getPaymentLink(Long amount, String name, String phone, String orderId);
}
