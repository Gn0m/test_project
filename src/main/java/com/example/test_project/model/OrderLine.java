package com.example.test_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "order_line", schema = "public")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order_id;

    @ManyToOne
    @JoinColumn(name = "goods_id")
    private Product goods_id;
    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLine orderLine = (OrderLine) o;
        return Objects.equals(order_id, orderLine.order_id) && Objects.equals(goods_id, orderLine.goods_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, goods_id);
    }

    public void setOrder_id(Order order_id) {
        if (sameAsFormer(order_id))
            return;
        Order oldOrder = this.order_id;
        this.order_id = order_id;
        if (oldOrder != null)
            oldOrder.removeOrdersLinet(this);
        if (order_id != null)
            order_id.addOrdersLine(this);
    }

    public void setGoods_id(Product goods_id) {
        if (sameAsFormer(goods_id))
            return;
        Product oldProduct = this.goods_id;
        this.goods_id = goods_id;
        if (oldProduct != null)
            oldProduct.removeProductsLinet(this);
        if (goods_id != null)
            goods_id.addProductsLine(this);
    }

    private boolean sameAsFormer(Order newOrder) {
        return order_id == null ? newOrder == null : order_id.equals(newOrder);
    }

    private boolean sameAsFormer(Product newProduct) {
        return goods_id == null ? newProduct == null : goods_id.equals(newProduct);
    }

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
