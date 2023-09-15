package com.example.test_project.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
@Table(name = "goods", schema = "public")
@ApiModel(description = "Товар")
public class Product {

    /**
     * номер товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "Номер товара")
    private int id;
    /**
     * название товара
     */
    @ApiModelProperty(value = "Наименование")
    private String name;
    /**
     * цена товара
     */
    @ApiModelProperty(value = "Цена")
    private double price;
    /**
     * связь с позицией
     */
    @JsonIgnore
    @OneToMany(mappedBy = "goods_id", cascade = CascadeType.REMOVE)
    private Set<OrderLine> productsLine = new HashSet<>();

    /**
     * Переносит параметры из переданного товара в текущий
     *
     * @param product клонируемый объект
     */
    public void cloneParam(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
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
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && Objects.equals(name, product.name);
    }

    /**
     * hashCode
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, price);
    }

    /**
     * представление товара в виде строки
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Id = " + id +
                ", name='" + name +
                ", price=" + price;
    }


}
