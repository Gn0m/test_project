package com.example.test_project.controllers;

import com.example.test_project.builder.ClassBuilder;
import com.example.test_project.dto.ProductDTO;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Product;
import com.example.test_project.path.PathURL;
import com.example.test_project.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Product product;
    private ProductDTO productDTO;

    @MockBean
    private ProductService service;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        product = ClassBuilder.getProduct();
        productDTO = ClassBuilder.getProductDTO();
    }

    @Test
    void add() throws Exception {
        MockHttpServletRequestBuilder content = post(PathURL.BASIC_GOODS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product));

        Mockito.when(service.saveProduct(productDTO))
                .thenReturn(product);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(2.1)))
                .andDo(MockMvcResultHandlers.print()
                );
    }

    @Test
    void update() throws Exception {
        product.setName("Исправленный молоток");
        product.setPrice(5.4);

        MockHttpServletRequestBuilder content = patch(PathURL.BASIC_GOODS_URL.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(product));

        Mockito.when(service.updateProduct(product, 1))
                .thenReturn(product);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(product.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(product.getName())))
                .andDo(MockMvcResultHandlers.print()
                );
    }

    @Test
    void delete() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders
                .delete(PathURL.BASIC_GOODS_URL.concat("/1"));

        Map<String, Boolean> status = new HashMap<>();
        status.put("Deleted", Boolean.TRUE);

        Mockito.when(service.deleteById(1))
                .thenReturn(status);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.Deleted").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.Deleted", CoreMatchers.is(Boolean.TRUE)))
                .andDo(MockMvcResultHandlers.print()
                );

        Mockito.verify(service, Mockito.times(1)).deleteById(1);
    }

    @Test
    void getAll() throws Exception {
        Product clone = new Product();
        clone.setId(2);
        clone.setName("Самая новая пила");
        clone.setPrice(101.4);


        List<Product> list = new ArrayList<>();
        list.add(product);
        list.add(clone);

        Mockito.when(service.getAll())
                .thenReturn(list);

        this.mockMvc.perform(get(PathURL.BASIC_GOODS_URL.concat("/all")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", CoreMatchers.is(product.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name", CoreMatchers.is(product.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].price", CoreMatchers.is(product.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", CoreMatchers.is(clone.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name", CoreMatchers.is(clone.getName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].price", CoreMatchers.is(clone.getPrice())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getOne() throws Exception {

        Mockito.when(service.getId(1))
                .thenReturn(product);

        Mockito.when(service.getId(2))
                .thenThrow(new ResourceNotFoundException(notFoundId()));

        this.mockMvc.perform(get(PathURL.BASIC_GOODS_URL.concat("/1")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(product.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", CoreMatchers.is(product.getPrice())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(product.getName())))
                .andDo(MockMvcResultHandlers.print());

        this.mockMvc.perform(get(PathURL.BASIC_GOODS_URL.concat("/2")))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    private String notFoundId() {
        return "Product with Id: " + 2 + " not found";
    }

}