package com.ecommerce.paymentService.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InitiatePayementDto {
    private String email;
    private String phoneNumber;
    private String orderId;
    private Long amount;
    public String accountTaxId;
    public String purchaseOrder;

    public String getAccountTaxId() {
        return accountTaxId;
    }

    public void setAccountTaxId(String accountTaxId) {
        this.accountTaxId = accountTaxId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}
