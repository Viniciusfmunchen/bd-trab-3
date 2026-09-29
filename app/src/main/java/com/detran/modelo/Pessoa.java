package com.detran.modelo;

public record Pessoa(
        int id,
        String nome,
        String documento,
        String tipo
) {}
