package com.detran.visao.paineis;

import com.detran.dao.InfracaoDAO;
import com.detran.modelo.Infracao;
import com.detran.util.Formatador;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;
import com.detran.visao.dialogos.DialogoInfracao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelInfracoes extends JPanel {
    private final JTextField campoPesquisa = new JTextField();
    private final ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
            new Object[] { "Data", "Placa", "Infração", "Gravidade", "Pontos", "Status" }, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private List<Infracao> dados = List.of();

    public PainelInfracoes() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel painelSuperior = new JPanel(new BorderLayout(0, 4));
        painelSuperior.setOpaque(false);
        painelSuperior.add(UtilitarioInterface.criarCabecalho("Infrações", "Pesquise por placa, descrição, status ou gravidade."), BorderLayout.NORTH);
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
                    var infracao = dados.get(linhaSelecionada);
                    new DialogoInfracao(SwingUtilities.getWindowAncestor(PainelInfracoes.this), infracao).setVisible(true);
                }
            }
        });

        pesquisar();
    }

    private void pesquisar() {
        try {
            dados = InfracaoDAO.pesquisar(campoPesquisa.getText());
            modeloTabela.setRowCount(0);
            for (var infracao : dados) {
                modeloTabela.addRow(new Object[] {
                        Formatador.dataHora(infracao.dataHora()),
                        infracao.placa(),
                        infracao.descricao(),
                        infracao.gravidade(),
                        infracao.pontos(),
                        infracao.status()
                });
            }
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
        }
    }
}
