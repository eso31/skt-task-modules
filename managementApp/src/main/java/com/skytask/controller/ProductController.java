package com.skytask.controller;

import com.skytask.channel.ProductSource;
import com.skytask.common.Product;
import com.skytask.store.ProductStore;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class ProductController {

    private ProductStore productStore;
    private ProductSource productSource;

    public ProductController(ProductStore productService, ProductSource productSource) {
        this.productStore = productService;
        this.productSource = productSource;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() {
        productSource.getProductList().send(MessageBuilder.withPayload("getProductsList").build());
        return new ModelAndView("index", "products", productStore.getProducts());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createform() {
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Product product) {
        productSource.createProduct().send(MessageBuilder.withPayload(product).build());
        return new ModelAndView("redirect:/");
    }
}
