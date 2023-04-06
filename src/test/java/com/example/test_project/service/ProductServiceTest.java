package com.example.test_project.service;

import com.example.test_project.dao.ProductRepo;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceTest {

    @MockBean
    private ProductRepo repo;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(5);
        product.setPrice(20.1);
        product.setName("Тестовый молоток");

    }

    @Test
    void getAll() {
        Product clone = new Product();
        clone.setId(2);
        clone.setPrice(product.getPrice());
        clone.setName("Тестовый молоток 2");


        List<Product> list = new ArrayList<>();
        list.add(product);
        list.add(clone);

        Mockito.when(repo.findAll())
                .thenReturn(list);

        List<Product> all = repo.findAll();

        int id = all.get(1).getId();
        int size = all.size();

        assertEquals(2, size);
        assertEquals(2, id);

        Mockito.verify(repo, Mockito.times(1)).findAll();
    }

    @Test
    void save() {
        Mockito.when(repo.save(product))
                .thenReturn(product);

        Product save = repo.save(product);

        int id = save.getId();
        double price = save.getPrice();
        String name = save.getName();

        assertEquals(5, id);
        assertEquals(20.1, price);
        assertEquals("Тестовый молоток", name);

        Mockito.verify(repo, Mockito.times(1)).save(product);
    }

    @Test
    void update() {
        Product clone = new Product();
        clone.setId(5);
        clone.setName("Тестовый молоток 2");
        clone.setPrice(55.1);

        product.setName(clone.getName());
        product.setPrice(clone.getPrice());

        Mockito.when(repo.save(clone))
                .thenReturn(product);

        Product save = repo.save(clone);

        int id = save.getId();
        double price = save.getPrice();
        String name = save.getName();

        assertEquals(5, id);
        assertEquals(55.1, price);
        assertEquals("Тестовый молоток 2", name);

        Mockito.verify(repo, Mockito.times(1)).save(product);
    }

    @Test
    void deleteById() {
        repo.save(product);
        repo.deleteById(1);

        assertEquals(0,repo.count());
        assertNotEquals(1,repo.count());

        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
    }

    @Test
    void getId() {
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repo.findById(5))
                .thenReturn(optional);

        Product orderDB = repo.findById(5).orElseThrow(() -> new ResourceNotFoundException(notFoundId(1)));

        int id = orderDB.getId();
        String name = orderDB.getName();

        assertEquals("Тестовый молоток", name);
        assertEquals(5, id);

        Mockito.verify(repo, Mockito.times(1)).findById(5);
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }
}