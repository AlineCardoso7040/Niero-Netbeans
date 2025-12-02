package Niero.model;

public class Agendamento {
    private int id;
    private String data;
    private String hora;
    private Cliente cliente;
    private String cultura;
    private String status;
    private String acoesRecomendadas;

    public Agendamento(int id, String data, String hora, Cliente cliente, String cultura, String status, String acoesRecomendadas) {
        this.id = id;
        this.data = data;
        this.hora = hora;
        this.cliente = cliente;
        this.cultura = cultura;
        this.status = status;
        this.acoesRecomendadas = acoesRecomendadas;
    }

    public Agendamento(String data, String hora, Cliente cliente, String cultura, String status, String acoesRecomendadas) {
        this(-1, data, hora, cliente, cultura, status, acoesRecomendadas);
    }

    // Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public String getHora() {
        return hora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getCultura() {
        return cultura;
    }

    public String getStatus() {
        return status;
    }

    public String getAcoesRecomendadas() {
        return acoesRecomendadas;
    }

    // Setters (para edição)
    public void setData(String data) {
        this.data = data;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAcoesRecomendadas(String acoesRecomendadas) {
        this.acoesRecomendadas = acoesRecomendadas;
    }
}
