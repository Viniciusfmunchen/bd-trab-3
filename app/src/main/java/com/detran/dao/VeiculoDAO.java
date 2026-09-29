package com.detran.dao;

import com.detran.banco.BancoDeDados;
import com.detran.modelo.HistoricoIpva;
import com.detran.modelo.Pessoa;
import com.detran.modelo.Veiculo;
import com.detran.modelo.VeiculoResumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class VeiculoDAO {
    private VeiculoDAO() {
    }

    public static List<VeiculoResumo> pesquisar(String termo) throws SQLException {
        String comandoSql = """
                SELECT v.id_veiculo, v.placa, mv.nome_modelo_veiculo, ma.nome_marca_veiculo,
                       v.ano_fabricacao, c.nome_cor, p.nome
                FROM veiculo v
                JOIN modelo_veiculo mv ON mv.id_modelo_veiculo = v.id_modelo_veiculo
                JOIN marca_veiculo ma ON ma.id_marca_veiculo = mv.id_marca_veiculo
                LEFT JOIN cor c ON c.id_cor = v.id_cor
                JOIN pessoa p ON p.id_pessoa = v.id_proprietario
                WHERE v.placa ILIKE ? OR v.cod_renavam ILIKE ? OR v.num_chassi ILIKE ?
                   OR mv.nome_modelo_veiculo ILIKE ? OR ma.nome_marca_veiculo ILIKE ?
                ORDER BY v.placa
                """;
        String valorPesquisa = "%" + termo.trim() + "%";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            for (int indice = 1; indice <= 5; indice++) {
                declaracao.setString(indice, valorPesquisa);
            }
            try (ResultSet resultado = declaracao.executeQuery()) {
                List<VeiculoResumo> lista = new ArrayList<>();
                while (resultado.next()) {
                    lista.add(new VeiculoResumo(
                            resultado.getInt(1),
                            resultado.getString(2),
                            resultado.getString(3),
                            resultado.getString(4),
                            resultado.getInt(5),
                            resultado.getString(6),
                            resultado.getString(7)));
                }
                return lista;
            }
        }
    }

    public static Veiculo buscarPorId(int id) throws SQLException {
        String comandoSql = """
                SELECT v.id_veiculo, v.placa, v.cod_renavam, v.num_chassi, v.ano_fabricacao,
                       c.nome_cor, mv.nome_modelo_veiculo, ma.nome_marca_veiculo, tv.nome_tipo_veiculo,
                       po.id_pessoa, po.nome, po.documento, tpo.nome_tipo_pessoa,
                       mp.id_pessoa, mp.nome, mp.documento, tmp.nome_tipo_pessoa
                FROM veiculo v
                LEFT JOIN cor c ON c.id_cor = v.id_cor
                JOIN modelo_veiculo mv ON mv.id_modelo_veiculo = v.id_modelo_veiculo
                JOIN marca_veiculo ma ON ma.id_marca_veiculo = mv.id_marca_veiculo
                JOIN tipo_veiculo tv ON tv.id_tipo_veiculo = mv.id_tipo_veiculo
                JOIN pessoa po ON po.id_pessoa = v.id_proprietario
                JOIN tipo_pessoa tpo ON tpo.id_tipo_pessoa = po.id_tipo_pessoa
                LEFT JOIN pessoa mp ON mp.id_pessoa = v.id_motorista_principal
                LEFT JOIN tipo_pessoa tmp ON tmp.id_tipo_pessoa = mp.id_tipo_pessoa
                WHERE v.id_veiculo = ?
                """;
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, id);
            try (ResultSet resultado = declaracao.executeQuery()) {
                if (!resultado.next())
                    return null;
                Pessoa proprietario = new Pessoa(resultado.getInt(10), resultado.getString(11), resultado.getString(12),
                        resultado.getString(13));
                Pessoa motorista = resultado.getObject(14) == null ? null
                        : new Pessoa(resultado.getInt(14), resultado.getString(15), resultado.getString(16),
                                resultado.getString(17));
                return new Veiculo(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getInt(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        resultado.getString(8),
                        resultado.getString(9),
                        proprietario,
                        motorista);
            }
        }
    }

    public static List<HistoricoIpva> buscarIpvasPorVeiculo(int idVeiculo) throws SQLException {
        String comandoSql = """
                SELECT h.id_ipva, h.ano_exercicio, h.valor_base, h.valor_pago,
                       h.data_pagamento, h.data_vencimento, s.nome_status_ipva
                FROM historico_ipva h
                JOIN status_ipva s ON s.id_status_ipva = h.id_status_ipva
                WHERE h.id_veiculo = ?
                ORDER BY h.ano_exercicio DESC
                """;
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, idVeiculo);
            try (ResultSet resultado = declaracao.executeQuery()) {
                List<HistoricoIpva> lista = new ArrayList<>();
                while (resultado.next()) {
                    lista.add(new HistoricoIpva(
                            resultado.getInt(1),
                            resultado.getInt(2),
                            resultado.getBigDecimal(3),
                            resultado.getBigDecimal(4),
                            resultado.getDate(5) == null ? null : resultado.getDate(5).toLocalDate(),
                            resultado.getDate(6).toLocalDate(),
                            resultado.getString(7)));
                }
                return lista;
            }
        }
    }
}
