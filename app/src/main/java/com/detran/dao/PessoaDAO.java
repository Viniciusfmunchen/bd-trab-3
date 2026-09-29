package com.detran.dao;

import com.detran.banco.BancoDeDados;
import com.detran.modelo.Cnh;
import com.detran.modelo.Condutor;
import com.detran.modelo.Pessoa;
import com.detran.modelo.VeiculoResumo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PessoaDAO {
    private PessoaDAO() {
    }

    public static List<Pessoa> pesquisarProprietarios(String termo) throws SQLException {
        String comandoSql = """
                SELECT p.id_pessoa, p.nome, p.documento, tp.nome_tipo_pessoa
                FROM pessoa p
                JOIN tipo_pessoa tp ON tp.id_tipo_pessoa = p.id_tipo_pessoa
                WHERE (p.nome ILIKE ? OR p.documento ILIKE ?)
                  AND EXISTS (SELECT 1 FROM veiculo v WHERE v.id_proprietario = p.id_pessoa)
                ORDER BY p.nome
                """;
        String valorPesquisa = "%" + (termo == null ? "" : termo.trim()) + "%";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setString(1, valorPesquisa);
            declaracao.setString(2, valorPesquisa);
            try (ResultSet resultado = declaracao.executeQuery()) {
                List<Pessoa> lista = new ArrayList<>();
                while (resultado.next()) {
                    lista.add(new Pessoa(
                            resultado.getInt(1),
                            resultado.getString(2),
                            resultado.getString(3),
                            resultado.getString(4)));
                }
                return lista;
            }
        }
    }

    public static List<VeiculoResumo> veiculosDoProprietario(int idPessoa) throws SQLException {
        String comandoSql = """
                SELECT v.id_veiculo, v.placa, mv.nome_modelo_veiculo, ma.nome_marca_veiculo,
                       v.ano_fabricacao, c.nome_cor, p.nome
                FROM veiculo v
                JOIN modelo_veiculo mv ON mv.id_modelo_veiculo = v.id_modelo_veiculo
                JOIN marca_veiculo ma ON ma.id_marca_veiculo = mv.id_marca_veiculo
                LEFT JOIN cor c ON c.id_cor = v.id_cor
                JOIN pessoa p ON p.id_pessoa = v.id_proprietario
                WHERE v.id_proprietario = ?
                ORDER BY v.placa
                """;
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, idPessoa);
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

    public static List<Condutor> pesquisarCondutores(String termo) throws SQLException {
        String comandoSql = """
                SELECT p.id_pessoa, p.nome, p.documento, c.num_registro, c.data_validade, c.pontuacao_atual
                FROM pessoa p
                JOIN cnh c ON c.id_pessoa = p.id_pessoa
                WHERE p.nome ILIKE ? OR p.documento ILIKE ? OR c.num_registro ILIKE ?
                ORDER BY p.nome
                """;
        String valorPesquisa = "%" + (termo == null ? "" : termo.trim()) + "%";
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setString(1, valorPesquisa);
            declaracao.setString(2, valorPesquisa);
            declaracao.setString(3, valorPesquisa);
            try (ResultSet resultado = declaracao.executeQuery()) {
                List<Condutor> lista = new ArrayList<>();
                while (resultado.next()) {
                    lista.add(new Condutor(
                            resultado.getInt(1),
                            resultado.getString(2),
                            resultado.getString(3),
                            resultado.getString(4),
                            resultado.getDate(5).toLocalDate().toString(),
                            resultado.getInt(6)));
                }
                return lista;
            }
        }
    }

    public static Cnh buscarCnh(int idPessoa) throws SQLException {
        String comandoSql = """
                SELECT c.num_registro, c.data_validade, c.pontuacao_atual,
                       COALESCE(string_agg(cc.nome_categoria_cnh, ', ' ORDER BY cc.nome_categoria_cnh), '')
                FROM cnh c
                LEFT JOIN cnh_categoria ccat ON ccat.num_registro = c.num_registro
                LEFT JOIN categoria_cnh cc ON cc.id_categoria_cnh = ccat.id_categoria_cnh
                WHERE c.id_pessoa = ?
                GROUP BY c.num_registro, c.data_validade, c.pontuacao_atual
                """;
        try (Connection conexao = BancoDeDados.obterConexao();
                PreparedStatement declaracao = conexao.prepareStatement(comandoSql)) {
            declaracao.setInt(1, idPessoa);
            try (ResultSet resultado = declaracao.executeQuery()) {
                if (!resultado.next())
                    return null;
                String textoCategorias = resultado.getString(4);
                List<String> listaCategorias = (textoCategorias == null || textoCategorias.isBlank())
                        ? List.of()
                        : Arrays.asList(textoCategorias.split(", "));
                return new Cnh(resultado.getString(1), resultado.getDate(2).toLocalDate(), resultado.getInt(3),
                        listaCategorias);
            }
        }
    }
}
