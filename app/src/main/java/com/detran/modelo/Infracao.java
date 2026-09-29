package com.detran.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record Infracao(
        int id,
        String placa,
        LocalDateTime dataHora,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        String status,
        String codigoCtb,
        String descricao,
        String gravidade,
        int pontos,
        BigDecimal valorBase,
        String condutor,
        String via,
        String cidade,
        String jurisdicao,
        String referencia) {
}
