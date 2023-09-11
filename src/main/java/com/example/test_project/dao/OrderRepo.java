package com.example.test_project.dao;

import com.example.test_project.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Интерфейс для работы с базой данных закупки
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@Repository
public interface OrderRepo extends JpaRepository<Order, Integer> {
}
