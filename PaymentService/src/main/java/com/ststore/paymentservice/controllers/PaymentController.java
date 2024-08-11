package com.ststore.paymentservice.controllers;

import com.ststore.paymentservice.dtos.InitiatePaymentDto;
import com.ststore.paymentservice.services.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    @PostMapping
    public String initiatePayment(@RequestBody InitiatePaymentDto initiatePaymentDto){
        return paymentService.inititatePayment(initiatePaymentDto.getAmount(), initiatePaymentDto.getName(), initiatePaymentDto.getPhone(), initiatePaymentDto.getOrderId());
    }
}
