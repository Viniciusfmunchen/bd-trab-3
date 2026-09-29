package com.detran.modelo;

import java.time.LocalDate;
import java.util.List;

public record Cnh(
        String registro,
        LocalDate validade,
        int pontuacao,
        List<String> categorias
) {}
