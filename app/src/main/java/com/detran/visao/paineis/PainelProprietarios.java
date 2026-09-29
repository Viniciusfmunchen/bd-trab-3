package com.detran.visao.paineis;

import com.detran.dao.PessoaDAO;
import com.detran.modelo.Pessoa;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;
import com.detran.visao.dialogos.DialogoProprietario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class PainelProprietarios extends JPanel {
    private final JTextField campoPesquisa = new JTextField();
    private final ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
            new Object[] { "Nome", "Documento", "Tipo" }, 0);
    private final JTable tabela = new JTable(modeloTabela);
    private List<Pessoa> dados = List.of();

    public PainelProprietarios() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JPanel painelSuperior = new JPanel(new BorderLayout(0, 4));
        painelSuperior.setOpaque(false);
        painelSuperior.add(UtilitarioInterface.criarCabecalho("Proprietários", "Pesquise por nome ou documento."), BorderLayout.NORTH);
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
                    var proprietario = dados.get(linhaSelecionada);
                    new DialogoProprietario(SwingUtilities.getWindowAncestor(PainelProprietarios.this), proprietario.id(), proprietario.nome()).setVisible(true);
                }
            }
        });

        pesquisar();
    }

    private void pesquisar() {
        try {
            dados = PessoaDAO.pesquisarProprietarios(campoPesquisa.getText());
            modeloTabela.setRowCount(0);
            for (var proprietario : dados) {
                modeloTabela.addRow(new Object[] { proprietario.nome(), proprietario.documento(), proprietario.tipo() });
            }
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
        }
    }
}
