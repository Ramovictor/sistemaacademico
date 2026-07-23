package com.cursojava.sistemaacademico.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cursojava.sistemaacademico.service.AlunoService;
import com.cursojava.sistemaacademico.model.Aluno;
import com.cursojava.sistemaacademico.model.Nota;

@RestController
@RequestMapping("/api/alunos")
public class AlunoRestController {

    private final AlunoService alunoService;

    public AlunoRestController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public List<Aluno> listar() {
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aluno> buscarPorId(
            @PathVariable Long id) {

        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(aluno);
    }

    // retirar as notas de uum aluno...
    @GetMapping("{id}/notas")
    public ResponseEntity<List<Nota>> listarNotas(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/media")
    public ResponseEntity<Map<String, Object>> consultarMedia(@PathVariable Long id) {
        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> resposta = Map.of(
                "nome", aluno.getNome(),
                "media", aluno.getMedia(),
                "situacao", aluno.getSituacao());

        return ResponseEntity.ok(resposta);
    }

    // /alunos -> pagina HTML(retorna a lista de alunos em HTML)
    // /api/alunos -> dados em JSON(retorna a lista de alunos em JSON)

    @GetMapping("/quantidade")
    public Map<String, Object> quantCadastrados() {

        return Map.of(
                "quantidade", alunoService.listarTodos().size());
    }

    @GetMapping("/api/alunos/nomes")
    public List<String> listarNomes() {

        List<String> nomes = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            nomes.add(aluno.getNome());
        }

        return nomes;
    }

    @GetMapping("/api/alunos/aprovados")
    public List<String> listarAprovados() {

        List<String> aprovados = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if (aluno.getSituacao().equals("Aprovado")) {
                aprovados.add(aluno.getNome());
            }
        }

        return aprovados;
    }

    @GetMapping("api/alunos/reprovados")
    public List<String> listarReprovados() {

        List<String> reprovados = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if (aluno.getSituacao().equals("Reprovado")) {
                reprovados.add(aluno.getNome());
            }
        }

        return reprovados;
    }

    @GetMapping("api/alunos/sem-notas")
    public List<String> listarAlunosSemNotas() {

        List<String> alunosSemNotas = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {
            if (aluno.getNotas().isEmpty()) {
                alunosSemNotas.add(aluno.getNome());
            }
        }

        return alunosSemNotas;
    }

    @GetMapping("/api/alunos/boletim")
    public List<Map<String, Object>> boletimSimplificado() {

        List<Map<String, Object>> boletim = new ArrayList<>();

        for (Aluno aluno : alunoService.listarTodos()) {

            Map<String, Object> dados = new HashMap<>();

            dados.put("nome", aluno.getNome());
            dados.put("media", aluno.getMedia());
            dados.put("situacao", aluno.getSituacao());

            boletim.add(dados);
        }

        return boletim;
    }

    @GetMapping("api/alunos/{id}/email")
    public Map<String, String> buscarEmail(@PathVariable Long id) {

        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return Map.of("erro", "Aluno não encontrado");
        }

        return Map.of("email", aluno.getEmail());
    }

    @PostMapping
    public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno aluno) {

        if (alunoService.dadosInvalidos(aluno)) {
            return ResponseEntity.badRequest().build();
        }

        Aluno alunoCadastrado = alunoService.cadastrarAluno(
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getMatricula(),
                aluno.getIdade());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alunoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> atualizar(
            @PathVariable Long id,
            @RequestBody Aluno dados) {
        Aluno alunoExistente = alunoService.buscarPorId(id);

        if (alunoExistente == null) {
            return ResponseEntity.notFound().build();
        }

        if (alunoService.dadosInvalidos(dados)) {
            return ResponseEntity.badRequest().build();
        }

        Aluno alunoAtualizado = alunoService.atualizarAluno(
                id, dados);

        return ResponseEntity.ok(alunoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {
        boolean excluiu = alunoService.excluirAluno(id);

        if (!excluiu) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}/notas")
    public @PostMapping("{id}/notas") public ResponseEntity<Nota> cadastrarNota(
            @PathVariable Long id,
            @RequestBody Nota nota) {

        Aluno aluno = alunoService.buscarPorId(id);

        if (aluno == null) {
            return ResponseEntity.notFound().build();
        }

        if (alunoService.notaInvalida(nota)) {
            return ResponseEntity.badRequest().build();
        }

        Nota notaCadastrada = (Nota) alunoService.cadastrarNota(
                id,
                nota.getDescricao(),
                nota.getValor());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(notaCadastrada);
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Aluno>> buscarPorNome(
            @RequestParam String nome) {

        if (nome.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        List<Aluno> encontrados = alunoService.buscarPorNome(nome);

        return ResponseEntity.ok(encontrados);
    }
}

// O AlunoController é responsável por controlar as páginas HTML da aplicação.
// Ele recebe as requisições do navegador, chama o AlunoService,
// envia os dados para o Thymeleaf e retorna o nome da página que será exibida.

// O AlunoRestController é responsável por fornecer dados em formato JSON para
// outras aplicações ou para o frontend.