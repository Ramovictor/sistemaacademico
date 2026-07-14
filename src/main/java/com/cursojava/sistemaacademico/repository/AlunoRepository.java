package com.cursojava.sistemaacademico.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.cursojava.sistemaacademico.model.Aluno;

@Repository
public class AlunoRepository {
    private List<Aluno> alunos = new ArrayList<>();
    private Long proximoId = 1L;

    public List<Aluno> listarTodos() {
        return alunos;
    }

    public Aluno buscarPorId(Long id) {
        for (Aluno aluno : alunos) {
            if (aluno.getId() == id) {
                return aluno;
            }
        }

        return null;
    }

    public Aluno salvar(String nome, String email, String matricula, int idade) {
        Aluno aluno = new Aluno(proximoId, nome, email, matricula, idade);

        alunos.add(aluno);
        proximoId++;

        return aluno;
    }

    public Aluno atualizar(Long id, String nome, String email, String matricula, int idade

    ) {
        Aluno aluno = buscarPorId(id);

        if (aluno == null) {
            return null;
        }

        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setMatricula(matricula);
        aluno.setIdade(idade);

        return aluno;
    }

    public boolean excluir(Long id) {
        Aluno aluno = buscarPorId(id);

        if (aluno == null) {
            return false;
        }

        return alunos.remove(aluno);
    }

    public List<Aluno> buscarPorNome(String nome) {
        List<Aluno> encontrados = new ArrayList<>();
        String nomeProcurado = nome.toLowerCase();

        for (Aluno aluno : alunos) {
            String nomeDoAluno = aluno.getNome().toLowerCase();

            if (nomeDoAluno.contains(nomeProcurado)) {
                encontrados.add(aluno);
            }
        }

        return encontrados;
    }
}
