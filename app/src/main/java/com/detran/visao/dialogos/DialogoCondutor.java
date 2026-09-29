package com.detran.visao.dialogos;

import com.detran.dao.InfracaoDAO;
import com.detran.dao.PessoaDAO;
import com.detran.modelo.Cnh;
import com.detran.modelo.Condutor;
import com.detran.modelo.Infracao;
import com.detran.util.Formatador;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class DialogoCondutor extends JDialog {
    public DialogoCondutor(Window proprietarioJanela, Condutor condutor) {
        super(proprietarioJanela, "Detalhes do Condutor", ModalityType.APPLICATION_MODAL);
        setSize(760, 560);
        setLocationRelativeTo(proprietarioJanela);
        try {
            Cnh cnh = PessoaDAO.buscarCnh(condutor.id());
            List<Infracao> listaInfracoes = InfracaoDAO.buscarPorCondutor(condutor.id());
            construirInterface(condutor, cnh, listaInfracoes);
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
            dispose();
        }
    }

    private void construirInterface(Condutor condutor, Cnh cnh, List<Infracao> listaInfracoes) {
        JPanel painelRaiz = new JPanel(new BorderLayout(0, 14));
        painelRaiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel rotuloTitulo = new JLabel(condutor.nome());
        rotuloTitulo.setFont(rotuloTitulo.getFont().deriveFont(Font.BOLD, 24f));
        painelRaiz.add(rotuloTitulo, BorderLayout.NORTH);

        JPanel painelCentral = new JPanel(new BorderLayout(0, 12));
        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        String textoCategorias = (cnh == null || cnh.categorias().isEmpty()) ? "-" : String.join(", ", cnh.categorias());
        areaTexto.setText("Documento: " + condutor.documento() + "\n\nCNH: " + (cnh == null ? "-" : cnh.registro()) + "\nValidade: "
                + (cnh == null ? "-" : Formatador.data(cnh.validade())) + "\nCategorias: " + textoCategorias
                + "\nPontuação Atual: " + (cnh == null ? condutor.pontuacao() : cnh.pontuacao()));
        painelCentral.add(areaTexto, BorderLayout.NORTH);

        ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
                new Object[] { "Data", "Placa", "Infração", "Pontos", "Status" }, 0);
        for (var registro : listaInfracoes) {
            modeloTabela.addRow(new Object[] {
                    Formatador.dataHora(registro.dataHora()),
                    registro.placa(),
                    registro.descricao(),
                    registro.pontos(),
                    registro.status()
            });
        }
        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (evento.getClickCount() == 2 && linhaSelecionada >= 0) {
                    new DialogoInfracao(DialogoCondutor.this, listaInfracoes.get(linhaSelecionada)).setVisible(true);
                }
            }
        });
        painelCentral.add(new JScrollPane(tabela), BorderLayout.CENTER);
        painelRaiz.add(painelCentral, BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(evento -> dispose());
        painelRaiz.add(botaoFechar, BorderLayout.SOUTH);

        setContentPane(painelRaiz);
    }
}
