package Niero;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import Niero.ui.Sidebar;
import Niero.ui.painels.PainelAgenda;
import Niero.ui.painels.PainelCadastrarCliente;
import Niero.ui.painels.PainelConsultorias;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;


import Niero.ui.utils.Visual;

public class TelaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public TelaPrincipal() {
        super("Niero Assessoria - Gestão de Atendimentos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null); 
       
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Inicializa o CardLayout e o painel de conteúdo principal
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(Visual.COR_FUNDO_PAINEL);

        // Adiciona os painéis de conteúdo
        mainContentPanel.add(new PainelAgenda(), "AGENDA");
        mainContentPanel.add(new PainelCadastrarCliente(), "CADASTRAR_CLIENTE");
        mainContentPanel.add(new PainelConsultorias(), "CONSULTORIAS");
        

        Sidebar sidebar = new Sidebar(cardLayout, mainContentPanel);
        contentPane.add(sidebar, BorderLayout.WEST);
        
        contentPane.add(mainContentPanel, BorderLayout.CENTER);
        
        // Define a tela inicial
        cardLayout.show(mainContentPanel, "AGENDA");
    }

    /**
     * Método principal que inicia a aplicação.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {

                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new TelaPrincipal().setVisible(true);
        });
    }
}
