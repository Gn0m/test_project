package com.example.test_project.controllers.service;

import com.example.test_project.dao.OrderRepo;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepo repo;

    public List<Order> getAll() {
        List<Order> all = repo.findAll();

        if (all.isEmpty())
            throw new ResourceNotFoundException("Orders not found");

        return all;
    }

    public Order save(Order order) {
        order.setId(null);
        order.getOrdersLine().forEach(item -> item.setOrder_id(order));
        return repo.save(order);
    }

    public Order update(Order order, int id) {
        Order orderDb = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id)));

        orderDb.setDate(order.getDate());
        orderDb.setAddress(order.getAddress());
        orderDb.setClient(order.getClient());

        OrderLine line = new OrderLine();
        order.getOrdersLine().forEach(orderLine -> {
            line.setCount(orderLine.getCount());
            line.setId(orderLine.getId());
            line.setGoods_id(orderLine.getGoods_id());
        });

        line.setOrder_id(orderDb);

        orderDb.getOrdersLine().forEach(item -> {
            item.setCount(line.getCount());
            item.setId(line.getId());
            item.setGoods_id(line.getGoods_id());
        });

        repo.save(orderDb);

        return orderDb;
    }

    public Map<String, Boolean> deleteById(int id) {
        Order order = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id)));
        repo.delete(order);
        Map<String, Boolean> status = new HashMap<>();
        status.put("deleted", Boolean.TRUE);
        return status;
    }

    public Order getId(int id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
    }

    private String notFoundId(int id) {
        return "Order with Id: " + id + " not found";
    }
}
