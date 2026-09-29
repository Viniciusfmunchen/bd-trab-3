package com.detran.dao;

import com.detran.banco.BancoDeDados;
import com.detran.modelo.Infracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class InfracaoDAO {
    private InfracaoDAO() {
    }

    private static final String CONSULTA_BASE = """
            SELECT i.id_infracao, v.placa, i.data_hora_infracao, i.data_vencimento, i.data_pagamento,
                   si.nome_status_infracao, ti.codigo_ctb, ti.descricao, g.nome_gravidade, ti.pontos, ti.valor_base,
                   p.nome, vt.nome_oficial, c.nome_cidade, li.referencia, j.nome_jurisdicao_via
            FROM infracao i
            JOIN veiculo v ON v.id_veiculo = i.id_veiculo
            JOIN status_infracao si ON si.id_status_infracao = i.id_status_infracao
            JOIN tipo_infracao ti ON ti.id_tipo_infracao = i.id_tipo_infracao
            JOIN gravidade_infracao g ON g.id_gravidade = ti.id_gravidade
            LEFT JOIN pessoa p ON p.id_pessoa = i.id_condutor_infrator
            JOIN local_infracao li ON li.id_local_infracao = i.id_local_infracao
            JOIN via_transito vt ON vt.id_via = li.id_via
            JOIN cidade c ON c.id_cidade = li.id_cidade
            JOIN jurisdicao_via j ON j.id_jurisdicao_via = vt.id_jurisdicao_via
            """;

    public static List<Infracao> pesquisar(String termo) throws SQLException {
        String comandoSql = CONSULTA_BASE
                + " WHERE v.placa ILIKE ? OR ti.descricao ILIKE ? OR si.nome_status_infracao ILIKE ? OR g.nome_gravidade ILIKE ? ORDER BY i.data_hora_infracao DESC";
        String valorPesquisa = "%" + (termo == null ? "" : termo.trim()) + "%";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            for (int indice = 1; indice <= 4; indice++) {
                declaracao.setString(indice, valorPesquisa);
            }
            try (ResultSet resultado = declaracao.executeQuery()) {
                return mapearLista(resultado);
            }
        }
    }

    public static List<Infracao> buscarPorVeiculo(int idVeiculo) throws SQLException {
        String comandoSql = CONSULTA_BASE + " WHERE i.id_veiculo = ? ORDER BY i.data_hora_infracao DESC";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, idVeiculo);
            try (ResultSet resultado = declaracao.executeQuery()) {
                return mapearLista(resultado);
            }
        }
    }

    public static List<Infracao> buscarPorCondutor(int idCondutor) throws SQLException {
        String comandoSql = CONSULTA_BASE + " WHERE i.id_condutor_infrator = ? ORDER BY i.data_hora_infracao DESC";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, idCondutor);
            try (ResultSet resultado = declaracao.executeQuery()) {
                return mapearLista(resultado);
            }
        }
    }

    private static List<Infracao> mapearLista(ResultSet resultado) throws SQLException {
        List<Infracao> lista = new ArrayList<>();
        while (resultado.next()) {
            lista.add(new Infracao(
                    resultado.getInt(1),
                    resultado.getString(2),
                    resultado.getTimestamp(3).toLocalDateTime(),
                    resultado.getDate(4).toLocalDate(),
                    resultado.getDate(5) == null ? null : resultado.getDate(5).toLocalDate(),
                    resultado.getString(6),
                    resultado.getString(7),
                    resultado.getString(8),
                    resultado.getString(9),
                    resultado.getInt(10),
                    resultado.getBigDecimal(11),
                    resultado.getString(12),
                    resultado.getString(13),
                    resultado.getString(14),
                    resultado.getString(15),
                    resultado.getString(16)));
        }
        return lista;
    }
}
