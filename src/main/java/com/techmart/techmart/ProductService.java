package com.techmart.techmart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    public Product updateProduct(int id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    public List<Product> searchProducts(String keyword) {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }
    
    public List<Product> searchProductsByCategory(String keyword, String category) {
        return productRepository.findByNameContainingIgnoreCaseAndCategory(keyword, category);
    }

    // NEW METHODS

    public List<Product> getProductsByPriceAsc() {
        return productRepository.findAllByOrderByPriceAsc();
    }

    public List<Product> getProductsByPriceDesc() {
        return productRepository.findAllByOrderByPriceDesc();
    }

    public List<Product> getProductsByNameAsc() {
        return productRepository.findAllByOrderByNameAsc();
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public List<Product> getProductsByCategoryPriceAsc(String category) {
        return productRepository.findByCategoryOrderByPriceAsc(category);
    }

    public List<Product> getProductsByCategoryPriceDesc(String category) {
        return productRepository.findByCategoryOrderByPriceDesc(category);
    }

    public List<Product> getProductsByCategoryNameAsc(String category) {
        return productRepository.findByCategoryOrderByNameAsc(category);
    }
    
    public Page<Product> getProductsByPage(int page) {

        return productRepository.findAll(PageRequest.of(page, 6));

    }

}