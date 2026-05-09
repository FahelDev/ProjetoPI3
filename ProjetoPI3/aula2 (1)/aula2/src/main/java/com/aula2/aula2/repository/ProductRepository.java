package com.aula2.aula2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aula2.aula2.model.Product;

public interface  ProductRepository extends JpaRepository<Product, Long> {
    
    
}
