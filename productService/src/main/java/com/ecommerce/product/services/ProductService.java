package com.ecommerce.product.services;

import com.ecommerce.product.dtos.ProductDto;
import com.ecommerce.product.models.Categories;
import com.ecommerce.product.models.Product;
import com.ecommerce.product.repositories.CategoryRepository;
import com.ecommerce.product.repositories.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service("productService")
public class ProductService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
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
        // Convert category IDs to Categories entities
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
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct != null) {
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setSku(product.getSku());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setActive(product.isActive());
            existingProduct.setPrice(product.getPrice());
            return productRepository.save(existingProduct);
        }
        return null;
    }
}
