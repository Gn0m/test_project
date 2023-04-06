package com.example.test_project.controllers;

import com.example.test_project.model.Order;
import com.example.test_project.controllers.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private OrderService service;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public Order add(@RequestBody Order order) {
        return service.save(order);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Order update(@RequestBody Order order, @PathVariable("id") int id) {
        return service.update(order, id);
    }

    @DeleteMapping("/{id}")
    public Map<String, Boolean> delete(@PathVariable("id") int id) {
        return service.deleteById(id);
    }

    @GetMapping("/all")
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Order getOne(@PathVariable("id") int id) {
        return service.getId(id);
    }
}
