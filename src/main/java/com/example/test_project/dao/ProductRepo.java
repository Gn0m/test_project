package com.example.test_project.dao;

import com.example.test_project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс для работы с базой данных товара
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
public interface ProductRepo extends JpaRepository<Product, Integer> {
}
