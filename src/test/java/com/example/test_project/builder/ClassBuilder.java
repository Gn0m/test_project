package com.example.test_project.builder;

import com.example.test_project.dto.OrderDTO;
import com.example.test_project.dto.ProductDTO;
import com.example.test_project.model.Order;
import com.example.test_project.model.OrderLine;
import com.example.test_project.model.Product;

import java.time.LocalDate;
import java.util.Random;
import java.util.Set;

public class ClassBuilder {

    public static Order getOrder() {
        Order order = new Order();
        order.setClient(1);
        order.setDate(LocalDate.now());
        order.setAddress("г.Ростов-на-Дону, ул. 40 летия Победы 89/13");
        return order;
    }

    public static OrderDTO getOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setClient(1);
        orderDTO.setDate(order.getDate());
        orderDTO.setAddress("г.Ростов-на-Дону, ул. 40 летия Победы 89/13");
        return orderDTO;
    }

    public static Product getProduct() {
        Product product = new Product();
        product.setPrice(2.1);
        product.setName("Молоток супер крутой");
        return product;
    }

    public static ProductDTO getProductDTO() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Молоток супер крутой");
        dto.setPrice(2.1);
        return dto;
    }

    public static Order setOrderLineToOrder(Order order, Product product) {
        Set<OrderLine> ordersLine = order.getOrdersLine();

        for (int i = 0; i < 2; i++) {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrder_id(order);
            orderLine.setGoods_id(product);
            orderLine.setCount(new Random().nextInt(50));
            ordersLine.add(orderLine);
        }
        return order;
    }
}
