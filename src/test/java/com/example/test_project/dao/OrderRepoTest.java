package com.example.test_project.dao;

import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.model.OrderLine;
import com.example.test_project.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("/application-test.properties")
class OrderRepoTest {

    @Autowired
    private OrderRepo repo;

    @BeforeEach
    void setUp() {
        //изменяются данные первых 3 продуктов которые добавлены в бд из flyway
        Order order1 = new Order();
        order1.setId(1);
        order1.setClient(22);
        order1.setAddress(null);
        order1.setDate(LocalDate.now());
        repo.save(order1);

        Order order2 = new Order();
        order2.setId(2);
        order2.setClient(44);
        Set<OrderLine> lines = new HashSet<>();
        Product product = new Product();
        product.setId(1);
        product.setName("Молоток тестовый");
        product.setPrice(102.4);
        OrderLine orderLine = new OrderLine();
        orderLine.setOrder_id(order2);
        orderLine.setCount(40);
        orderLine.setGoods_id(product);
        orderLine.setId(1);
        order2.setOrdersLine(lines);
        repo.save(order2);

        Order order3 = new Order();
        order3.setId(3);
        order3.setAddress("Тестовая улица");
        order3.setDate(LocalDate.now());
        repo.save(order3);
    }

    @Test
    void getId() {
        Order order = repo.findById(1).orElse(new Order());

        assertEquals(22, order.getClient());

        assertNotNull(order.getDate());

        assertNull(order.getAddress());
    }

    @Test
    void getAll() {
        List<Order> all = repo.findAll();

        assertEquals(4, all.size());

        Order order = all.get(1);
        assertEquals(44, order.getClient());

        boolean empty = all.isEmpty();
        assertFalse(empty);
    }

    @Test
    void delete() {
        Order order = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));
        repo.delete(order);

        Order deleted = repo.findById(1).orElse(null);

        assertNull(deleted);
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }

    @Test
    void update() {
        Order order = new Order();
        order.setId(1);
        order.setClient(55);
        order.setAddress("Тестовый адрес");
        repo.save(order);

        Order order1 = repo.findById(1).orElse(new Order());
        assertNotNull(order1.getAddress());
        assertEquals(55, order1.getClient());
    }

    @Test
    void save() {
        Order order = new Order();
        order.setClient(55);
        order.setAddress("Тестовый адрес");
        order.setDate(LocalDate.now());

        Product product = new Product();
        product.setId(1);
        product.setName("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA");
        product.setPrice(499);

        Set<OrderLine> ordersLine = order.getOrdersLine();
        ordersLine.forEach(item -> {
            item.setId(1);
            item.setCount(20);
            item.setGoods_id(product);
            item.setOrder_id(order);
        });

        Order save = repo.save(order);

        Order orderDB = repo.findById(5).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(5)));

        assertEquals(5, save.getId());
        assertNotNull(save);
        assertNotNull(orderDB);
        assertEquals(save, orderDB);
    }

}