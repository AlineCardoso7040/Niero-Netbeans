package Niero.ui.painels;

import java.awt.BorderLayout;
import java.awt.Color;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Niero.ui.utils.Visual;
import Niero.data.GerenciadorDados;
import Niero.model.Agendamento;

import java.util.List;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JOptionPane;
import Niero.ui.painels.PainelConsultorias;

public class PainelAgenda extends JPanel {

    private static DefaultTableModel tableModel;
    private static JTable tabela;

    public PainelAgenda() {
        super(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Visual.COR_FUNDO_PAINEL);

        // --- Cabeçalho 
        JLabel lblTitulo = new JLabel("Agenda de Consultorias");
        lblTitulo.setFont(Visual.FONTE_TITULO_PRINCIPAL);
        lblTitulo.setForeground(Visual.COR_PRINCIPAL_TEXTO);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela ---
	        String[] colunas = {"Cliente", "Data", "Hora", "Status"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna a tabela não editável
            }
        };
        tabela = new JTable(tableModel);
        
        // --- Estilização da Tabela ---
        tabela.setFont(Visual.FONTE_TABELA_CORPO);
        tabela.setRowHeight(30);
        tabela.setGridColor(Visual.COR_LINHA_TABELA);
        tabela.setSelectionBackground(Visual.COR_DESTAQUE_CLIENTE_SEL);
        tabela.setSelectionForeground(Color.WHITE);
        
        JTableHeader header = tabela.getTableHeader();
        header.setFont(Visual.FONTE_TABELA_HEADER);
        header.setBackground(Visual.COR_FUNDO_HEADER_);
        header.setOpaque(false);
        
        // Adiciona o listener de clique
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Duplo clique
                    int linha = tabela.getSelectedRow();
                    if (linha != -1) {
                        editarAgendamento(linha);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.getViewport().setBackground(Color.WHITE);
        add(scrollPane, BorderLayout.CENTER);
        
        // --- Botão de Apagar Cliente ---
        JButton btnApagar = new JButton("Apagar Agendamento");
        btnApagar.setFont(Visual.FONTE_FORMULARIO_BOTAO);
        btnApagar.setBackground(Visual.COR_DESTAQUE_CLIENTE_SEL);
        btnApagar.setForeground(Visual.COR_TEXTO_BOTAO_APAGAR_AGENDAMENTO);
        btnApagar.setFocusPainted(false);
        btnApagar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnApagar.putClientProperty("JButton.buttonType", "roundRect");
        
        btnApagar.addActionListener(e -> apagarAgendamento());
        
        JPanel painelBotoes = new JPanel(new BorderLayout());
        painelBotoes.setBackground(Visual.COR_FUNDO_PAINEL);
        painelBotoes.add(btnApagar, BorderLayout.EAST);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        add(painelBotoes, BorderLayout.SOUTH);
        
        atualizarTabela(); // Carrega os dados iniciais
    }

    public static void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa a tabela
        List<Agendamento> agendamentos = GerenciadorDados.getInstance().getAgendamentos();
        
	        for (Agendamento agendamento : agendamentos) {
	            tableModel.addRow(new Object[]{
	                agendamento.getCliente().getNome(),
	                agendamento.getData(),
	                agendamento.getHora(),
	                agendamento.getStatus()
	            });
	        }
    }
    
    private void apagarAgendamento() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um agendamento para apagar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Agendamento agendamento = GerenciadorDados.getInstance().getAgendamentos().get(linha);
        int confirmacao = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja apagar o agendamento do cliente: " + agendamento.getCliente().getNome() + "?", 
            "Confirmar Exclusão", 
            JOptionPane.YES_NO_OPTION);
            
            if (confirmacao == JOptionPane.YES_OPTION) {
                // Remove o agendamento do banco de dados
                GerenciadorDados.getInstance().removerAgendamento(agendamento);
                
                // Atualiza a tabela e o painel de consultorias
                atualizarTabela();
                PainelConsultorias.atualizarPainel();
                
                JOptionPane.showMessageDialog(this, "Agendamento apagado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
    }

    private void editarAgendamento(int linha) {
        Agendamento agendamento = GerenciadorDados.getInstance().getAgendamentos().get(linha);
        
        JDialog dialog = new JDialog();
        dialog.setTitle("Editar Agendamento - " + agendamento.getCliente().getNome());
        dialog.setModal(true);
        dialog.setSize(450, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos de Edição
        JTextField txtData = new JTextField(agendamento.getData(), 15);
        JTextField txtHora = new JTextField(agendamento.getHora(), 15);
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Confirmado", "Pendente", "Cancelado", "Concluído"});
        cmbStatus.setSelectedItem(agendamento.getStatus());
        JTextArea txtAcoes = new JTextArea(agendamento.getAcoesRecomendadas(), 5, 20);
        txtAcoes.setLineWrap(true);
        txtAcoes.setWrapStyleWord(true);
        JScrollPane scrollAcoes = new JScrollPane(txtAcoes);
        
        // Adicionar componentes ao painel
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(new JLabel(agendamento.getCliente().getNome()), gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(txtData, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Hora:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(txtHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(cmbStatus, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.NORTHEAST; painel.add(new JLabel("Ações:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.weighty = 1.0; painel.add(scrollAcoes, gbc);
        
        // Botão Salvar
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.addActionListener(e -> {
            // Validação simples
            if (txtData.getText().isEmpty() || txtHora.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Data e Hora não podem ser vazios.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
             // Atualiza o objeto Agendamento
            agendamento.setData(txtData.getText());
            agendamento.setHora(txtHora.getText());
            agendamento.setStatus((String) cmbStatus.getSelectedItem());
            agendamento.setAcoesRecomendadas(txtAcoes.getText());
            
            // Persiste a alteração no banco de dados
            GerenciadorDados.getInstance().atualizarAgendamento(agendamento);
            
            // Atualiza a tabela na tela
            atualizarTabela();
            
            // Notifica o PainelConsultorias para sincronizar
            PainelConsultorias.atualizarPainel();
            
            dialog.dispose();
        });
        
        gbc.gridx = 1; gbc.gridy = y; gbc.weighty = 0.0; gbc.anchor = GridBagConstraints.EAST; painel.add(btnSalvar, gbc);
        
        dialog.add(painel);
        dialog.setVisible(true);
    }
}

