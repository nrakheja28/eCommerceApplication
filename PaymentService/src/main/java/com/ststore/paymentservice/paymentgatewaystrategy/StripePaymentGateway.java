package com.ststore.paymentservice.paymentgatewaystrategy;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements PaymentGateway{

    @Value("${stripe.key}")
    private String stripeKey;

    @Override
    public String getPaymentLink(Long amount, String name, String phone, String orderId) {

        Price price = getPrice(amount);
        Stripe.apiKey = this.stripeKey;
        PaymentLinkCreateParams params =
                PaymentLinkCreateParams.builder()
                        .addLineItem(
                                PaymentLinkCreateParams.LineItem.builder()
                                        .setPrice(price.getId())
                                        .setQuantity(1L)
                                        .build()
                        )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                            PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                            .setUrl("google.com").build()
                                        )
                                        .build()
                        )
                        .build();
        try {
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }

    private Price getPrice(Long amount){


        Stripe.apiKey = this.stripeKey;

        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("usd")
                        .setUnitAmount(amount)
                        .setRecurring(
                                PriceCreateParams.Recurring.builder()
                                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                        .build()
                        )
                        .setProductData(
                                PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                        )
                        .build();
        try {
            return Price.create(params);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
}
