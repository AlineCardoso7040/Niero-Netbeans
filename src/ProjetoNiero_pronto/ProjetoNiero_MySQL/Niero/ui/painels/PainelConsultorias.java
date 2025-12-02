package Niero.ui.painels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import Niero.data.GerenciadorDados;
import Niero.model.Agendamento;

import javax.swing.JComboBox;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultComboBoxModel;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;

import Niero.ui.utils.Visual;

public class PainelConsultorias extends JPanel {

    private static JTextArea areaDetalhes;
    private static JComboBox<String> cmbClientes;

    private static PainelConsultorias instance;

    public PainelConsultorias() {
        super(new BorderLayout(10, 10));
        instance = this;

        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Visual.COR_FUNDO_PAINEL);

        // --- Cabeçalho ---
        JLabel lblTitulo = new JLabel("Acompanhamento de Consultorias");
        lblTitulo.setFont(Visual.FONTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(Visual.COR_PRINCIPAL_TEXTO);
        
        // --- Painel de Seleção de Cliente ---
        JPanel painelSelecao = new JPanel(new BorderLayout(10, 0));
        painelSelecao.setBackground(Visual.COR_FUNDO_PAINEL);
        painelSelecao.add(lblTitulo, BorderLayout.NORTH); // Adiciona o título no topo do painel de seleção
        
        JPanel painelCombo = new JPanel(new BorderLayout(10, 0));
        painelCombo.setBackground(Visual.COR_FUNDO_PAINEL);
        painelCombo.add(new JLabel("Selecione o Cliente:"), BorderLayout.WEST);

        cmbClientes = new JComboBox<>();
        cmbClientes.setFont(Visual.FONTE_FORMULARIO_LABEL);
        painelCombo.add(cmbClientes, BorderLayout.CENTER);
        
        painelSelecao.add(painelCombo, BorderLayout.CENTER); // Adiciona o combo abaixo do título
        
        add(painelSelecao, BorderLayout.NORTH);

        // --- Painel de Detalhes da Consultoria ---
        areaDetalhes = new JTextArea();
        areaDetalhes.setFont(Visual.FONTE_BOTAO_SIDEBAR);
        areaDetalhes.setEditable(false);
        areaDetalhes.setBackground(Visual.COR_FUNDO_PAINEL);
        areaDetalhes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Adiciona um listener para atualizar os detalhes ao mudar a seleção
        cmbClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarDetalhesConsultoria();
            }
        });

        // --- Organização ---
        JScrollPane scrollDetalhes = new JScrollPane(areaDetalhes);
        scrollDetalhes.setBorder(null);
        
        add(scrollDetalhes, BorderLayout.CENTER);
        
        atualizarPainel(); // Carrega os dados iniciais
    }

    public static void atualizarPainel() {
        if (instance == null) return;

        List<Agendamento> agendamentos = GerenciadorDados.getInstance().getAgendamentos();
        Vector<String> nomesClientes = new Vector<>();
        for (Agendamento agendamento : agendamentos) {
            nomesClientes.add(agendamento.getCliente().getNome());
        }
        
        String selecaoAtual = (String) cmbClientes.getSelectedItem();
        cmbClientes.setModel(new DefaultComboBoxModel<>(nomesClientes));
        if (selecaoAtual != null && nomesClientes.contains(selecaoAtual)) {
            cmbClientes.setSelectedItem(selecaoAtual);
        } else if (cmbClientes.getItemCount() > 0) {
            cmbClientes.setSelectedIndex(0);
        } else {
            areaDetalhes.setText("Nenhum agendamento encontrado.");
        }
        
        atualizarDetalhesConsultoria();
    }

    private static void atualizarDetalhesConsultoria() {
        String clienteSelecionado = (String) cmbClientes.getSelectedItem();
        if (clienteSelecionado != null) {
            Agendamento agendamento = GerenciadorDados.getInstance().getAgendamentos().stream()
                .filter(a -> a.getCliente().getNome().equals(clienteSelecionado))
                .findFirst()
                .orElse(null);

            if (agendamento != null) {
                String detalhes = String.format(
                    "Cliente: %s\nData: %s\nHora: %s\nStatus: %s\n\nAções Recomendadas:\n%s",
                    agendamento.getCliente().getNome(),
                    agendamento.getData(),
                    agendamento.getHora(),
                    agendamento.getStatus(),
                    agendamento.getAcoesRecomendadas()
                );
                areaDetalhes.setText(detalhes);
                areaDetalhes.setCaretPosition(0);
                

            } else {
                areaDetalhes.setText("Detalhes da consultoria não encontrados para o cliente: " + clienteSelecionado);
            }
        } else {
            areaDetalhes.setText("Selecione um cliente para ver os detalhes da consultoria.");
        }
    }
}
