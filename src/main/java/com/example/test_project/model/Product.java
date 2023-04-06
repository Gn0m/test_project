package com.example.test_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "goods", schema = "public")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;

    @JsonIgnore
    @OneToMany(mappedBy = "goods_id")
    private Set<OrderLine> productsLine = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    public void removeProductsLinet(OrderLine line) {
        if (!productsLine.contains(line))
            return;
        productsLine.remove(line);
        line.setGoods_id(null);
    }

    public void addProductsLine(OrderLine line) {
        if (productsLine.contains(line))
            return;
        productsLine.add(line);
        line.setGoods_id(this);
    }
}
