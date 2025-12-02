package Niero.ui.painels;

import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.JDialog;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import Niero.ui.utils.Visual;
import Niero.data.GerenciadorDados;
import Niero.model.Cliente;
import Niero.model.Agendamento;
import Niero.ui.painels.PainelAgenda;
import Niero.ui.painels.PainelConsultorias;

public class PainelCadastrarCliente extends JPanel {

    private JTextField txtNome;
    private JTextField txtCpfCnpj;
    private JTextField txtContato;
    private JTextField txtEndereco;

    public PainelCadastrarCliente() {
        super(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Visual.COR_FUNDO_PAINEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- Título do Formulário ---
        JLabel lblTitulo = new JLabel("Cadastro de Novo Cliente");
        lblTitulo.setFont(Visual.FONTE_TITULO_PRINCIPAL);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 20, 8); // Aumenta margem inferior
        add(lblTitulo, gbc);

        // Reset GBC
        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 8, 8, 8);

        // --- Campos do Formulário ---
        txtNome = adicionarCampoFormulario(this, gbc, "Nome do Cliente/Fazenda:", 1);
        txtCpfCnpj = adicionarCampoFormulario(this, gbc, "CPF/CNPJ:", 2);
        txtContato = adicionarCampoFormulario(this, gbc, "Contato (Telefone):", 3);
        txtEndereco = adicionarCampoFormulario(this, gbc, "Endereço da Propriedade:", 4);

        // --- Botão de Salvar ---
        gbc.gridy = 5;
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        JButton btnSalvar = new JButton("Salvar Cliente");
        btnSalvar.setFont(Visual.FONTE_FORMULARIO_BOTAO);
        btnSalvar.setBackground(Visual.COR_DESTAQUE_CLIENTE_SEL);
        btnSalvar.setForeground(Visual.COR_TEXTO_BOTAO_APAGAR_AGENDAMENTO);
        btnSalvar.setFocusPainted(false);
        btnSalvar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btnSalvar.putClientProperty("JButton.buttonType", "roundRect");
        add(btnSalvar, gbc);
        
        // Adiciona um espaçamento vertical para empurrar o conteúdo para o topo
        gbc.gridy = 6;
        gbc.weighty = 1.0;
        add(new JLabel(""), gbc);
        
        btnSalvar.addActionListener(e -> salvarCliente());
    }
    
    private void salvarCliente() {
        String nome = txtNome.getText();
        String cpfCnpj = txtCpfCnpj.getText();
        String contato = txtContato.getText();
        String endereco = txtEndereco.getText();
        
        if (nome.isEmpty() || cpfCnpj.isEmpty() || contato.isEmpty() || endereco.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Cria o novo cliente
        Cliente novoCliente = new Cliente(nome, cpfCnpj, contato, endereco);
        
        // Salva no Gerenciador de Dados (o método retorna o cliente com o ID gerado)
        Cliente clienteSalvo = GerenciadorDados.getInstance().adicionarCliente(novoCliente);
        
        JOptionPane.showMessageDialog(this, "Cliente " + nome + " salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        
        // Limpar campos
        txtNome.setText("");
        txtCpfCnpj.setText("");
        txtContato.setText("");
        txtEndereco.setText("");
        
        // Pergunta se deseja criar um agendamento agora
        int resposta = JOptionPane.showConfirmDialog(this, 
            "Deseja criar um agendamento/consultoria para este cliente agora?", 
            "Criar Agendamento", 
            JOptionPane.YES_NO_OPTION);
        
        if (resposta == JOptionPane.YES_OPTION) {
            abrirDialogoAgendamento(clienteSalvo);
        }
    }
    
    private void abrirDialogoAgendamento(Cliente cliente) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Novo Agendamento - " + cliente.getNome());
        dialog.setModal(true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);
        
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Campos
        JTextField txtData = new JTextField(15);
        JTextField txtHora = new JTextField(15);
        JTextField txtCultura = new JTextField(15);
        JComboBox<String> cmbStatus = new JComboBox<>(new String[]{"Agendado", "Pendente", "Confirmado", "Concluído", "Cancelado"});
        JTextArea txtAcoes = new JTextArea(5, 20);
        txtAcoes.setLineWrap(true);
        txtAcoes.setWrapStyleWord(true);
        JScrollPane scrollAcoes = new JScrollPane(txtAcoes);
        
        // Adicionar componentes ao painel
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(new JLabel(cliente.getNome()), gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Data (dd/mm/aaaa):"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(txtData, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Hora (hh:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(txtHora, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Cultura:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(txtCultura, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.EAST; painel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.anchor = GridBagConstraints.WEST; painel.add(cmbStatus, gbc);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.anchor = GridBagConstraints.NORTHEAST; painel.add(new JLabel("Ações Recomendadas:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; gbc.weighty = 1.0; painel.add(scrollAcoes, gbc);
        
        // Botões
        JPanel painelBotoes = new JPanel();
        JButton btnSalvar = new JButton("Salvar Agendamento");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnSalvar.addActionListener(e -> {
            if (txtData.getText().isEmpty() || txtHora.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Data e Hora são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Agendamento novoAgendamento = new Agendamento(
                txtData.getText(), 
                txtHora.getText(), 
                cliente, 
                txtCultura.getText(), 
                (String) cmbStatus.getSelectedItem(), 
                txtAcoes.getText()
            );
            
            GerenciadorDados.getInstance().adicionarAgendamento(novoAgendamento);
            PainelAgenda.atualizarTabela();
            PainelConsultorias.atualizarPainel();
            
            JOptionPane.showMessageDialog(dialog, "Agendamento criado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        btnCancelar.addActionListener(e -> dialog.dispose());
        
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2; gbc.weighty = 0.0; gbc.anchor = GridBagConstraints.CENTER;
        painel.add(painelBotoes, gbc);
        
        dialog.add(painel);
        dialog.setVisible(true);
    }
    
    /**
     * Helper para adicionar um par de Label-TextField a um painel GridBagLayout.
     */
    private JTextField adicionarCampoFormulario(JPanel painel, GridBagConstraints gbc, String label, int yPos) {
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel lbl = new JLabel(label);
        lbl.setFont(Visual.FONTE_FORMULARIO_LABEL);
        painel.add(lbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.anchor = GridBagConstraints.WEST;
        JTextField txt = new JTextField(30);
        txt.putClientProperty("JComponent.roundRect", true);
        txt.setFont(Visual.FONTE_FORMULARIO_LABEL);
        painel.add(txt, gbc);
        return txt;
    }
}
