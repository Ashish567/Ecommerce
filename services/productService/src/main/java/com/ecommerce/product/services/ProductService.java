package com.ecommerce.product.services;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.ecommerce.product.models.Product;
import com.ecommerce.product.models.Categories;
import com.ecommerce.product.dtos.ProductSearchCriteria;
import com.ecommerce.product.dtos.ProductDto;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.repositories.ProductRepository;

@Service("productService")
public class ProductService {
    @Value("${spring.kafka.topic-1}")
    private String topic1NameD;

    @Value("${spring.kafka.topic-2}")
    private String topic2NameCU;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final KafkaProducerService kafkaProducerService;

    ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, KafkaProducerService kafkaProducerService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public <idd> void sendProductToSearch(String topic, ProductDto productDetails) {
        System.out.println(productDetails);
        if (topic.equals("product-to-search-topic-d")) {
            kafkaProducerService.sendMessageD(topic, productDetails);
        } else {
            kafkaProducerService.sendMessageCU(topic, productDetails);
        }
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
        sendProductToSearch(topic2NameCU, product.toDto());
        return productRepository.save(product);
    }
    public void deleteProduct(Long id) {
        sendProductToSearch(topic1NameD, new ProductDto(id));
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
        sendProductToSearch(topic2NameCU, existingProduct.toDto());
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
