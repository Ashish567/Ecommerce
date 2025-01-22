package com.ecommerce.paymentService.paymentgateways;

import com.ecommerce.paymentService.dtos.InitiatePayementDto;
import com.ecommerce.paymentService.dtos.PaymentResponseDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StripepayPG implements PaymentGateway {
    @Value("${stripe.successUrl")
    static String successUrl;

    @Value("${stripe.footer")
    static String footer;

    @Value("${stripe.currency")
    static String currency;

    private static final String REDIRECT_URL = successUrl;
    private static final String FOOTER = footer;

    @Override
    public PaymentResponseDTO generatePaymentLink(InitiatePayementDto initiatePayementDto) throws StripeException {

        // Create customer
        Customer customer = createCustomer(initiatePayementDto.getEmail(), initiatePayementDto.getPhoneNumber());

        // Create price
        Price price = createPrice(initiatePayementDto.getAmount(), initiatePayementDto.getOrderId());

        // Create payment link
        PaymentLink paymentLink = createPaymentLink(price.getId(), initiatePayementDto.getOrderId(), initiatePayementDto.getAccountTaxId(), initiatePayementDto.getPurchaseOrder());

        // Create response DTO
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentUrl(paymentLink.getUrl());
        response.setCustomerId(customer.getId());
        response.setPriceId(price.getId());
        response.setPaymentLinkId(paymentLink.getId());

        return response;
    }

    private Customer createCustomer(String email, String phoneNumber) throws StripeException {
        CustomerCreateParams customerParams = CustomerCreateParams.builder()
                .setEmail(email)
                .setPhone(phoneNumber)
                .build();
        return Customer.create(customerParams);
    }

    private Price createPrice(Long amount, String orderId) throws StripeException {
        Product product = Product.create(ProductCreateParams.builder()
                .setName(String.valueOf(orderId))
                .build());
        PriceCreateParams params = PriceCreateParams.builder()
                .setCurrency(currency)
                .setUnitAmount(amount)
                .setProduct(product.getId())
                .build();
        return Price.create(params);
    }

    private PaymentLink createPaymentLink(String priceId, String orderId,
                                          String accountTaxId, String purchaseOrder) throws StripeException {
        PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                .addLineItem(
                        PaymentLinkCreateParams.LineItem.builder()
                                .setPrice(priceId)
                                .setQuantity(1L)
                                .build()
                )
                .setAfterCompletion(createAfterCompletionParams())
                .setInvoiceCreation(createInvoiceCreationParams(orderId, accountTaxId, purchaseOrder))
                .build();

        return PaymentLink.create(params);
    }

    private PaymentLinkCreateParams.AfterCompletion createAfterCompletionParams() {
        return PaymentLinkCreateParams.AfterCompletion.builder()
                .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                .setRedirect(
                        PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                .setUrl(REDIRECT_URL)
                                .build()
                )
                .build();
    }

    private PaymentLinkCreateParams.InvoiceCreation createInvoiceCreationParams(
            String orderId, String accountTaxId, String purchaseOrder) {
        return PaymentLinkCreateParams.InvoiceCreation.builder()
                .setEnabled(true)
                .setInvoiceData(
                        PaymentLinkCreateParams.InvoiceCreation.InvoiceData.builder()
                                .setDescription("Invoice for order-" + orderId)
                                .putMetadata("order", orderId)
//                                .addAccountTaxId(accountTaxId)
                                .addCustomField(
                                        PaymentLinkCreateParams.InvoiceCreation.InvoiceData.CustomField.builder()
                                                .setName("Purchase Order")
                                                .setValue(purchaseOrder)
                                                .build()
                                )
                                .setRenderingOptions(
                                        PaymentLinkCreateParams.InvoiceCreation.InvoiceData.RenderingOptions.builder()
                                                .setAmountTaxDisplay(
                                                        PaymentLinkCreateParams.InvoiceCreation.InvoiceData.RenderingOptions.AmountTaxDisplay.INCLUDE_INCLUSIVE_TAX
                                                )
                                                .build()
                                )
                                .setFooter(FOOTER)
                                .build()
                )
                .build();
    }
}