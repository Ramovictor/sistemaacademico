package com.cursojava.sistemaacademico.model;

public class Nota {
    private String descricao;
    private double valor;
    
    public Nota(String descricao, double valor){
        this.descricao = descricao;
        this.valor = valor;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public double getValor() {
        return this.valor;
    }
}
