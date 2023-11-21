package com.marcosgrocha.cmp.view;

import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.marcosgrocha.cmp.model.Tabuleiro;

// @SuppressWarnings("serial")
public class PainelTabuleiro extends JPanel {
    public PainelTabuleiro(Tabuleiro tabuleiro) {
        
        setLayout(new GridLayout(
            tabuleiro.getLinhas(), tabuleiro.getColunas()));

        tabuleiro.paraCadaCampo(c -> add(new BotaoCampo(c)));
        tabuleiro.registrarObservador(e -> {

            SwingUtilities.invokeLater(() -> {
                if (e.isGanhou()) {
                    JOptionPane.showMessageDialog(this, "Você ganhou o jogo!");
                } else {
                    JOptionPane.showMessageDialog(this, "EXPLOSÃO! - Tente novamente!");
                }
                tabuleiro.reiniciar();
            });
        });
    }
}
