package com.hunt.springeshop.service;

import com.hunt.springeshop.domain.Product;
import com.hunt.springeshop.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getAll();

    Product findByTitle(String title);
    void addToUserBucket(Long productId, String username);
    void removeToUserBucket(Long productId, String username);
    void addProduct(ProductDTO dto);
}
