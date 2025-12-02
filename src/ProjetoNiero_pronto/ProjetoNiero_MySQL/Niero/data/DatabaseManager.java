package Niero.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // Configurações do MySQL - ALTERE CONFORME SEU AMBIENTE
    private static final String DB_HOST = "localhost";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "niero_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "14789632asf";
    
    private static final String URL = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME 
            + "?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Carrega o driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Cria conexão com o banco de dados
            conn = DriverManager.getConnection(URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Driver MySQL não encontrado: " + e.getMessage());
        }
        return conn;
    }

    public static void createNewDatabase() {
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            if (conn != null) {
                // Tabela CLIENTES
                String sqlClientes = "CREATE TABLE IF NOT EXISTS CLIENTES (\n"
                        + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                        + " nome VARCHAR(255) NOT NULL,\n"
                        + " cpf_cnpj VARCHAR(20) NOT NULL UNIQUE,\n"
                        + " contato VARCHAR(100),\n"
                        + " endereco TEXT\n"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
                stmt.execute(sqlClientes);

                // Tabela AGENDAMENTOS
                String sqlAgendamentos = "CREATE TABLE IF NOT EXISTS AGENDAMENTOS (\n"
                        + " id INT AUTO_INCREMENT PRIMARY KEY,\n"
                        + " cliente_id INT NOT NULL,\n"
                        + " data VARCHAR(10) NOT NULL,\n"
                        + " hora VARCHAR(8) NOT NULL,\n"
                        + " cultura VARCHAR(100),\n"
                        + " status VARCHAR(50),\n"
                        + " acoes_recomendadas TEXT,\n"
                        + " FOREIGN KEY (cliente_id) REFERENCES CLIENTES(id) ON DELETE CASCADE,\n"
                        + " INDEX idx_cliente_id (cliente_id),\n"
                        + " INDEX idx_data (data)\n"
                        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;";
                stmt.execute(sqlAgendamentos);
                
                System.out.println("Tabelas criadas ou já existentes no MySQL.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }
    
    public static void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            createNewDatabase();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC driver não encontrado: " + e.getMessage());
        }
    }
    
    // Método auxiliar para testar a conexão
    public static boolean testConnection() {
        try (Connection conn = connect()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexão com MySQL estabelecida com sucesso!");
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Falha ao testar conexão: " + e.getMessage());
        }
        return false;
    }
}
