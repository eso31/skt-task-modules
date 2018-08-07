package com.skytask.service;

import com.skytask.common.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class ProductServiceImpl implements ProductService {

    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
