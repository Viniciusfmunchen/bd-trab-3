package com.detran.modelo;

public record Veiculo(
        int id,
        String placa,
        String renavam,
        String chassi,
        int anoFabricacao,
        String cor,
        String modelo,
        String marca,
        String tipo,
        Pessoa proprietario,
        Pessoa motoristaPrincipal
) {}
