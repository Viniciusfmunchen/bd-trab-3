package com.detran;

import com.detran.visao.JanelaPrincipal;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] argumentos) {
        FlatDarkLaf.setup();
        JFrame.setDefaultLookAndFeelDecorated(true);

        SwingUtilities.invokeLater(() -> {
            try {
                new JanelaPrincipal().setVisible(true);
            } catch (Exception excecao) {
                JOptionPane.showMessageDialog(null,
                        "Não foi possível iniciar a aplicação.\n\n" + excecao.getMessage(),
                        "Erro de Inicialização", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
