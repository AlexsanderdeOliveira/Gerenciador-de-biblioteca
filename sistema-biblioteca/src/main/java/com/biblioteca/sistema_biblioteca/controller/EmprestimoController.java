package com.biblioteca.sistema_biblioteca.controller;

import com.biblioteca.sistema_biblioteca.dto.RelatorioEmprestimoDTO;
import com.biblioteca.sistema_biblioteca.exceptions.EmprestimoNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.model.Emprestimo;
import com.biblioteca.sistema_biblioteca.service.EmprestimoService;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService service;

    public EmprestimoController(EmprestimoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Emprestimo> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<Emprestimo> listarPaginado(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return service.listarPaginado(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarPorId(@PathVariable Long id) {
        Emprestimo emprestimo = service.buscarPorId(id);
        if (emprestimo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping("/nome")
    public List<Emprestimo> buscarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/vencidos")
    public List<Emprestimo> listarVencidos() {
        return service.listaVencidos();
    }

    @GetMapping("/relatorio")
    public List<RelatorioEmprestimoDTO> listarRelatorio() {
        return service.listarRelatorio();
    }

    @PostMapping("/realizar")
    public ResponseEntity<Emprestimo> realizarEmprestimo(@RequestParam Long livroId, @RequestParam Long usuarioId) {
        Emprestimo emprestimo = service.realizarEmprestimo(livroId, usuarioId);
        return ResponseEntity.status(201).body(emprestimo);
    }

    @PatchMapping("/atualizar-vencidos")
    public List<Emprestimo> atualizarVencidos() {
        return service.atualizarVencidos();
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<Emprestimo> realizarDevolucao(@PathVariable Long id) {
        return ResponseEntity.ok(service.realizarDevolucao(id));
    }
}
