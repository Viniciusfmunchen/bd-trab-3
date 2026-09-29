package com.detran.banco;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class BancoDeDados {
    private static final String URL = obterVariavel("DB_URL", "jdbc:postgresql://localhost:5432/detran");
    private static final String USUARIO = obterVariavel("DB_USER", "postgres");
    private static final String SENHA = obterVariavel("DB_PASSWORD", "postgres");

    private BancoDeDados() {}

    private static String obterVariavel(String chave, String valorPadrao) {
        String propriedade = System.getProperty(chave);
        if (propriedade != null && !propriedade.isBlank()) return propriedade;
        String ambiente = System.getenv(chave);
        return (ambiente != null && !ambiente.isBlank()) ? ambiente : valorPadrao;
    }

    public static Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

