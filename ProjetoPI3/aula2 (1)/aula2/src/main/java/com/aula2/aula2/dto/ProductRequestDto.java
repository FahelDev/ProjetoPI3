package com.aula2.aula2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequestDto(

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    String name,

    @Size(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    String descricao,

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    Double preco,

    @NotBlank(message = "Categoria é obrigatória")
    String categoria,

    @Size(max = 500, message = "URL da imagem muito longa")
    String imageUrl

) {}