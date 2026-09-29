package com.detran.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

public record HistoricoIpva(
        int id,
        int anoExercicio,
        BigDecimal valorBase,
        BigDecimal valorPago,
        LocalDate dataPagamento,
        LocalDate dataVencimento,
        String status
) {}
