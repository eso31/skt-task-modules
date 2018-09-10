package com.skytask.controller;

import com.skytask.common.Product;
import com.skytask.service.ProductServiceManagement;
import org.springframework.amqp.AmqpException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Controller
class ProductController {

    private ProductServiceManagement productServiceManagement;

    public ProductController(ProductServiceManagement productServiceManagement) {
        this.productServiceManagement = productServiceManagement;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView list() throws IOException {
        List<Product> productList;
        try {
            productList = productServiceManagement.getProductListRabbit();
        } catch (AmqpException amqpException) {
            return new ModelAndView("error", "errors", Collections.singletonList("Failed to connect with rabbitmq"));
        }
        return new ModelAndView("index", "products", productList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView createform() {
        return new ModelAndView("create");
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView create(@Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("error", "errors",
                    bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toArray());
        }

        try {
            productServiceManagement.createProductRabbit(product);
        } catch (IllegalArgumentException e) {
            return new ModelAndView("error", "errors", Collections.singletonList("Product can not be null"));
        } catch (AmqpException amqpException) {
            return new ModelAndView("error", "errors", Collections.singletonList("Failed to connect with rabbitmq"));
        }
        return new ModelAndView("redirect:/");
    }
}
