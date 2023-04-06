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

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders", schema = "public")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer client;
    private LocalDate date;
    private String address;

    @OneToMany(mappedBy = "order_id", cascade = CascadeType.ALL)
    private Set<OrderLine> ordersLine = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Set<OrderLine> getOrdersLine() {
        return new HashSet<>(ordersLine);
    }

    public void addOrdersLine(OrderLine line) {
        if (ordersLine.contains(line))
            return ;
        ordersLine.add(line);
        line.setOrder_id(this);
    }

    public void removeOrdersLinet(OrderLine line) {
        if (!ordersLine.contains(line))
            return ;
        ordersLine.remove(line);
        line.setOrder_id(null);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client=" + client +
                ", date=" + date +
                ", address='" + address + '\'' +
                '}';
    }
}
