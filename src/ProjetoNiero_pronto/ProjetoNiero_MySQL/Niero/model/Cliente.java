package Niero.model;

public class Cliente {
    private int id;
    private String nome;
    private String cpfCnpj;
    private String contato;
    private String endereco;

    public Cliente(int id, String nome, String cpfCnpj, String contato, String endereco) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.contato = contato;
        this.endereco = endereco;
    }

    public Cliente(String nome, String cpfCnpj, String contato, String endereco) {
        this(-1, nome, cpfCnpj, contato, endereco);
    }

    // Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getContato() {
        return contato;
    }

    public String getEndereco() {
        return endereco;
    }

    @Override
    public String toString() {
        return nome;
    }
}
