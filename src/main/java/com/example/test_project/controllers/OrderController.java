package com.example.test_project.controllers;

import com.example.test_project.dto.OrderDTO;
import com.example.test_project.model.Order;
import com.example.test_project.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Класс контроллера заказа
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    /**
     * Сервис для  выполнения операций добавления, удаления, изменения, извлечения заказа из бд
     */
    private final OrderService service;

    /**
     * метод для получения заказа из конечной точки <code>/orders</code> в формате json и сохранения его в бд
     *
     * @param order заказ который нужно сохранить
     * @return Order
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public Order addOrder(@RequestBody OrderDTO order) {
        return service.saveOrder(order);
    }

    /**
     * метод для получения заказа из конечной точки <code>/orders/{id}</code> в формате json и обновления его данных в бд
     *
     * @param order заказ который нужно обновить
     * @param id    номер заказа
     * @return <code>Order</code>
     */
    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Order updateOrder(@RequestBody Order order, @PathVariable("id") int id) {
        return service.updateById(order, id);
    }

    /**
     * метод для получения id из конечно точки <code>/orders/{id}</code> в формате json и удаления заказа из бд, возвращает
     * boolean при удачном или неудачном удалении
     *
     * @param id номер заказа
     * @return {@code Map<String, Boolean>}
     */
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteOrderById(@PathVariable("id") int id) {
        return service.deleteById(id);
    }

    /**
     * метод для получения из конечной точки <code>/orders/all</code> в формате json всех заказов
     *
     * @return {@code List<Order>}
     */
    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return service.getAllOrders();
    }

    /**
     * метод для получения из конечной точки <code>/orders/{id}</code> в формате json 1 заказа по его id
     *
     * @param id номер заказа
     * @return <code>Order</code>
     */
    @GetMapping("/{id}")
    public Order getOrder(@PathVariable("id") int id) {
        return service.getById(id);
    }
}
