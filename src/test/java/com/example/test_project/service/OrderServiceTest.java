package com.example.test_project.service;

import com.example.test_project.dao.OrderRepo;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class OrderServiceTest {

    @MockBean
    private OrderRepo repo;

    private Order order;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);
        order.setClient(2);
        order.setDate(LocalDate.now());
        order.setAddress("Тестовый адресс");
    }

    @Test
    void getAll() {
        Order clone = new Order();
        clone.setId(2);
        clone.setAddress(order.getAddress());
        clone.setDate(order.getDate());
        clone.setClient(order.getClient());

        List<Order> list = new ArrayList<>();
        list.add(order);
        list.add(clone);

        Mockito.when(repo.findAll())
                .thenReturn(list);

        List<Order> all = repo.findAll();

        int id = all.get(1).getId();
        int size = all.size();

        assertEquals(2, size);
        assertEquals(2, id);

        Mockito.verify(repo, Mockito.times(1)).findAll();
    }

    @Test
    void save() {
        Mockito.when(repo.save(order))
                .thenReturn(order);

        Order save = repo.save(order);

        int id = save.getId();
        LocalDate date = save.getDate();
        int client = save.getClient();

        assertEquals(1, id);
        assertEquals(order.getDate(), date);
        assertEquals(2, client);

        Mockito.verify(repo, Mockito.times(1)).save(order);
    }

    @Test
    void update() {
        Order clone = new Order();
        clone.setId(5);
        clone.setAddress(order.getAddress());
        clone.setDate(LocalDate.now());
        clone.setClient(order.getClient());

        order.setDate(clone.getDate());

        Mockito.when(repo.save(clone))
                .thenReturn(order);

        Order save = repo.save(clone);

        int id = save.getId();
        LocalDate date = save.getDate();
        int client = save.getClient();
        assertEquals(1, id);
        assertEquals(order.getDate(), date);
        assertEquals(2, client);

        Mockito.verify(repo, Mockito.times(1)).save(clone);
    }

    @Test
    void deleteById() {
        repo.save(order);
        repo.deleteById(1);

        assertEquals(0,repo.count());
        assertNotEquals(1,repo.count());

        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
    }

    @Test
    void getId() {
        Optional<Order> optional = Optional.of(order);

        Mockito.when(repo.findById(1))
                .thenReturn(optional);

        Order orderDB = repo.findById(1).orElseThrow(() -> new ResourceNotFoundException(notFoundId(1)));

        int id = orderDB.getId();
        int client = orderDB.getClient();

        assertEquals(2, client);
        assertEquals(1, id);

        Mockito.verify(repo, Mockito.times(1)).findById(1);
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }
}