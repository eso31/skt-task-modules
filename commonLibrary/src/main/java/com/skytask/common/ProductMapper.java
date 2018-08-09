package com.skytask.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.List;

public class ProductMapper {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static List<Product> json2ProductList(String json) throws IOException {
        CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, Product.class);
        List<Product> productList = mapper.readValue(json, type);
        return productList;
    }

    public static String productList2Json(List<Product> products) throws JsonProcessingException {
        return mapper.writeValueAsString(products);
    }
}
