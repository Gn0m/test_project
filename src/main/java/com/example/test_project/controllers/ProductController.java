package com.example.test_project.controllers;

import com.example.test_project.dto.ProductDTO;
import com.example.test_project.model.Product;
import com.example.test_project.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Класс контроллера товара
 *
 * @author L.Gushin
 * @version 2.0
 * @since 07/09/2023
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
public class ProductController {
    /**
     * Сервис для  выполнения операций добавления, удаления, изменения, извлечения товара из бд
     */
    private final ProductService service;

    /**
     * метод для получения товара из конечной точки <code>/goods</code> в формате json и сохранения его в бд
     *
     * @param product товар для сохранения
     * @return <code>Product</code>
     */
    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Товар", notes = "Сохраняет товар", response = Product.class)
    public Product addProduct(@RequestBody ProductDTO product) {
        return service.saveProduct(product);
    }

    /**
     * метод для получения товара из конечной точки <code>/goods/{id}</code> в формате json и обновления его данных в бд
     *
     * @param product товар для изменения
     * @param id      номер товара для изменения
     * @return <code>Product</code>
     */
    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Product updateProduct(@RequestBody Product product, @PathVariable("id") int id) {
        return service.updateProduct(product, id);
    }

    /**
     * метод для получения id из конечно точки <code>/goods/{id}</code> в формате json и удаления заказа из бд, возвращает
     * boolean при удачном или неудачном удалении
     *
     * @param id номер товара для удаления
     * @return <code>boolean</code>
     */
    @DeleteMapping("/{id}")
    public Map<String, Boolean> deleteProductById(@PathVariable("id") int id) {
        return service.deleteById(id);
    }

    /**
     * метод для получения из конечной точки <code>/goods/all</code> в формате json всех товаров
     *
     * @return {@code List<Product>}
     */
    @GetMapping("/all")
    public List<Product> getAllProduct() {
        return service.getAll();
    }

    /**
     * метод для получения из конечной точки <code>/goods/{id}</code> в формате json 1 товара по его id
     *
     * @param id номер товара
     * @return <code>Product</code>
     */
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") int id) {
        return service.getId(id);
    }
}
