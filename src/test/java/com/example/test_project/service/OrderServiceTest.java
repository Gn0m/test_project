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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        order.setClient(6);
        order.setDate(LocalDate.now());
        order.setAddress("г.Ростов-на-Дону,ул. Вятская 45");
    }

    @Test
    void getAll() {
        Order clone = new Order();
        clone.setId(2);
        clone.cloneParams(order);

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
        assertEquals(6, client);

        Mockito.verify(repo, Mockito.times(1)).save(order);
    }

    @Test
    void update() {
        Order clone = new Order();

        order.setDate(LocalDate.now());

        clone.cloneParams(order);

        Mockito.when(repo.save(clone))
                .thenReturn(order);

        Order save = repo.save(clone);

        int id = save.getId();
        LocalDate date = save.getDate();
        int client = save.getClient();
        assertEquals(1, id);
        assertEquals(LocalDate.now(), date);
        assertEquals(6, client);

        Mockito.verify(repo, Mockito.times(1)).save(clone);
    }

    @Test
    void deleteById() {
        repo.save(order);

        repo.deleteById(1);

        Mockito.verify(repo, Mockito.times(1)).deleteById(1);
        Mockito.verify(repo, Mockito.times(1)).save(order);
    }

    @Test
    void getId() {
        Optional<Order> optional = Optional.of(order);

        Mockito.when(repo.findById(1))
                .thenReturn(optional);

        Order orderDB = repo.findById(1).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(1)));

        int id = orderDB.getId();
        int client = orderDB.getClient();

        assertEquals(6, client);
        assertEquals(1, id);

        Mockito.verify(repo, Mockito.times(1)).findById(1);
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }
}