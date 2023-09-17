package com.example.test_project.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс сущности заказа
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders", schema = "public")
public class Order {
    /**
     * номер заказа
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * номер клиента
     */
    private int client;
    /**
     * дата заказа
     */
    private LocalDate date;
    /**
     * адресс заказа
     */
    private String address;

    /**
     * связь с позицией заказа
     */
    @OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderLine> ordersLine = new HashSet<>();

    /**
     * Переносит параметры из переданного заказа в текущий
     *
     * @param order заказ для клонирования
     */
    public void cloneParams(Order order) {
        this.date = order.getDate();
        this.address = order.getAddress();
        this.client = order.getClient();

        if (order.getOrdersLine().isEmpty()) {
            this.ordersLine.clear();
        }

        if (!order.getOrdersLine().isEmpty()) {
            order.getOrdersLine().forEach(orderLine -> orderLine.setOrder_id(this));
            this.ordersLine.clear();
            this.ordersLine.addAll(order.getOrdersLine());
        }
    }

    /**
     * equals
     *
     * @param o объект для сравнения
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * представление заказа в виде строки
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Id=" + id +
                ", client=" + client +
                ", date=" + date +
                ", address='" + address + " orderLine size: " + ordersLine.size();
    }
}
