package com.example.test_project.controllers;

import com.example.test_project.model.Product;
import com.example.test_project.controllers.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
public class ProductController {

    private ProductService service;

    @PostMapping(value = "", consumes = "application/json", produces = "application/json")
    public Product add(@RequestBody Product product) {
        return service.save(product);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public Product update(@RequestBody Product product, @PathVariable("id") int id) {
        return service.update(product, id);
    }

    @DeleteMapping("/{id}")
    public Map<String,Boolean> delete(@PathVariable("id") int id) {
       return service.deleteById(id);
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Product getOne(@PathVariable("id") int id) {
        return service.getId(id);
    }
}
