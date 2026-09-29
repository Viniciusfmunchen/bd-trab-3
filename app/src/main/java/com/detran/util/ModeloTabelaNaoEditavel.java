package com.detran.util;

import javax.swing.table.DefaultTableModel;

public class ModeloTabelaNaoEditavel extends DefaultTableModel {
    public ModeloTabelaNaoEditavel(Object[] colunas, int quantidadeLinhas) {
        super(colunas, quantidadeLinhas);
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}
