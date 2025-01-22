-- Select the database
USE user_service;

-- Run a query
SELECT * FROM user_service;

DROP TABLE `authorities`;
DROP TABLE `usersz`;

CREATE TABLE `usersz` (
                          `user_id` INT NOT NULL AUTO_INCREMENT,
                          `username` VARCHAR(100) NOT NULL,
                          `email` VARCHAR(100) NOT NULL,
                          `roles` VARCHAR(20) NOT NULL,
                          `password` VARCHAR(500) NOT NULL,
                          `deleted` VARCHAR(100) NOT NULL,
                          `created_at` DATE DEFAULT NULL,
                          `updated_at` DATE DEFAULT NULL,
                          `is_email_verified` BOOL DEFAULT FALSE,
                          PRIMARY KEY (`user_id`)
);

INSERT INTO `usersz` (`username`, `email`, `roles`, `password`, `deleted`, `created_at`)
VALUES ('admin','admin@admin.com','ROLE_ADMIN','{bcrypt}$2y$10$xFy3o97Q3I1rC4KTAiESd.o9W5UCSswZnCJNxhGEdQkb6hAfLMNLW','false',CURDATE());


CREATE TABLE `authorities` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `user_id` INT NOT NULL,
                               `name` VARCHAR(50) NOT NULL,
                               `deleted` BOOL NOT NULL DEFAULT FALSE,
                               `updated_at` DATE DEFAULT NULL,
                               `created_at` DATE DEFAULT NULL,
                               `description` VARCHAR(100) NOT NULL,
                               PRIMARY KEY (`id`),
                               KEY `user_id` (`user_id`),
                               CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `usersz` (`user_id`)
);
INSERT INTO `authorities` (`user_id`, `name`, `description`)
VALUES
    (1, 'VIEWPRODUCTS', 'View all products'),
    (1, 'MANAGEPRODUCTS', 'Add, edit, or delete products'),
    (1, 'VIEWORDERS', 'View all orders'),
    (1, 'MANAGEORDERS', 'Process and manage orders'),
    (1, 'VIEWCUSTOMERS', 'View customer information'),
    (1, 'MANAGECUSTOMERS', 'Add, edit, or delete customer information'),
    (1, 'VIEWCATEGORIES', 'View product categories'),
    (1, 'MANAGECATEGORIES', 'Add, edit, or delete product categories'),
    (1, 'VIEWREVIEWS', 'View product reviews'),
    (1, 'MANAGEREVIEWS', 'Approve, edit, or delete product reviews'),
    (1, 'VIEWCOUPONS', 'View discount coupons'),
    (1, 'MANAGECOUPONS', 'Add, edit, or delete discount coupons');

-- DELETE FROM `authorities`;

-- INSERT INTO `authorities` (`user_id`, `name`, `description`)
-- VALUES
--     (1, 'ROLE_USER', 'Basic user role with limited access'),
--     (1, 'ROLE_ADMIN', 'Administrator with full access to all features'),
--     (1, 'ROLE_MANAGER', 'Manager with access to manage orders and products'),
--     (1, 'ROLE_EDITOR', 'Editor with permissions to edit content and products'),
--     (1, 'ROLE_VIEWER', 'Viewer with read-only access to data'),
--     (1, 'ROLE_SUPPORT', 'Support role with access to customer service tools'),
--     (1, 'ROLE_VENDOR', 'Vendor role with access to manage their own products');


CREATE TABLE roles_list (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       NAME ENUM('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_EDITOR', 'ROLE_VIEWER', 'ROLE_SUPPORT', 'ROLE_VENDOR') NOT NULL,
                       description VARCHAR(255) NOT NULL
);

CREATE TABLE authorities_list (
                                  id INT PRIMARY KEY AUTO_INCREMENT,
                                  NAME ENUM('VIEWPRODUCTS', 'MANAGEPRODUCTS', 'VIEWORDERS', 'MANAGEORDERS', 'VIEWCUSTOMERS', 'MANAGECUSTOMERS', 'VIEWCATEGORIES', 'MANAGECATEGORIES', 'VIEWREVIEWS', 'MANAGEREVIEWS', 'VIEWCOUPONS', 'MANAGECOUPONS') NOT NULL,
                                  description VARCHAR(255) NOT NULL
);

INSERT INTO roles_list (NAME, description) VALUES
                                               ('ROLE_USER', 'Basic user role with limited access'),
                                               ('ROLE_ADMIN', 'Administrator with full access to all features'),
                                               ('ROLE_MANAGER', 'Manager with access to manage orders and products'),
                                               ('ROLE_EDITOR', 'Editor with permissions to edit content and products'),
                                               ('ROLE_VIEWER', 'Viewer with read-only access to data'),
                                               ('ROLE_SUPPORT', 'Support role with access to customer service tools'),
                                               ('ROLE_VENDOR', 'Vendor role with access to manage their own products');

INSERT INTO authorities_list (NAME, description) VALUES
                                                ('VIEWPRODUCTS', 'View all products'),
                                                ('MANAGEPRODUCTS', 'Add, edit, or delete products'),
                                                ('VIEWORDERS', 'View all orders'),
                                                ('MANAGEORDERS', 'Process and manage orders'),
                                                ('VIEWCUSTOMERS', 'View customer information'),
                                                ('MANAGECUSTOMERS', 'Add, edit, or delete customer information'),
                                                ('VIEWCATEGORIES', 'View product categories'),
                                                ('MANAGECATEGORIES', 'Add, edit, or delete product categories'),
                                                ('VIEWREVIEWS', 'View product reviews'),
                                                ('MANAGEREVIEWS', 'Approve, edit, or delete product reviews'),
                                                ('VIEWCOUPONS', 'View discount coupons'),
                                                ('MANAGECOUPONS', 'Add, edit, or delete discount coupons');

