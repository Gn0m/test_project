package com.example.test_project.service;

import com.example.test_project.dao.ProductRepo;
import com.example.test_project.dto.ProductDTO;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для работы с БД, добавление, удаление, изменение <code>Product</code>
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@Service
@AllArgsConstructor
public class ProductService {
    /**
     * реализация интерфейса для сохранения, извлечения, удаления и изменения данных в бд заказа
     */
    private final ProductRepo repo;

    /**
     * Получаем все товары, если список пустой, возбуждаем исключение <code>ResourceNotFoundException</code>
     *
     * @return {@code List<Product>}
     * @throws ResourceNotFoundException if список пустой
     */
    public List<Product> getAll() {
        List<Product> all = repo.findAll();

        if (all.isEmpty())
            throw new ResourceNotFoundException("Goods not found");

        return all;
    }

    /**
     * Сохраняет товар
     *
     * @param dto товар для сохранения
     * @return <code>Product</code>
     */
    public Product saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return repo.save(product);
    }

    /**
     * Находим в бд товар, если товара нет возбуждаем исключение <code>ResourceNotFoundException</code>, если найден копируем параметры
     * из product в найденный товар и сохраняем его обратно в бд.
     *
     * @param product товар с новыми значениями
     * @param id      номер товара который нужно обновить
     * @return <code>Product</code>
     * @throws ResourceNotFoundException if товар с указанным значением не найден
     */
    public Product updateProduct(Product product, int id) {
        Product productDb = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
        productDb.cloneParam(product);

        return repo.save(productDb);
    }

    /**
     * Находим по <code>id</code>, если не нашли, возбуждаем исключение <code>ResourceNotFoundException</code>, если нашли,
     * удаляем и возвращаем <code>Boolean.TRUE</code>
     *
     * @param id номер товара для удаления
     * @return Boolean
     */
    public Map<String, Boolean> deleteById(int id) {
        Map<String, Boolean> status = new HashMap<>();
        boolean present = repo.findById(id).isPresent();
        if (present) {
            repo.deleteById(id);
            status.put("Deleted", Boolean.TRUE);
        } else {
            throw new ResourceNotFoundException(notFoundId(id));
        }

        return status;
    }

    /**
     * Получаем <code>Product</code> по <code>Id</code> иначе возбуждаем исключение <code>ResourceNotFoundException</code>
     *
     * @param id номер товара
     * @return <code>Product</code>
     * @throws ResourceNotFoundException if не найден товар по <code>Id</code>
     */
    public Product getId(int id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
    }

    /**
     * Собираем строку для <code>ResourceNotFoundException</code> по переданному <code>Id</code>
     *
     * @param id номер товара
     * @return String
     */
    private String notFoundId(int id) {
        return "Product with Id: " + id + " not found";
    }
}
