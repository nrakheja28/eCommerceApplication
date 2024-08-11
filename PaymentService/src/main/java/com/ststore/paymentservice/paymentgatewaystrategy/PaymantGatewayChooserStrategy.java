package com.ststore.paymentservice.paymentgatewaystrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymantGatewayChooserStrategy {

    @Autowired
    private RazorpayPaymentGateway razorpayPaymentGateway;

    @Autowired
    private StripePaymentGateway stripePaymentGateway;

    public PaymentGateway getPaymentGateway() {
        Random random = new Random();
        if(random.nextBoolean()){
            return razorpayPaymentGateway;
        }
        else {
            return stripePaymentGateway;
        }
    }

}
