package com.detran.modelo;

public record Condutor(
        int id,
        String nome,
        String documento,
        String registroCnh,
        String validadeCnh,
        int pontuacao
) {}
