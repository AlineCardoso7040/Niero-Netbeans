package Niero.data;

import Niero.model.Agendamento;
import Niero.model.Cliente;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GerenciadorDados {
    private static GerenciadorDados instance;
    private GerenciadorDados() {
        DatabaseManager.initialize();
    }

    public static GerenciadorDados getInstance() {
        if (instance == null) {
            instance = new GerenciadorDados();
        }
        return instance;
    }

    public List<Cliente> getClientes() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id, nome, cpf_cnpj, contato, endereco FROM CLIENTES";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("cpf_cnpj"),
                        rs.getString("contato"),
                        rs.getString("endereco")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return clientes;
    }

    public Cliente adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO CLIENTES(nome, cpf_cnpj, contato, endereco) VALUES(?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, cliente.getNome());
            pstmt.setString(2, cliente.getCpfCnpj());
            pstmt.setString(3, cliente.getContato());
            pstmt.setString(4, cliente.getEndereco());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return cliente;
    }

    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT id, nome, cpf_cnpj, contato, endereco FROM CLIENTES WHERE id = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf_cnpj"),
                            rs.getString("contato"),
                            rs.getString("endereco"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Agendamento> getAgendamentos() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT id, cliente_id, data, hora, cultura, status, acoes_recomendadas FROM AGENDAMENTOS";

        try (Connection conn = DatabaseManager.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = buscarClientePorId(rs.getInt("cliente_id"));
                if (cliente != null) {
                    agendamentos.add(new Agendamento(
                            rs.getInt("id"),
                            rs.getString("data"),
                            rs.getString("hora"),
                            cliente,
                            rs.getString("cultura"),
                            rs.getString("status"),
                            rs.getString("acoes_recomendadas")));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return agendamentos;
    }

    public Agendamento adicionarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO AGENDAMENTOS(cliente_id, data, hora, cultura, status, acoes_recomendadas) VALUES(?,?,?,?,?,?)";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, agendamento.getCliente().getId());
            pstmt.setString(2, agendamento.getData());
            pstmt.setString(3, agendamento.getHora());
            pstmt.setString(4, agendamento.getCultura());
            pstmt.setString(5, agendamento.getStatus());
            pstmt.setString(6, agendamento.getAcoesRecomendadas());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    agendamento.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return agendamento;
    }

    public void atualizarAgendamento(Agendamento agendamento) {
        String sql = "UPDATE AGENDAMENTOS SET data = ?, hora = ?, status = ?, acoes_recomendadas = ? WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, agendamento.getData());
            pstmt.setString(2, agendamento.getHora());
            pstmt.setString(3, agendamento.getStatus());
            pstmt.setString(4, agendamento.getAcoesRecomendadas());
            pstmt.setInt(5, agendamento.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removerAgendamento(Agendamento agendamento) {
        String sql = "DELETE FROM AGENDAMENTOS WHERE id = ?";

        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, agendamento.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Cliente buscarClientePorNome(String nome) {
        String sql = "SELECT id, nome, cpf_cnpj, contato, endereco FROM CLIENTES WHERE nome = ?";
        try (Connection conn = DatabaseManager.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nome);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("cpf_cnpj"),
                            rs.getString("contato"),
                            rs.getString("endereco"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
