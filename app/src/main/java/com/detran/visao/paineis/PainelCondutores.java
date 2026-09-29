package com.detran.visao.paineis;

import com.detran.dao.PessoaDAO;
import com.detran.modelo.Condutor;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;
import com.detran.visao.dialogos.DialogoCondutor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelCondutores extends JPanel {
    private final JTextField campoPesquisa = new JTextField();
    private final ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
            new Object[] { "Nome", "Documento", "CNH", "Validade", "Pontos" }, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private List<Condutor> dados = List.of();

    public PainelCondutores() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel painelSuperior = new JPanel(new BorderLayout(0, 4));
        painelSuperior.setOpaque(false);
        painelSuperior.add(UtilitarioInterface.criarCabecalho("Condutores", "Pesquise por nome, documento ou número da CNH. Duplo clique para detalhes."), BorderLayout.NORTH);
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
                    var condutor = dados.get(linhaSelecionada);
                    new DialogoCondutor(SwingUtilities.getWindowAncestor(PainelCondutores.this), condutor).setVisible(true);
                }
            }
        });

        pesquisar();
    }

    private void pesquisar() {
        try {
            dados = PessoaDAO.pesquisarCondutores(campoPesquisa.getText());
            modeloTabela.setRowCount(0);
            for (var condutor : dados) {
                modeloTabela.addRow(new Object[] {
                        condutor.nome(),
                        condutor.documento(),
                        condutor.registroCnh(),
                        condutor.validadeCnh(),
                        condutor.pontuacao()
                });
            }
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
        }
    }
}
