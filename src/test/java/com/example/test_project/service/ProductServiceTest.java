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
        product.setId(1);
        product.setPrice(20.1);
        product.setName("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA");

    }

    @Test
    void getAll() {
        Product clone = new Product();
        clone.cloneParam(product);
        clone.setId(2);


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

        assertEquals(1, id);
        assertEquals(20.1, price);
        assertEquals("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA", name);

        Mockito.verify(repo, Mockito.times(1)).save(product);
    }

    @Test
    void update() {
        product.setName("Молоток слесарный 1000г кв. боек стальная ручка SPARTA");
        product.setPrice(30.1);

        Product clone = new Product();
        clone.cloneParam(product);
        clone.setId(1);

        Mockito.when(repo.save(clone))
                .thenReturn(product);

        Product save = repo.save(clone);

        int id = save.getId();
        double price = save.getPrice();
        String name = save.getName();

        assertEquals(1, id);
        assertEquals(30.1, price);
        assertEquals("Молоток слесарный 1000г кв. боек стальная ручка SPARTA", name);

        Mockito.verify(repo, Mockito.times(1)).save(product);
    }

    @Test
    void deleteById() {
        repo.save(product);
        repo.deleteById(1);

        Mockito.verify(repo, Mockito.times(1)).save(product);
        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
    }

    @Test
    void getId() {
        Optional<Product> optional = Optional.of(product);

        Mockito.when(repo.findById(1))
                .thenReturn(optional);

        Product orderDB = repo.findById(1).orElseThrow(() -> new ResourceNotFoundException(notFoundId(1)));

        int id = orderDB.getId();
        String name = orderDB.getName();

        assertEquals("Молоток слесарный 1000г кв. боек деревянная ручка SPARTA", name);
        assertEquals(1, id);

        Mockito.verify(repo, Mockito.times(1)).findById(1);
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }
}