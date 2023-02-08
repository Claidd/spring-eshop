package com.hunt.springeshop.dao;

import com.hunt.springeshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByTitle(String title);
}
