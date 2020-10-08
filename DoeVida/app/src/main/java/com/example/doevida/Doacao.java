package com.example.doevida;

public class Doacao {

    String nome;
    String centro;
    String data;
    String telefone;
    String email;
    String status;

    public Doacao(String nome, String centro, String data, String telefone, String email, String Status) {
        this.nome = nome;
        this.centro = centro;
        this.data = data;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Doacao{" +
                "nome='" + nome + '\'' +
                ", centro='" + centro + '\'' +
                ", data='" + data + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
