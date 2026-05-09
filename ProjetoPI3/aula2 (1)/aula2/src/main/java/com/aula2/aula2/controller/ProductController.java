package com.aula2.aula2.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aula2.aula2.service.ProductService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.aula2.aula2.model.Product;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.aula2.aula2.dto.ProductRequestDto;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("api/product")
@CrossOrigin("*")
public class ProductController {
    private final ProductService service;


    public ProductController(ProductService service){
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> listAll(){
        return ResponseEntity.ok(service.findAll());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Product> findById (@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id)) ;
    }
    

    @PostMapping("product")
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequestDto dto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductRequestDto dto ) {     
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete (@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    
}
