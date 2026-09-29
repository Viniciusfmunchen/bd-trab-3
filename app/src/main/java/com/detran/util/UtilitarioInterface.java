package com.detran.util;

import javax.swing.*;
import java.awt.*;

public final class UtilitarioInterface {
    private UtilitarioInterface() {}

    public static void mostrarErro(Component componentePai, Exception excecao) {
        JOptionPane.showMessageDialog(componentePai, excecao.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void mostrarInformacao(Component componentePai, String mensagem) {
        JOptionPane.showMessageDialog(componentePai, mensagem, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static JPanel criarCabecalho(String textoTitulo, String textoSubtitulo) {
        JPanel painelCabecalho = new JPanel(new BorderLayout(0, 4));
        painelCabecalho.setOpaque(false);
        JLabel rotuloTitulo = new JLabel(textoTitulo);
        rotuloTitulo.setFont(rotuloTitulo.getFont().deriveFont(Font.BOLD, 24f));
        painelCabecalho.add(rotuloTitulo, BorderLayout.NORTH);
        if (textoSubtitulo != null && !textoSubtitulo.isBlank()) {
            painelCabecalho.add(new JLabel(textoSubtitulo), BorderLayout.CENTER);
        }
        return painelCabecalho;
    }

    public static JPanel criarBarraPesquisa(JTextField campoTexto, JButton botaoAcao) {
        JPanel painelBarra = new JPanel(new BorderLayout(8, 0));
        painelBarra.setOpaque(false);
        painelBarra.add(campoTexto, BorderLayout.CENTER);
        painelBarra.add(botaoAcao, BorderLayout.EAST);
        painelBarra.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        return painelBarra;
    }
}
