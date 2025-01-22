package com.ecommerce.userservice.model;

import lombok.Getter;

@Getter
public enum Authority_list {
    VIEWPRODUCTS("View all products"),
    MANAGEPRODUCTS("Add, edit, or delete products"),
    VIEWORDERS("View all orders"),
    MANAGEORDERS("Process and manage orders"),
    VIEWCUSTOMERS("View customer information"),
    MANAGECUSTOMERS("Add, edit, or delete customer information"),
    VIEWCATEGORIES("View product categories"),
    MANAGECATEGORIES("Add, edit, or delete product categories"),
    VIEWREVIEWS("View product reviews"),
    MANAGEREVIEWS("Approve, edit, or delete product reviews"),
    VIEWCOUPONS("View discount coupons"),
    MANAGECOUPONS("Add, edit, or delete discount coupons");

    private final String description;

    Authority_list(String description) {
        this.description = description;
    }

}