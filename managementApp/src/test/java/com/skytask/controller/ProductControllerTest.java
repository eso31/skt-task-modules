package com.skytask.controller;

import com.skytask.service.ProductServiceManagement;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceManagement productServiceManagement;

    @InjectMocks
    private ProductController productController;

    @Test
    public void CreateProductFormTest() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"));
    }

    @Test
    public void getListTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    public void createTest() throws Exception {
        mockMvc.perform(post("/create")
                .param("description", "description")
                .param("price", "25.0")
                .param("name", "coca")
                .param("stock", "10")
        )
                .andExpect(status().is3xxRedirection());
    }
}