package com.example.doevida;

public class Doacao {
    String id;
    String nome;
    String centro;
    String data;
    String telefone;
    String email;
    String status;

    public Doacao(String id, String nome, String centro, String data, String telefone, String email, String status) {
        this.id = id;
        this.nome = nome;
        this.centro = centro;
        this.data = data;
        this.telefone = telefone;
        this.email = email;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Doacao{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", centro='" + centro + '\'' +
                ", data='" + data + '\'' +
                ", telefone='" + telefone + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        // 1 - Hemoce Crato
        // 2 - Hemoce Fortaleza
        // 3 - Hemoce Iguatu
        // 4 - Hemoce Juazeiro do Norte
        // 5 - Hemoce Quixadá
        // 6 - Hemoce Sobral

        switch (centro){
            case "1":
                this.centro = "Hemoce Crato";
                break;
            case "2":
                this.centro = "Hemoce Fortaleza";
                break;
            case "3":
                this.centro = "Hemoce Iguatu";
                break;
            case "4":
                this.centro = "Hemoce Juazeiro do Norte";
                break;
            case "5":
                this.centro = "Hemoce Quixadá";
                break;
            case "6":
                this.centro = "Hemoce Sobral";
                break;

            default:
                break;
        }
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

}
