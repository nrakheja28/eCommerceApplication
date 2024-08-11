package com.ststore.paymentservice.paymentgatewaystrategy;

import com.razorpay.PaymentLink;
import org.json.JSONObject;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RazorpayPaymentGateway implements PaymentGateway {

    @Autowired
    private RazorpayClient razorpayClient;

    @Override
    public String getPaymentLink(Long amount, String name, String phone, String orderId) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",true);
        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",1722445836);
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for policy no #23456");
        JSONObject customer = new JSONObject();
        customer.put("name",name);
        customer.put("contact",phone);
        customer.put("email","gaurav.kumar@example.com");
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("notify",notify);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Jeevan Bima");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://example-callback-url.com/");
        paymentLinkRequest.put("callback_method","get");
        PaymentLink payment =null;
        try {
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        } catch (RazorpayException e) {
            throw new RuntimeException(e);
        }
        return payment.get("short_url").toString();
    }
}
