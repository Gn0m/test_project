package com.example.test_project.service;

import com.example.test_project.dao.OrderRepo;
import com.example.test_project.dto.OrderDTO;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Order;
import com.example.test_project.model.OrderLine;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Класс для работы с БД, добавление, удаление, изменение Order
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@Slf4j
@Service
@AllArgsConstructor
public class OrderService {
    /**
     * реализация интерфейса для сохранения, извлечения, удаления и изменения данных в бд закупки
     */
    private final OrderRepo repo;

    /**
     * Получаем все заказы, если список пустой, возбуждаем исключение <code>ResourceNotFoundException</code>
     *
     * @return {@code List<Order>}
     * @throws ResourceNotFoundException if список пустой
     */
    public List<Order> getAllOrders() {
        List<Order> all = getOrderList();

        if (all.isEmpty())
            throw new ResourceNotFoundException("Orders not found");

        return all;
    }

    /**
     * Получаем список всех Order
     *
     * @return <code>List<Order></code>
     */
    private List<Order> getOrderList() {
        return repo.findAll();
    }

    /**
     * Перед сохранением заказа обходит все позиции и связывает с самим заказом
     * ( сделано так тупо потому что не знаю как в ангулар избавится от цикличности в JSON по принцыпу
     * <code>@JsonIgnore</code> в java )
     *
     * @param dto заказ который необходимо сохранить
     * @return <code>Order</code>
     */
    public Order saveOrder(OrderDTO dto) {
        Order order = new Order();
        order.setClient(dto.getClient());
        order.setDate(dto.getDate());
        order.setAddress(dto.getAddress());

        val ordersDtoLine = dto.getOrdersLine();
        Set<OrderLine> ordersLine = order.getOrdersLine();

        ordersDtoLine.forEach(orderLine -> {
            OrderLine line = new OrderLine();
            line.setCount(orderLine.getCount());
            line.setGoods_id(orderLine.getGoods_id());
            line.setOrder_id(order);
            ordersLine.add(line);
        });

        return repo.save(order);
    }

    /**
     * Находим в бд если нет возбуждаем исключение, далее в найденный объект клонируем параметры из переданного заказа
     * и попутно через <code>forEach</code> проставляем найденный заказ ( объект из бд в качестве связи <code>order_id</code> )
     * P.S смотри почему в save методе
     *
     * @param id    номер заказа который нужно обновить
     * @param order заказ параметрами которого нужно обновить запись в бд
     * @return <code>Order</code>
     */
    public Order updateById(Order order, int id) {
        Order orderDb = getById(id);
        orderDb.cloneParams(order);
        return repo.save(orderDb);
    }

    /**
     * Находим по Id или возбуждаем исключение если не нашли, если позиций нет, то удаляем и возвращаем <code>Boolean.TRUE</code>,
     * если позиции есть то возвращаем <code>Boolean.FALSE</code>
     *
     * @param id номер заказа
     * @return {@code Map<String,Boolean>}
     */
    public Map<String, Boolean> deleteById(int id) {
        Map<String, Boolean> status = new HashMap<>();
        Order order = getById(id);
        if (order.getOrdersLine().isEmpty()) {
            repo.delete(order);
            status.put("Deleted", Boolean.TRUE);
            return status;
        } else {
            status.put("Deleted", Boolean.FALSE);
            return status;
        }
    }

    /**
     * Получаем Order по Id иначе возбуждаем исключение ResourceNotFoundException
     *
     * @param id номер заказа
     * @return <code>Order</code>
     * @throws ResourceNotFoundException if заказ не найден по указанному <code>id</code>
     */
    public Order getById(int id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
    }

    /**
     * Собираем строку для ResourceNotFoundException по переданному Id
     *
     * @param id номер заказа
     * @return String
     */
    private String notFoundId(int id) {
        return "Order with Id " + id + " not found";
    }

}
