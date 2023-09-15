package com.example.test_project.controllers;

import com.example.test_project.dto.OrderDTO;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.service.OrderService;
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

import java.time.LocalDate;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Order order;

    @MockBean
    private OrderService service;

    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setClient(2);
        order.setDate(LocalDate.now());
        order.setAddress("Тестовый адресс");
    }

    @Test
    void add() throws Exception {
        MockHttpServletRequestBuilder content = post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));

        OrderDTO dto = new OrderDTO();
        dto.setClient(2);
        dto.setDate(order.getDate());
        dto.setAddress("Тестовый адресс");


        Mockito.when(service.saveOrder(dto))
                .thenReturn(order);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
                .andDo(MockMvcResultHandlers.print()
                );
    }

    @Test
    void update() throws Exception {
        order.setId(5);
        order.setAddress("Тестовый адресс 2");
        order.setClient(5);

        MockHttpServletRequestBuilder content = patch("/orders/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(order));
        //для того чтобы проверить что id не меняется при апдейте если его передавать
        Order clone = new Order();
        clone.setId(1);
        clone.setAddress(order.getAddress());
        clone.setDate(order.getDate());
        clone.setClient(order.getClient());

        Mockito.when(service.updateById(order, 1))
                .thenReturn(clone);

        this.mockMvc.perform(content)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(clone.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(order.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.client", CoreMatchers.is(order.getClient())))
                .andDo(MockMvcResultHandlers.print()
                );
    }

    @Test
    void delete() throws Exception {
        MockHttpServletRequestBuilder content = MockMvcRequestBuilders.delete("/orders/1");

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
        Order clone = new Order();
        clone.setId(2);
        clone.setAddress("г.Ростов-на-Дону,ул. Текучева 44");
        clone.setDate(LocalDate.now());
        clone.setClient(5);

        List<Order> list = new ArrayList<>();
        list.add(order);
        list.add(clone);

        Mockito.when(service.getAllOrders())
                .thenReturn(list);

        this.mockMvc.perform(get("/orders/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id", CoreMatchers.is(order.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].address", CoreMatchers.is(order.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].date", CoreMatchers.is(order.getDate().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].id", CoreMatchers.is(clone.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].address", CoreMatchers.is(clone.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].date", CoreMatchers.is(clone.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getOne() throws Exception {

        Mockito.when(service.getById(1))
                .thenReturn(order);

        Mockito.when(service.getById(2))
                .thenThrow(new ResourceNotFoundException(notFoundId()));

        this.mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(order.getId())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address", CoreMatchers.is(order.getAddress())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", CoreMatchers.is(order.getDate().toString())))
                .andDo(MockMvcResultHandlers.print());

        this.mockMvc.perform(get("/orders/2"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode", CoreMatchers.is(404)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andDo(MockMvcResultHandlers.print());
    }

    private String notFoundId() {
        return "Order with Id: " + 2 + " not found";
    }

}