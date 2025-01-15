package com.ecommerce.userservice.model;

import lombok.Getter;

@Getter
public enum Role_list {
    ROLE_USER("Basic user role with limited access"),
    ROLE_ADMIN("Administrator with full access to all features"),
    ROLE_MANAGER("Manager with access to manage orders and products"),
    ROLE_EDITOR("Editor with permissions to edit content and products"),
    ROLE_VIEWER("Viewer with read-only access to data"),
    ROLE_SUPPORT("Support role with access to customer service tools"),
    ROLE_VENDOR("Vendor role with access to manage their own products");

    private final String description;

    Role_list(String description) {
        this.description = description;
    }

}



