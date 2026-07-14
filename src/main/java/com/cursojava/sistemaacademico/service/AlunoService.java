package com.cursojava.sistemaacademico.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cursojava.sistemaacademico.model.Aluno;
import com.cursojava.sistemaacademico.model.Nota;
import com.cursojava.sistemaacademico.repository.AlunoRepository;

@Service
public class AlunoService {
    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.listarTodos();
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.buscarPorId(id);
    }

    public Aluno cadastrarAluno(String nome, String email, String matricula, int idade) {
        return alunoRepository.salvar(nome, email, matricula, idade);
    }

    public void cadastrarNota(Long alunoId, String descricao, double valor) {
        Aluno aluno = alunoRepository.buscarPorId(alunoId);

        if (aluno != null) {
            Nota nota = new Nota(descricao, valor);
            aluno.adicionarNota(nota);
        }
    }

    public Aluno alunosCadastrados(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alunosCadastrados'");
    }

    public boolean dadosInvalidos(Aluno aluno) {
        return aluno.getNome() == null
                || aluno.getNome().isBlank()
                || aluno.getEmail() == null
                || aluno.getEmail().isBlank()
                || aluno.getMatricula() == null
                || aluno.getMatricula().isBlank()
                || aluno.getIdade() <= 0;
    }

    public List<Aluno> buscarPorNome(String nome) {
        return alunoRepository.buscarPorNome(nome);
    }

    public Aluno atualizarAluno(Long id, Aluno dados) {
        return alunoRepository.atualizar(
                id,
                dados.getNome(),
                dados.getEmail(),
                dados.getMatricula(),
                dados.getIdade());
    }

    public boolean excluirAluno(Long id) {
        return alunoRepository.excluir(id);
    }
}
