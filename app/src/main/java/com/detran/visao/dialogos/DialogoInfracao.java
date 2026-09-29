package com.detran.visao.dialogos;

import com.detran.modelo.Infracao;
import com.detran.util.Formatador;

import javax.swing.*;
import java.awt.*;

public class DialogoInfracao extends JDialog {
    public DialogoInfracao(Window proprietario, Infracao infracao) {
        super(proprietario, "Detalhes da Infração", ModalityType.APPLICATION_MODAL);
        setSize(700, 520);
        setLocationRelativeTo(proprietario);

        JPanel painelRaiz = new JPanel(new BorderLayout(0, 14));
        painelRaiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel rotuloTitulo = new JLabel("Infração " + infracao.codigoCtb());
        rotuloTitulo.setFont(rotuloTitulo.getFont().deriveFont(Font.BOLD, 24f));
        painelRaiz.add(rotuloTitulo, BorderLayout.NORTH);

        JTextArea areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        areaTexto.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        areaTexto.setText("Placa: " + infracao.placa() + "\nData: " + Formatador.dataHora(infracao.dataHora()) + "\n\nDescrição: "
                + infracao.descricao() + "\nGravidade: " + infracao.gravidade() + "\nPontos: " + infracao.pontos() + "\nValor base: "
                + Formatador.moeda(infracao.valorBase()) + "\nStatus: " + infracao.status() + "\nVencimento: "
                + Formatador.data(infracao.dataVencimento()) + "\nPagamento: " + Formatador.data(infracao.dataPagamento())
                + "\n\nCondutor: " + infracao.condutor() + "\nVia: " + infracao.via() + "\nCidade: " + infracao.cidade() + "\nJurisdição: "
                + infracao.jurisdicao() + "\nReferência: " + infracao.referencia());
        painelRaiz.add(new JScrollPane(areaTexto), BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(evento -> dispose());
        painelRaiz.add(botaoFechar, BorderLayout.SOUTH);

        setContentPane(painelRaiz);
    }
}
