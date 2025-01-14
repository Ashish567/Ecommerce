package com.ecommerce.paymentService.paymentgateways;

import com.stripe.StripeClient;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class StripepayPG implements PaymentGateway{
    private StripeClient stripeClient;

    public StripepayPG(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @Override
    public String generatePaymentLink(String email, String phoneNumber, Long amount, String orderId) throws StripeException {
//        JSONObject paymentLinkRequest = new JSONObject();
//        paymentLinkRequest.put("amount",amount);
//        paymentLinkRequest.put("currency","INR");
//        paymentLinkRequest.put("accept_partial",true);
//        paymentLinkRequest.put("expire_by",1735020526);
//        paymentLinkRequest.put("reference_id",orderId);
//        paymentLinkRequest.put("description","Payment for order no " + orderId);
//        JSONObject customer = new JSONObject();
//        customer.put("name","+918553139866");
//        customer.put("contact","Nikhil Jain");
//        customer.put("email","nikhil.jain_1@scaler.com");
//        paymentLinkRequest.put("customer",customer);
//        JSONObject notify = new JSONObject();
//        notify.put("sms",true);
//        notify.put("email",true);
//        paymentLinkRequest.put("notify",notify);
//        paymentLinkRequest.put("reminder_enable",false);
//        JSONObject notes = new JSONObject();
//        notes.put("Notes","Payment for your Amazon Order");
//        paymentLinkRequest.put("notes",notes);
//        paymentLinkRequest.put("callback_url","https://www.scaler.com");
//        paymentLinkRequest.put("callback_method","get");
        CustomerCreateParams customerParams =
                CustomerCreateParams.builder()
                        .setEmail(email)
                        .setPhone(phoneNumber)
                        .build();
        Customer customer = Customer.create(customerParams);
        PriceCreateParams params =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(amount)
                        .setProduct(orderId)
                        .build();

        Price price = Price.create(params);

        PaymentLinkCreateParams param =
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
                                                        .setUrl("https://example.com")
                                                        .build()
                                        )
                                        .build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(param);

        return paymentLink.getUrl();
    }
}
