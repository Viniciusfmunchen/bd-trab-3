package com.detran.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class Formatador {
    private Formatador() {
    }

    private static final Locale LOCALE_BRASIL = Locale.forLanguageTag("pt-BR");
    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public static String data(LocalDate dataValor) {
        return dataValor == null ? "-" : FORMATO_DATA.format(dataValor);
    }

    public static String dataHora(LocalDateTime dataHoraValor) {
        return dataHoraValor == null ? "-" : FORMATO_DATA_HORA.format(dataHoraValor);
    }

    public static String moeda(BigDecimal valorMonetario) {
        return valorMonetario == null ? "-" : NumberFormat.getCurrencyInstance(LOCALE_BRASIL).format(valorMonetario);
    }
}
