package com.kadosh;

public class Reserva {
    public int id;
    public String sala;
    public String data;
    public String periodo;
    public String usuario;
    public String geracao;

    // Construtor vazio (necessário para deserialização)
    public Reserva() {
    }

    public Reserva(int id, String sala, String data, String periodo, String usuario, String geracao) {
        this.id = id;
        this.sala = sala;
        this.data = data;
        this.periodo = periodo;
        this.usuario = usuario;
        this.geracao = geracao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getGeracao() {
        return geracao;
    }

    public void setGeracao(String geracao) {
        this.geracao = geracao;
    }
}
