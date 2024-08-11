package com.ststore.paymentservice.services;

public interface IPaymentService {
    String inititatePayment(Long amount, String name, String phone, String orderId);
}
