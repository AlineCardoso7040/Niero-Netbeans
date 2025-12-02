# Projeto Niero - Versão MySQL

Este é o **Projeto Niero** modificado para utilizar **MySQL** como banco de dados ao invés do SQLite.

## 📋 Descrição

O Projeto Niero é uma aplicação Java Desktop para gerenciamento de clientes e agendamentos de consultorias agrícolas. A aplicação permite:

- Cadastrar e gerenciar clientes (nome, CPF/CNPJ, contato, endereço)
- Agendar consultorias com informações de data, hora, cultura e status
- Visualizar e editar agendamentos existentes
- Registrar ações recomendadas para cada consultoria

## 🔄 Modificações Realizadas

Esta versão foi adaptada do SQLite para MySQL, incluindo:

- **Substituição do driver JDBC**: De `sqlite-jdbc` para `mysql-connector-j`
- **Atualização da classe `DatabaseManager.java`**: Configuração de conexão MySQL
- **Adaptação das queries SQL**: Sintaxe compatível com MySQL (ex: `AUTO_INCREMENT` ao invés de `AUTOINCREMENT`)
- **Remoção do arquivo SQLite**: Os dados agora são armazenados no servidor MySQL

## 📦 Estrutura do Projeto

```
ProjetoNiero_MySQL/
├── Niero/
│   ├── data/
│   │   ├── DatabaseManager.java    # Gerenciador de conexão MySQL
│   │   └── GerenciadorDados.java   # Operações CRUD
│   ├── model/
│   │   ├── Cliente.java            # Modelo de Cliente
│   │   └── Agendamento.java        # Modelo de Agendamento
│   ├── ui/
│   │   ├── painels/                # Painéis da interface
│   │   ├── utils/                  # Utilitários visuais
│   │   └── Sidebar.java            # Menu lateral
│   └── TelaPrincipal.java          # Classe principal
├── lib/
│   └── mysql-connector-j-8.2.0.jar # Driver JDBC MySQL
├── INSTRUCOES.md                   # Instruções de configuração
├── INSTALACAO_MYSQL.md             # Guia de instalação do MySQL
├── database.properties             # Arquivo de configuração (referência)
└── README.md                       # Este arquivo
```

## 🚀 Como Usar

### Pré-requisitos

1. **Java JDK 8 ou superior** instalado
2. **MySQL Server** instalado e em execução
3. **IDE Java** (Eclipse, IntelliJ IDEA, NetBeans, etc.)

### Passo a Passo

#### 1. Instalar o MySQL

Se você ainda não tem o MySQL instalado, consulte o arquivo **`INSTALACAO_MYSQL.md`** para instruções detalhadas de instalação no seu sistema operacional.

#### 2. Criar o Banco de Dados

Conecte-se ao MySQL e execute:

```sql
CREATE DATABASE niero_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

#### 3. Configurar as Credenciais

Edite o arquivo `Niero/data/DatabaseManager.java` e altere as seguintes linhas com suas credenciais:

```java
private static final String DB_USER = "root";           // Seu usuário MySQL
private static final String DB_PASSWORD = "sua_senha";  // Sua senha MySQL
```

#### 4. Configurar o Projeto na IDE

- Importe o projeto na sua IDE
- Adicione o arquivo `lib/mysql-connector-j-8.2.0.jar` ao Build Path/Classpath do projeto

#### 5. Executar a Aplicação

Execute a classe `TelaPrincipal.java`. Na primeira execução, as tabelas serão criadas automaticamente no banco de dados.

## 📚 Documentação Adicional

- **`INSTRUCOES.md`**: Instruções completas de configuração e uso do projeto
- **`INSTALACAO_MYSQL.md`**: Guia detalhado de instalação do MySQL para Windows, macOS e Linux
- **`database.properties`**: Arquivo de referência para configurações do banco

## 🗄️ Estrutura do Banco de Dados

### Tabela CLIENTES

| Campo    | Tipo         | Descrição                |
|----------|--------------|--------------------------|
| id       | INT (PK)     | Identificador único      |
| nome     | VARCHAR(255) | Nome do cliente          |
| cpf_cnpj | VARCHAR(20)  | CPF ou CNPJ (único)      |
| contato  | VARCHAR(100) | Telefone/email           |
| endereco | TEXT         | Endereço completo        |

### Tabela AGENDAMENTOS

| Campo               | Tipo        | Descrição                      |
|---------------------|-------------|--------------------------------|
| id                  | INT (PK)    | Identificador único            |
| cliente_id          | INT (FK)    | Referência ao cliente          |
| data                | VARCHAR(10) | Data do agendamento            |
| hora                | VARCHAR(8)  | Hora do agendamento            |
| cultura             | VARCHAR(100)| Tipo de cultura                |
| status              | VARCHAR(50) | Status do agendamento          |
| acoes_recomendadas  | TEXT        | Ações recomendadas             |

## ⚙️ Configurações Avançadas

### Alterar Host ou Porta do MySQL

Se o seu MySQL está em outro servidor ou porta, edite em `DatabaseManager.java`:

```java
private static final String DB_HOST = "localhost";  // Altere para o IP do servidor
private static final String DB_PORT = "3306";       // Altere para a porta correta
```

### Usar Usuário Dedicado (Recomendado)

Para maior segurança, crie um usuário específico para a aplicação:

```sql
CREATE USER 'niero_user'@'localhost' IDENTIFIED BY 'senha_forte';
GRANT ALL PRIVILEGES ON niero_db.* TO 'niero_user'@'localhost';
FLUSH PRIVILEGES;
```

E atualize as credenciais no `DatabaseManager.java`.

## 🐛 Solução de Problemas

### Erro: "Access denied for user"
- Verifique se o usuário e senha estão corretos em `DatabaseManager.java`
- Confirme que o usuário tem permissões no banco `niero_db`

### Erro: "Communications link failure"
- Verifique se o MySQL está rodando: `sudo systemctl status mysql` (Linux) ou verifique os Serviços no Windows
- Confirme que o host e porta estão corretos

### Erro: "Driver not found"
- Certifique-se de que o arquivo `mysql-connector-j-8.2.0.jar` está no Build Path da IDE

### Tabelas não são criadas
- Verifique se o banco `niero_db` existe
- Verifique as mensagens de erro no console da aplicação

## 📝 Notas

- As tabelas são criadas automaticamente na primeira execução
- O charset `utf8mb4` garante suporte completo a caracteres especiais e emojis
- O engine `InnoDB` fornece suporte a transações e chaves estrangeiras
- Índices foram adicionados para melhorar a performance das consultas

## 🤝 Suporte

Se você encontrar problemas ou tiver dúvidas:

1. Consulte os arquivos de documentação incluídos
2. Verifique se todos os pré-requisitos foram atendidos
3. Revise as mensagens de erro no console da aplicação
4. Confirme que o MySQL está instalado e rodando corretamente

---

**Versão**: MySQL Edition  
**Data**: Novembro 2025  
**Modificado por**: Manus AI
