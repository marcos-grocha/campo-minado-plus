package com.marcosgrocha.cmp.view;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import com.marcosgrocha.cmp.model.Campo;
import com.marcosgrocha.cmp.model.CampoEvento;
import com.marcosgrocha.cmp.model.CampoObservador;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(184, 66, 68);
    private final Color BG_VERDE = new Color(0, 100, 0);

    private Campo campo;

    public BotaoCampo(Campo campo) {
        this.campo = campo;
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));

        addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR:
                aplicarEstiloAbrir();
                break;
            case MARCAR:
                aplicarEstiloMarcar();
                break;
            case EXPLODIR:
                aplicarEstiloExplodir();
                break;
            default:
                aplicarEstiloPadrao();
                break;
        }        
        SwingUtilities.invokeLater(() -> {
            repaint();
            validate();
        });
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }
    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }
    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
        // setIcon
    }
    private void aplicarEstiloAbrir() {

        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            return;
        }

        setBackground(BG_PADRAO);

        switch (campo.minasNaVizinhanca()) {
            case 1:
                setForeground(BG_VERDE);
                break;
            case 2:
                setForeground(Color.BLUE);
                break;
            case 3:
                setForeground(Color.YELLOW);
                break;
            case 4:
            case 5:
            case 6:
                setForeground(Color.RED);
                break;
            default:
                setForeground(Color.PINK);
                break;
        }
        String valor = !campo.vizinhancaSegura() ? 
                campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }
    
    // Interface dos eventos do mouse
    @Override
    public void mousePressed(MouseEvent evento) {
        if (evento.getButton() == 1) {
            campo.abrir();
        } else {
            campo.alternarMarcacao();
        }
    }

    public void mouseEntered(MouseEvent arg0) {}
    public void mouseExited(MouseEvent arg0) {}
    public void mouseClicked(MouseEvent arg0) {}
    public void mouseReleased(MouseEvent arg0) {}

}