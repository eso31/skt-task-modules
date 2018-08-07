package com.skytask.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public class ProductMapper {
    public List<Product> json2ProductList(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, Product.class);
        List<Product> productList = mapper.readValue(json, type);
        return productList;
    }

    public String productList2Json(List<Product> products) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(products);
    }
}
