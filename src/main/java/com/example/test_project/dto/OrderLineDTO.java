package com.example.test_project.dto;

import com.example.test_project.model.Product;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "ДТО позиции в заказе")
public class OrderLineDTO {

    /**
     * связь с товаром
     */
    @ApiModelProperty(notes = "Товар в заказе")
    private Product goods_id;
    /**
     * количество
     */
    @ApiModelProperty(notes = "Количество товара в заказе")
    private int count;

    @Override
    public String toString() {
        return "OrderLineDTO{" +
                "goods_id=" + goods_id +
                ", count=" + count +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineDTO that = (OrderLineDTO) o;
        return count == that.count && Objects.equals(goods_id, that.goods_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goods_id, count);
    }
}
