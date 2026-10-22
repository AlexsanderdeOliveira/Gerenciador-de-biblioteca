package com.biblioteca.sistema_biblioteca.controller;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import com.biblioteca.sistema_biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping
    public List<Livro> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/paginado")
    public Page<Livro> listarPaginado (@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)Pageable pageable) {
        return service.listarPaginado(pageable);
    }

    @GetMapping("/disponiveis")
    public List<Livro> listarDisponiveis() {
        return service.listarDisponiveis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/ano")
    public List<Livro> listarPublicacaoMaiorQue(@RequestParam int anoPublicacao) {
        return service.listarPublicacaoMaiorQue(anoPublicacao);
    }

    @GetMapping("/categoria")
        public Page<Livro> listarPorCategoria(@RequestParam String categoria, @PageableDefault(size = 10, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        return service.listarPorCategoria(categoria, pageable);
    }

    @GetMapping("/disponiveis-por-autor")
    public List<Livro> listarAtivosPorAutor(@RequestParam String autor) {
        return service.listarAtivosPorAutor(autor);
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrar(@Valid @RequestBody Livro livro) {
        return ResponseEntity.status(201).body(service.cadastrar(livro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizar(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        return ResponseEntity.ok(service.atualizar(id, livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
