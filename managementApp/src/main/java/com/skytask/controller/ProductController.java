package com.skytask.controller;

import com.skytask.common.Product;
import com.skytask.service.ProductServiceManagement;
import com.skytask.store.ProductStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
class ProductController {

    private ProductStore productStore;
    private ProductServiceManagement productServiceManagement;

    public ProductController(ProductStore productStore, ProductServiceManagement productServiceManagement) {
        this.productStore = productStore;
        this.productServiceManagement = productServiceManagement;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() throws IOException {
        List<Product> productList = productServiceManagement.getProductListRabbit();
        return new ModelAndView("index", "products", productList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createform() {
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(Product product) {
        productServiceManagement.createProductRabbit(product);
        return "redirect:/";
    }
}
