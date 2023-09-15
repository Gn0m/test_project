package com.example.test_project.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "ДТО заказа")
public class OrderDTO {

    /**
     * номер клиента
     */
    @ApiModelProperty(notes = "Ид клиента")
    private int client;
    /**
     * дата заказа
     */
    @ApiModelProperty(notes = "Дата создания заказа")
    private LocalDate date;
    /**
     * адресс заказа
     */
    @ApiModelProperty(notes = "Адресс доставки заказа")
    private String address;
    /**
     * связь с позицией заказа
     */
    @ApiModelProperty(notes = "Позиции заказа")
    private Set<OrderLineDTO> ordersLine = new HashSet<>();

    @Override
    public String toString() {
        return "OrderDTO{" +
                "client=" + client +
                ", date=" + date +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return client == orderDTO.client && Objects.equals(date, orderDTO.date) && Objects.equals(address, orderDTO.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, date, address);
    }
}
