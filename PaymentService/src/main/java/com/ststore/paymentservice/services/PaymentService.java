package com.ststore.paymentservice.services;

import com.ststore.paymentservice.paymentgatewaystrategy.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;
import com.ststore.paymentservice.paymentgatewaystrategy.PaymantGatewayChooserStrategy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class PaymentService implements IPaymentService {

    @Autowired
    private PaymantGatewayChooserStrategy paymantGatewayChooserStrategy;

    @Override
    public String inititatePayment(Long amount, String name, String phone, String orderId){
        PaymentGateway paymentGateway = paymantGatewayChooserStrategy.getPaymentGateway();
        return paymentGateway.getPaymentLink(amount, name, phone, orderId);
    }
}
