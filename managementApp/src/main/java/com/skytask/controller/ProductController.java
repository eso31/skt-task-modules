package com.skytask.controller;

import com.skytask.common.Product;
import com.skytask.service.RabbitmqService;
import com.skytask.store.ProductStore;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutionException;

@Controller
class ProductController {

    private ProductStore productStore;
    private RabbitmqService rabbitmqService;

    public ProductController(ProductStore productStore, RabbitmqService rabbitmqService) {
        this.productStore = productStore;
        this.rabbitmqService = rabbitmqService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() throws ExecutionException, InterruptedException {
        rabbitmqService.getProductListRabbit();
        return new ModelAndView("index", "products", productStore.getProducts().get());
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String createform() {
        return "create";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(Product product) {
        rabbitmqService.createProductRabbit(product);
        return new ModelAndView("redirect:/");
    }
}
