package com.kadosh;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    public Long id;
    public String usuario;
    public String geracao;
    public String sala;
    public LocalDate data; // Assumindo o formato yyyy-MM-dd
    public String periodo;
    public String motivo;

    // Getters e setters (pode gerar automaticamente com o IDE ou usar Lombok)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getGeracao() { return geracao; }
    public void setGeracao(String geracao) { this.geracao = geracao; }

    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}

