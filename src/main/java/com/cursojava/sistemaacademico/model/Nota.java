package com.cursojava.sistemaacademico.model;

public class Nota {
    private String descricao;
    private double valor;

    public Nota() {
    }

    public Nota(String descricao, double valor) {
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public String setDescricao(String descrição) {
        return this.descricao;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
