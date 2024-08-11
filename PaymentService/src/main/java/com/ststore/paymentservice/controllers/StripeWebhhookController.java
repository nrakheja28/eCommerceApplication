package com.ststore.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripeWebhook")
public class StripeWebhhookController {

    @PostMapping
    public void respondToEvent(@RequestBody String jsonEvent) {
        System.out.println(jsonEvent);
    }
}
