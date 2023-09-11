package com.example.test_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * Класс сущности товара
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
@Table(name = "order_line", schema = "public")
public class OrderLine {
    /**
     * номер позиции
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * связь с заказом
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order_id;
    /**
     * связь с товаром
     */
    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Product goods_id;
    /**
     * количество
     */
    private int count;

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
        OrderLine orderLine = (OrderLine) o;
        return id == orderLine.id && count == orderLine.count && Objects.equals(order_id, orderLine.order_id) && Objects.equals(goods_id, orderLine.goods_id);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, order_id, goods_id, count);
    }

    /**
     * представление заказа в виде строки
     *
     * @return String
     */
    @Override
    public String toString() {
        return "OrderLine{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", goods_id=" + goods_id +
                ", count=" + count +
                '}';
    }
}
