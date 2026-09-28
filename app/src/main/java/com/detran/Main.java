package com.detran;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Vector;
import com.formdev.flatlaf.FlatDarkLaf;

public class Main extends JFrame {

    // Configurações do Banco de Dados (Ajuste com suas credenciais)
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/detran"; // Mude o nome do banco
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private JComboBox<String> comboPlacas;
    private JTable tabelaIpva;
    private JTable tabelaInfracoes;
    private DefaultTableModel modeloIpva;
    private DefaultTableModel modeloInfracoes;

    public Main() {
        setTitle("DETRAN-PR: Consulta de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        inicializarComponentes();
        carregarPlacas();
    }

    private void inicializarComponentes() {
        // Painel Superior (Filtro)
        JPanel painelTopo = new JPanel();
        painelTopo.add(new JLabel("Selecione o Veículo (Placa): "));
        comboPlacas = new JComboBox<>();
        comboPlacas.addActionListener(e -> buscarDadosVeiculo());
        painelTopo.add(comboPlacas);
        add(painelTopo, BorderLayout.NORTH);

        // Modelos das Tabelas
        modeloIpva = new DefaultTableModel(
                new String[] { "Proprietário", "Modelo", "Ano", "Valor Pago", "Data Pgto", "Status" }, 0);
        tabelaIpva = new JTable(modeloIpva);

        modeloInfracoes = new DefaultTableModel(
                new String[] { "Condutor", "CTB", "Infração", "Pontos", "Valor", "Data/Hora", "Status" }, 0);
        tabelaInfracoes = new JTable(modeloInfracoes);

        // Divisor de Tela (Split Pane) para mostrar IPVA e Infrações
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                criarPainelTabela("Histórico de IPVA (Últimos 4 anos)", tabelaIpva),
                criarPainelTabela("Histórico de Infrações", tabelaInfracoes));
        splitPane.setDividerLocation(250);
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel criarPainelTabela(String titulo, JTable tabela) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return painel;
    }

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Carrega as placas disponíveis no banco para o ComboBox
    private void carregarPlacas() {
        try (Connection conn = conectar();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT placa FROM veiculo ORDER BY placa")) {

            comboPlacas.addItem("Selecione...");
            while (rs.next()) {
                comboPlacas.addItem(rs.getString("placa"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar placas: " + e.getMessage());
        }
    }

    // Busca os dados nas Views baseado na placa selecionada
    private void buscarDadosVeiculo() {
        String placaSelecionada = (String) comboPlacas.getSelectedItem();
        if (placaSelecionada == null || placaSelecionada.equals("Selecione...")) {
            modeloIpva.setRowCount(0);
            modeloInfracoes.setRowCount(0);
            return;
        }

        carregarHistoricoIpva(placaSelecionada);
        carregarHistoricoInfracoes(placaSelecionada);
    }

    private void carregarHistoricoIpva(String placa) {
        modeloIpva.setRowCount(0); // Limpa a tabela
        String sql = "SELECT * FROM vw_historico_ipva WHERE placa = ?";

        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("proprietario"));
                linha.add(rs.getString("modelo"));
                linha.add(rs.getInt("ano_exercicio"));
                linha.add(String.format("R$ %.2f", rs.getDouble("valor_pago")));
                linha.add(rs.getDate("data_pagamento"));
                linha.add(rs.getString("status"));
                modeloIpva.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar IPVA: " + e.getMessage());
        }
    }

    private void carregarHistoricoInfracoes(String placa) {
        modeloInfracoes.setRowCount(0); // Limpa a tabela
        String sql = "SELECT * FROM vw_historico_infracoes WHERE placa = ?";

        try (Connection conn = conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, placa);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vector<Object> linha = new Vector<>();
                linha.add(rs.getString("condutor_infrator"));
                linha.add(rs.getString("codigo_ctb"));
                linha.add(rs.getString("infracao"));
                linha.add(rs.getInt("pontos"));
                linha.add(String.format("R$ %.2f", rs.getDouble("valor_multa")));
                linha.add(rs.getTimestamp("data_hora_infracao"));
                linha.add(rs.getString("status_multa"));
                modeloInfracoes.addRow(linha);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar Infrações: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            FlatDarkLaf.setup();
        } catch (Exception e) {
            System.err.println("Falha ao inicializar o FlatLaf");
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}