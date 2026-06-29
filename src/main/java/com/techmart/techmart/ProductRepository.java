package com.techmart.techmart;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByNameContainingIgnoreCase(String keyword);
    
    List<Product> findByNameContainingIgnoreCaseAndCategory(
            String keyword,
            String category);
    
    List<Product> findAllByOrderByPriceAsc();

    List<Product> findAllByOrderByPriceDesc();

    List<Product> findAllByOrderByNameAsc();
    
    List<Product> findByCategory(String category);

    List<Product> findByCategoryOrderByPriceAsc(String category);

    List<Product> findByCategoryOrderByPriceDesc(String category);

    List<Product> findByCategoryOrderByNameAsc(String category);

}