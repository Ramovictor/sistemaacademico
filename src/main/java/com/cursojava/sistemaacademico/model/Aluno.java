package com.cursojava.sistemaacademico.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class Aluno {
    private Long id;
    private String nome;
    private String email;
    private String matricula;
    private int idade;
    private List<Nota> notas = new ArrayList<>();

    public Aluno() {

    }

    public Aluno(Long id, String nome, String email,
            String matricula, int idade) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.matricula = matricula;
        this.idade = idade;
    }

    // setters

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    // getters

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public int getIdade() {
        return idade;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void adicionarNota(Nota nota) {
        notas.add(nota);
    }

    public double getMedia() {
        if (notas.isEmpty()) {
            return 0.0;
        }

        double soma = 0.0;

        for (Nota nota : notas) {
            soma += nota.getValor();
        }

        return soma / notas.size();
    }

    public String getSituacao() {
        if (notas.isEmpty()) {
            return "Sem notas";
        }

        if (getMedia() >= 7.0) {
            return "Aprovado";
        }

        return "Reprovado";
    }

}
