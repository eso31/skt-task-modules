package com.skytask.controller;

import com.skytask.common.Product;
import com.skytask.service.ProductServiceManagement;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
class ProductController {

    private ProductServiceManagement productServiceManagement;

    public ProductController(ProductServiceManagement productServiceManagement) {
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
