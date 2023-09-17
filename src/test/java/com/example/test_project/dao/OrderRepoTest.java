package com.example.test_project.dao;

import com.example.test_project.builder.ClassBuilder;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource("/application-test.properties")
class OrderRepoTest {

    @Autowired
    private OrderRepo repo;
    @Autowired
    private ProductRepo productRepo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {
        Order order = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        assertEquals(1, order.getClient());

        assertNotNull(order.getDate());

        assertNotNull(order.getAddress());
    }

    @Test
    void getAll() {
        List<Order> all = repo.findAll();

        assertEquals(4, all.size());

        Order order = all.get(1);
        assertEquals(2, order.getClient());

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
        Order order = repo.findById(3).orElseThrow(() ->
                new ResourceNotFoundException(notFoundId(3)));

        order.setClient(55);
        order.setAddress("г.Москва, ул. Новый Арбат 56");
        repo.save(order);

        Order update = repo.findById(3).orElseThrow(() ->
                new ResourceNotFoundException(notFoundId(3)));

        assertNotNull(update.getAddress());
        assertEquals("г.Москва, ул. Новый Арбат 56", order.getAddress());
        assertEquals(55, update.getClient());
    }

    @Test
    void save() {
        Order order = ClassBuilder.getOrder();
        Product product = productRepo.findById(1).orElseThrow(()->
                new ResourceNotFoundException("1"));

        Order save = repo.save(ClassBuilder.setOrderLineToOrder(order,product));

        Order orderDB = repo.findById(5).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(5)));

        assertEquals(5, save.getId());
        assertNotNull(save);
        assertNotNull(orderDB);
        assertEquals(save, orderDB);
        assertEquals(2,orderDB.getOrdersLine().size());
        assertFalse(orderDB.getOrdersLine().isEmpty());
    }

}