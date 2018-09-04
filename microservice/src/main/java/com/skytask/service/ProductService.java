package com.skytask.service;

import com.skytask.common.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProducts();

    Long create(Product product);

}
