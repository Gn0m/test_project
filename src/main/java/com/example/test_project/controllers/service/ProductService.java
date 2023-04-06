package com.example.test_project.controllers.service;

import com.example.test_project.dao.ProductRepo;
import com.example.test_project.exception.ResourceNotFoundException;
import com.example.test_project.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepo repo;

    public List<Product> getAll() {
        List<Product> all = repo.findAll();

        if (all.isEmpty())
            throw new ResourceNotFoundException("Goods not found");

        return all;
    }

    public Product save(Product product) {
        return repo.save(product);
    }

    public Product update(Product product, int id) {
        Product productDb = repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );

        if (productDb != null && productDb.getId() == id) {
            product.setId(id);
            repo.save(product);
        }
        return product;
    }

    public Map<String, Boolean> deleteById(int id) {
        repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
        repo.deleteById(id);
        Map<String, Boolean> status = new HashMap<>();
        status.put("deleted", Boolean.TRUE);
        return status;
    }

    public Product getId(int id) {
        return repo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(notFoundId(id))
        );
    }

    private String notFoundId(int id) {
        return "Product with Id: " + id + " not found";
    }
}
