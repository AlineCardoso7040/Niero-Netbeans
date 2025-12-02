package Niero.ui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Niero.ui.utils.Visual;

public class Sidebar extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public Sidebar(CardLayout cardLayout, JPanel mainContentPanel) {
        this.cardLayout = cardLayout;
        this.mainContentPanel = mainContentPanel;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Visual.COR_FUNDO_SIDEBAR);
        setPreferredSize(new Dimension(220, 0));
        
        // Logo
        JLabel lblLogo = new JLabel("Niero Assessoria");
        lblLogo.setFont(Visual.FONTE_LOGO);
        lblLogo.setForeground(Visual.COR_TEXTO_SIDEBAR); // Garante que a logo seja branca no fundo escuro
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogo.setBorder(new EmptyBorder(20, 10, 20, 10));
        add(lblLogo);
        
        add(Box.createRigidArea(new Dimension(0, 20))); 

        // Botões de Navegação
      add(criarBotaoSidebar("Agenda", "AGENDA"));
        add(criarBotaoSidebar("Cadastrar Clientes", "CADASTRAR_CLIENTE"));
        add(criarBotaoSidebar("Consultorias", "CONSULTORIAS"));        
        add(Box.createVerticalGlue());

        // Botão Sair
        JButton btnSair = new JButton("Sair");
        formatarBotaoSidebar(btnSair);
        btnSair.addActionListener(e -> System.exit(0));
        add(btnSair);
    }

    private JButton criarBotaoSidebar(String texto, String cardName) {
        JButton button = new JButton(texto);
        formatarBotaoSidebar(button);
        button.addActionListener(e -> cardLayout.show(mainContentPanel, cardName));
        return button;
    }
    
    private void formatarBotaoSidebar(JButton button) {
        button.setForeground(Visual.COR_TEXTO_SIDEBAR);
        button.setBackground(Visual.COR_FUNDO_SIDEBAR);
        button.setFont(Visual.FONTE_BOTAO_SIDEBAR);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(15, 25, 15, 25));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, button.getPreferredSize().height));

        // Efeito Hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(Visual.COR_BOTAO_SIDEBAR_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                button.setBackground(Visual.COR_FUNDO_SIDEBAR);
            }
        });
    }
}
