package com.detran.visao.dialogos;

import com.detran.dao.InfracaoDAO;
import com.detran.dao.VeiculoDAO;
import com.detran.modelo.HistoricoIpva;
import com.detran.modelo.Infracao;
import com.detran.modelo.Veiculo;
import com.detran.util.Formatador;
import com.detran.util.ModeloTabelaNaoEditavel;
import com.detran.util.UtilitarioInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;

public class DialogoVeiculo extends JDialog {
    public DialogoVeiculo(Window proprietarioJanela, int idVeiculo) {
        super(proprietarioJanela, "Detalhes do Veículo", ModalityType.APPLICATION_MODAL);
        setSize(900, 620);
        setLocationRelativeTo(proprietarioJanela);

        try {
            Veiculo veiculo = VeiculoDAO.buscarPorId(idVeiculo);
            if (veiculo == null) {
                UtilitarioInterface.mostrarInformacao(this, "Veículo não encontrado.");
                dispose();
                return;
            }
            List<HistoricoIpva> listaIpva = VeiculoDAO.buscarIpvasPorVeiculo(idVeiculo);
            List<Infracao> listaInfracoes = InfracaoDAO.buscarPorVeiculo(idVeiculo);
            construirInterface(veiculo, listaIpva, listaInfracoes);
        } catch (SQLException excecao) {
            UtilitarioInterface.mostrarErro(this, excecao);
            dispose();
        }
    }

    private void construirInterface(Veiculo veiculo, List<HistoricoIpva> listaIpva, List<Infracao> listaInfracoes) {
        JPanel painelRaiz = new JPanel(new BorderLayout(0, 14));
        painelRaiz.setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));

        JLabel rotuloTitulo = new JLabel("Veículo " + veiculo.placa());
        rotuloTitulo.setFont(rotuloTitulo.getFont().deriveFont(Font.BOLD, 24f));
        painelRaiz.add(rotuloTitulo, BorderLayout.NORTH);

        JPanel painelInfo = new JPanel(new GridLayout(0, 4, 10, 8));
        adicionarCampo(painelInfo, "Placa", veiculo.placa());
        adicionarCampo(painelInfo, "RENAVAM", veiculo.renavam());
        adicionarCampo(painelInfo, "Chassi", veiculo.chassi());
        adicionarCampo(painelInfo, "Ano", String.valueOf(veiculo.anoFabricacao()));
        adicionarCampo(painelInfo, "Marca", veiculo.marca());
        adicionarCampo(painelInfo, "Modelo", veiculo.modelo());
        adicionarCampo(painelInfo, "Tipo", veiculo.tipo());
        adicionarCampo(painelInfo, "Cor", veiculo.cor());
        adicionarCampo(painelInfo, "Proprietário", veiculo.proprietario().nome());
        adicionarCampo(painelInfo, "Documento", veiculo.proprietario().documento());
        adicionarCampo(painelInfo, "Motorista", veiculo.motoristaPrincipal() == null ? "-" : veiculo.motoristaPrincipal().nome());
        adicionarCampo(painelInfo, "Doc. Motorista", veiculo.motoristaPrincipal() == null ? "-" : veiculo.motoristaPrincipal().documento());

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Dados", new JScrollPane(painelInfo));
        abas.addTab("Histórico de IPVA", criarPainelIpva(listaIpva));
        abas.addTab("Infrações", criarPainelInfracoes(listaInfracoes));
        painelRaiz.add(abas, BorderLayout.CENTER);

        JButton botaoFechar = new JButton("Fechar");
        botaoFechar.addActionListener(evento -> dispose());
        painelRaiz.add(botaoFechar, BorderLayout.SOUTH);

        setContentPane(painelRaiz);
    }

    private void adicionarCampo(JPanel painel, String rotulo, String valor) {
        painel.add(new JLabel("<html><b>" + rotulo + ":</b> " + (valor == null ? "-" : valor) + "</html>"));
    }

    private JPanel criarPainelIpva(List<HistoricoIpva> linhas) {
        ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
                new Object[] { "Ano", "Valor Base", "Valor Pago", "Vencimento", "Pagamento", "Status" }, 0);
        for (var registro : linhas) {
            modeloTabela.addRow(new Object[] {
                    registro.anoExercicio(),
                    Formatador.moeda(registro.valorBase()),
                    Formatador.moeda(registro.valorPago()),
                    Formatador.data(registro.dataVencimento()),
                    Formatador.data(registro.dataPagamento()),
                    registro.status()
            });
        }
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(new JScrollPane(new JTable(modeloTabela)), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelInfracoes(List<Infracao> linhas) {
        ModeloTabelaNaoEditavel modeloTabela = new ModeloTabelaNaoEditavel(
                new Object[] { "Data", "Código", "Descrição", "Gravidade", "Pontos", "Status" }, 0);
        for (var registro : linhas) {
            modeloTabela.addRow(new Object[] {
                    Formatador.dataHora(registro.dataHora()),
                    registro.codigoCtb(),
                    registro.descricao(),
                    registro.gravidade(),
                    registro.pontos(),
                    registro.status()
            });
        }
        JTable tabela = new JTable(modeloTabela);
        tabela.setRowHeight(28);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(300);
        tabela.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evento) {
                int linhaSelecionada = tabela.getSelectedRow();
                if (evento.getClickCount() == 2 && linhaSelecionada >= 0) {
                    new DialogoInfracao(DialogoVeiculo.this, linhas.get(linhaSelecionada)).setVisible(true);
                }
            }
        });
        JPanel painel = new JPanel(new BorderLayout());
        painel.add(new JScrollPane(tabela), BorderLayout.CENTER);
        return painel;
    }
}
