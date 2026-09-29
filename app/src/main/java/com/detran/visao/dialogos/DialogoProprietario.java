package com.detran.visao.dialogos;

import com.detran.dao.PessoaDAO;
import com.detran.modelo.VeiculoResumo;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class DialogoProprietario extends JDialog {
    public DialogoProprietario(Window proprietarioJanela, int idPessoa, String nomePessoa) {
        super(proprietarioJanela, "Proprietário", ModalityType.APPLICATION_MODAL);
        setSize(760, 500);
        setLocationRelativeTo(proprietarioJanela);
        try {
            construirInterface(idPessoa, nomePessoa);
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
            dispose();
        }
    }

    private void construirInterface(int idPessoa, String nomePessoa) throws SQLException {
        JPanel painelRaiz = new JPanel(new BorderLayout(0, 14));
        painelRaiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel rotuloTitulo = new JLabel(nomePessoa);
        rotuloTitulo.setFont(rotuloTitulo.getFont().deriveFont(Font.BOLD, 24f));
        painelRaiz.add(rotuloTitulo, BorderLayout.NORTH);

        List<VeiculoResumo> linhas = PessoaDAO.veiculosDoProprietario(idPessoa);
        ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
                new Object[] { "Placa", "Modelo", "Marca", "Ano", "Cor" }, 0);
        for (var veiculo : linhas) {
            modeloTabela.addRow(new Object[] { veiculo.placa(), veiculo.modelo(), veiculo.marca(), veiculo.ano(), veiculo.cor() });
        }

        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(30);
        painelRaiz.add(new JScrollPane(tabela), BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(evento -> dispose());
        painelRaiz.add(botaoFechar, BorderLayout.SOUTH);

        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                if (evento.getClickCount() == 2) {
                    int linhaSelecionada = tabela.getSelectedRow();
                    if (linhaSelecionada >= 0) {
                        new DialogoVeiculo(DialogoProprietario.this, linhas.get(linhaSelecionada).id()).setVisible(true);
                    }
                }
            }
        });

        setContentPane(painelRaiz);
    }
}
