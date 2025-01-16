package com.ecommerce.product.services;


import com.ecommerce.product.dtos.ProductDto;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.repositories.ProductRepository;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.kafka.core.KafkaTemplate;



import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.product.models.Product;
import com.ecommerce.product.models.Categories;
import com.ecommerce.product.repositories.ProductRepository;
import com.ecommerce.product.dtos.ProductSearchCriteria;

@Service("productService")
public class ProductService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    private static final String TOPIC = "product-to-search";
    public void sendProductToSearch(String productDetails) {
        kafkaTemplate.send(TOPIC, productDetails);
    }


    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    @Transactional
    public Product saveProduct(ProductDto productDto) {
        Product product = productDto.toEntity();
        Set<Categories> categories = productDto.getCategoryIds()
                .stream()
                .map(categoryId -> categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new RuntimeException("Category not found: " + categoryId)))
                .collect(Collectors.toSet());
        product.setCategories(categories); // Ensure the product entity is managed and its state is refreshed
        if (product.getId() != null) {
            Product existingProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + product.getId()));
            product.setVersion(existingProduct.getVersion()); }
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional
    public Product updateProduct(Long id, Product product) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setSku(product.getSku());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setActive(product.isActive());
        existingProduct.setPrice(product.getPrice());
        return productRepository.save(existingProduct);
    }

    public Page<Product> findProductsWithFilters(ProductSearchCriteria criteria, Pageable pageable) {
        Specification<Product> spec = buildSpecification(criteria);
        return productRepository.findAll(spec, pageable);
    }
    private Specification<Product> buildSpecification(final ProductSearchCriteria criteria) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();

                if (criteria.getCategories() != null && !criteria.getCategories().isEmpty()) {
                    Join<Product, Categories> categoryJoin = root.join("categories");
                    predicates.add(categoryJoin.get("name").in(criteria.getCategories()));
                }

                if (criteria.getBrand() != null && !criteria.getBrand().isEmpty()) {
                    predicates.add(cb.equal(root.get("brand"), criteria.getBrand()));
                }

                if (criteria.getMinPrice() != null) {
                    predicates.add(cb.greaterThanOrEqualTo(root.get("price"), criteria.getMinPrice()));
                }

                if (criteria.getMaxPrice() != null) {
                    predicates.add(cb.lessThanOrEqualTo(root.get("price"), criteria.getMaxPrice()));
                }

                if (criteria.getIsActive() != null) {
                    predicates.add(cb.equal(root.get("isActive"), criteria.getIsActive()));
                }

                return predicates.isEmpty() ? null : cb.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
