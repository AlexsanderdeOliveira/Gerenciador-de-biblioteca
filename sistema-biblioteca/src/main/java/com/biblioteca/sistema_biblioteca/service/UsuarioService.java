package com.biblioteca.sistema_biblioteca.service;
import com.biblioteca.sistema_biblioteca.exceptions.EmailJaCadastradoException;
import com.biblioteca.sistema_biblioteca.exceptions.UsuarioNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import com.biblioteca.sistema_biblioteca.repository.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {
    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Usuario> listarPaginado(Pageable pageable)  {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Usuario> buscarPorNome(String nome, Pageable pageable) {
        return repository.findByNome(nome, pageable);
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException());
    }

    @Transactional
    public Usuario cadastrar(Usuario usuario) {
        if (repository.existsByEmail(usuario.getEmail())) throw new EmailJaCadastradoException();
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizar(Long id, Usuario usuarioAtualizado) {
        Usuario existente = buscarPorId(id);
        existente.setNome(usuarioAtualizado.getNome());
        existente.setEmail(usuarioAtualizado.getEmail());
        existente.setTelefone(usuarioAtualizado.getTelefone());
        return repository.save(existente);
    }

    @Transactional
    public boolean deletar(Long id) {
        buscarPorId(id);
        repository.deleteById(id);
        return true;
    }
}
