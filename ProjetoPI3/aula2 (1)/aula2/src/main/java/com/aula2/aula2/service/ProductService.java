package com.aula2.aula2.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.aula2.aula2.dto.ProductRequestDto;
import com.aula2.aula2.exception.ProductNotFoundException;
import com.aula2.aula2.model.Product;
import com.aula2.aula2.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    // GET /api/product
    public List<Product> findAll() {
        return repository.findAll();
    }

    // GET /api/product/{id}
    public Product findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    // POST /api/product/product
    public Product create(ProductRequestDto dto) {
        Product product = new Product();
        product.setName(dto.name());
        product.setDescricao(dto.descricao());
        product.setPreco(dto.preco());
        product.setCategoria(dto.categoria());
        product.setImageUrl(dto.imageUrl());
        return repository.save(product);
    }

    // PUT /api/product/{id}
    public Product update(Long id, ProductRequestDto dto) {
        Product product = findById(id);
        product.setName(dto.name());
        product.setDescricao(dto.descricao());
        product.setPreco(dto.preco());
        product.setCategoria(dto.categoria());
        product.setImageUrl(dto.imageUrl());
        return repository.save(product);
    }

    // DELETE /api/product/{id}
    public void delete(Long id) {
        Product product = findById(id);
        repository.delete(product);
    }
}