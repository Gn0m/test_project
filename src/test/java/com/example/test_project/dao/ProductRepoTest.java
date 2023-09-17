package com.example.test_project.dao;

import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepoTest {

    @Autowired
    private ProductRepo repo;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getId() {
        Product product = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        assertEquals(499, product.getPrice());
        assertNotNull(product.getName());
    }

    @Test
    void getAll() {
        List<Product> all = repo.findAll();

        assertEquals(4, all.size());

        Product product = all.get(1);
        assertEquals("Гвоздодер шестигранный крашеный 600мм", product.getName());

        Product product3 = all.get(3);
        assertEquals(87, product3.getPrice());

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
        Product product = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        product.setPrice(200);
        product.setName("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA измененный");
        repo.save(product);

        Product updated = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        assertNotNull(updated.getName());
        assertEquals(200, updated.getPrice());
        assertEquals("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA измененный", updated.getName());
    }

    @Test
    void save() {
        Product product = new Product();
        product.setName("Новый продукт");
        product.setPrice(999);

        Product save = repo.save(product);

        Product productDB = repo.findById(5).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(5)));

        assertEquals(5, save.getId());
        assertNotNull(save);
        assertNotNull(productDB);
        assertEquals(save, productDB);
    }

}