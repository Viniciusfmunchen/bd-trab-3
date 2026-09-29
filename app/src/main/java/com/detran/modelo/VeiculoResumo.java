package com.detran.modelo;

public record VeiculoResumo(
        int id,
        String placa,
        String modelo,
        String marca,
        int ano,
        String cor,
        String proprietario
) {}
