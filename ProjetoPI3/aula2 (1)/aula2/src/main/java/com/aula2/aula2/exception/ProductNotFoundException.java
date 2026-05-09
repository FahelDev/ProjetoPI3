package com.aula2.aula2.exception;

public class ProductNotFoundException extends  RuntimeException {
    public ProductNotFoundException(Long id){
        super("Produto nao encontrado");
    }
    
}
