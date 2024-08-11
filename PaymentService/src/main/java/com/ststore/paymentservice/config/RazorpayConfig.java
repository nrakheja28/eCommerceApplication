package com.ststore.paymentservice.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class RazorpayConfig {
    @Value("${razorpay.id}")
    private String razorpayId;

    @Value("${razorpay.secret}")
    private String razorpaySecret;

    @Bean
    public RazorpayClient getRazorpayClient() {
        try {
            return new RazorpayClient(razorpayId, razorpaySecret);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
    }
}
