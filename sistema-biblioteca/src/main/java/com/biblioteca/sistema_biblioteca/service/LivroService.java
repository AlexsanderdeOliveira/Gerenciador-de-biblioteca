package com.biblioteca.sistema_biblioteca.service;

import com.biblioteca.sistema_biblioteca.exceptions.LivroNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LivroService {
    private final LivroRepository repository;

    public LivroService(LivroRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Livro> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarDisponiveis() {
        return repository.findByDisponivel(true);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarPublicacaoMaiorQue(int anoPublicacao){
        return repository.findByAnoPublicacaoGreaterThan(anoPublicacao);
    }

    @Transactional(readOnly = true)
    public List<Livro> listarAtivosPorAutor(String autor) {
        return repository.findByAutor(autor);
    }

    @Transactional(readOnly = true)
    public Page<Livro> listarPorCategoria(String categoria, Pageable pageable) {
        return repository.findByCategoria(categoria, pageable);
    }

    @Transactional(readOnly = true)
    public Livro buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new LivroNaoEncontradoException());
    }

    @Transactional
    public Livro cadastrar(Livro livro) {
        return repository.save(livro);
    }

    @Transactional
    public Livro atualizar(Long id, Livro livroAtualizado) {
        Livro existente = buscarPorId(id);
        existente.setTitulo(livroAtualizado.getTitulo());
        existente.setAutor(livroAtualizado.getAutor());
        existente.setIsbn(livroAtualizado.getIsbn());
        existente.setAnoPublicacao(livroAtualizado.getAnoPublicacao());
        existente.setCategoria(livroAtualizado.getCategoria());
        return repository.save(existente);
    }

    @Transactional
    public boolean deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
        return true;
    }

}
