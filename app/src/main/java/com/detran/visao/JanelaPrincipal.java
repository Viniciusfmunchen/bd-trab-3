package com.detran.visao;

import com.detran.visao.paineis.PainelCondutores;
import com.detran.visao.paineis.PainelInfracoes;
import com.detran.visao.paineis.PainelProprietarios;
import com.detran.visao.paineis.PainelVeiculos;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {
    private final CardLayout layoutCartoes = new CardLayout();
    private final JPanel painelConteudo = new JPanel(layoutCartoes);

    public JanelaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1100, 700));
        setSize(1200, 760);
        setLocationRelativeTo(null);

        JPanel painelLateral = new JPanel();
        painelLateral.setPreferredSize(new Dimension(210, 0));
        painelLateral.setBorder(BorderFactory.createEmptyBorder(18, 14, 18, 14));
        painelLateral.setLayout(new BoxLayout(painelLateral, BoxLayout.Y_AXIS));
        painelLateral.add(Box.createVerticalStrut(24));

        adicionarBotaoNavegacao(painelLateral, "Veículos", "veiculos");
        adicionarBotaoNavegacao(painelLateral, "Proprietários", "proprietarios");
        adicionarBotaoNavegacao(painelLateral, "Condutores", "condutores");
        adicionarBotaoNavegacao(painelLateral, "Infrações", "infracoes");

        painelConteudo.add(new PainelVeiculos(), "veiculos");
        painelConteudo.add(new PainelProprietarios(), "proprietarios");
        painelConteudo.add(new PainelCondutores(), "condutores");
        painelConteudo.add(new PainelInfracoes(), "infracoes");

        setLayout(new BorderLayout());
        add(painelLateral, BorderLayout.WEST);
        add(painelConteudo, BorderLayout.CENTER);
    }

    private void adicionarBotaoNavegacao(JPanel painelLateral, String texto, String identificadorCartao) {
        JButton botaoNavegacao = new JButton(texto);
        botaoNavegacao.setAlignmentX(Component.LEFT_ALIGNMENT);
        botaoNavegacao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        botaoNavegacao.addActionListener(evento -> layoutCartoes.show(painelConteudo, identificadorCartao));
        painelLateral.add(botaoNavegacao);
        painelLateral.add(Box.createVerticalStrut(6));
    }
}
