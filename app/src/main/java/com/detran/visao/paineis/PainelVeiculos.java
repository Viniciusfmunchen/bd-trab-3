package com.detran.visao.paineis;

import com.detran.dao.VeiculoDAO;
import com.detran.modelo.VeiculoResumo;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;
import com.detran.visao.dialogos.DialogoVeiculo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelVeiculos extends JPanel {
    private final JTextField campoPesquisa = new JTextField();
    private final ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
            new Object[] { "Placa", "Modelo", "Marca", "Ano", "Cor", "Proprietário" }, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private List<VeiculoResumo> dados = List.of();

    public PainelVeiculos() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel painelSuperior = new JPanel(new BorderLayout(0, 4));
        painelSuperior.setOpaque(false);
        painelSuperior.add(UtilitarioInterface.criarCabecalho("Veículos", "Pesquise por placa, RENAVAM, chassi, modelo ou marca."), BorderLayout.NORTH);
        JButton botaoPesquisar = new JButton("Pesquisar");
        painelSuperior.add(UtilitarioInterface.criarBarraPesquisa(campoPesquisa, botaoPesquisar), BorderLayout.CENTER);
        add(painelSuperior, BorderLayout.NORTH);

        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(30);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        botaoPesquisar.addActionListener(evento -> pesquisar());
        campoPesquisa.addActionListener(evento -> pesquisar());
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (evento.getClickCount() == 2 && linhaSelecionada >= 0) {
                    var veiculo = dados.get(linhaSelecionada);
                    new DialogoVeiculo(SwingUtilities.getWindowAncestor(PainelVeiculos.this), veiculo.id()).setVisible(true);
                }
            }
        });

        pesquisar();
    }

    private void pesquisar() {
        try {
            dados = VeiculoDAO.pesquisar(campoPesquisa.getText());
            modeloTabela.setRowCount(0);
            for (var veiculo : dados) {
                modeloTabela.addRow(new Object[] {
                        veiculo.placa(),
                        veiculo.modelo(),
                        veiculo.marca(),
                        veiculo.ano(),
                        veiculo.cor(),
                        veiculo.proprietario()
                });
            }
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
        }
    }
}
