package com.example.test_project.dao;

import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.model.OrderLine;
import com.example.test_project.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepoTest {

    @Autowired
    private ProductRepo repo;

    @BeforeEach
    void setUp() {
        //изменяются данные первых 3 продуктов которые добавлены в бд из flyway
        Product product1 = new Product();
        product1.setId(1);
        product1.setName("Тестовый продукт 1");
        product1.setPrice(1000.12);
        repo.save(product1);

        Product product2 = new Product();
        product2.setId(2);
        product2.setName("Тестовый продукт 2");
        product2.setPrice(999.15);
        repo.save(product2);

        Product product3 = new Product();
        product3.setId(3);
        product3.setName("Тестовый продукт 3");
        product3.setPrice(1999.02);
        repo.save(product3);
    }

    @Test
    void getId() {
        Product product = repo.findById(1).orElse(new Product());

        assertEquals(1000.12, product.getPrice());

        assertNotNull(product.getName());
    }

    @Test
    void getAll() {
        List<Product> all = repo.findAll();

        assertEquals(4, all.size());

        Product product1 = all.get(1);
        assertEquals(999.15, product1.getPrice());

        Product product4 = all.get(3);
        assertEquals(87, product4.getPrice());

        boolean empty = all.isEmpty();
        assertFalse(empty);
    }

    @Test
    void delete() {
        Product product = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        repo.delete(product);

        Product deleted = repo.findById(1).orElse(null);

        assertNull(deleted);
    }

    private String notFoundId(int id) {
        return "Product with Id: " + id + " not found";
    }

    @Test
    void update() {
        Product product = new Product();
        product.setId(1);
        product.setPrice(55);
        product.setName("Тестовый товар измененный");
        repo.save(product);

        Product product1 = repo.findById(1).orElse(new Product());
        assertNotNull(product1.getName());
        assertEquals(55, product1.getPrice());
    }

    @Test
    void save() {
        Order order = new Order();
        order.setClient(55);
        order.setAddress("Тестовый адрес");
        order.setDate(LocalDate.now());

        Product product = new Product();
        product.setName("Новый продукт");
        product.setPrice(999);
        Set<OrderLine> ordersLine = order.getOrdersLine();
        ordersLine.forEach(item -> {
            item.setId(1);
            item.setCount(20);
            item.setGoods_id(product);
            item.setOrder_id(order);
        });

        Product save = repo.save(product);

        Product productDB = repo.findById(5).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(5)));

        assertEquals(5, save.getId());
        assertNotNull(save);
        assertNotNull(productDB);
        assertEquals(save, productDB);
    }

}