package com.biblioteca.sistema_biblioteca.service;
import com.biblioteca.sistema_biblioteca.dto.RelatorioEmprestimoDTO;
import com.biblioteca.sistema_biblioteca.exceptions.*;
import com.biblioteca.sistema_biblioteca.model.Emprestimo;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import com.biblioteca.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import com.biblioteca.sistema_biblioteca.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, LivroRepository livroRepository, UsuarioRepository usuarioRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.livroRepository = livroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Emprestimo> listarPaginado(Pageable pageable) {
        return emprestimoRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> listaVencidos() {
        return emprestimoRepository.findEmprestimosStatusVencido();
    }

    @Transactional(readOnly = true)
    public Emprestimo buscarPorId(Long id) {
        return emprestimoRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Emprestimo> buscarPorNome (String nome) {
        return emprestimoRepository.findByUsuarioNome(nome);
    }

    @Transactional(readOnly = true)
    public List<RelatorioEmprestimoDTO> listarRelatorio() {
        return emprestimoRepository.findByRelatorio();
    }

    @Transactional
    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId) {
        Livro livro = livroRepository.findById(livroId).orElseThrow(() -> new LivroNaoEncontradoException());
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow(() -> new UsuarioNaoEncontradoException());

        if (!livro.isDisponivel()) throw new LivroIndisponivelException();
        if (emprestimoRepository.countByUsuarioAndStatus(usuario, "ATIVO") >= 3) throw new LimiteEmprestimosException();

        livro.setDisponivel(false);
        livroRepository.save(livro);

        return emprestimoRepository.save(new Emprestimo(livro, usuario));
    }

    @Transactional
    public List<Emprestimo> atualizarVencidos () {
        List<Emprestimo> vencidos = emprestimoRepository.findEmprestimosVencidos();
        vencidos.forEach(vencido -> vencido.setStatus("VENCIDO"));
        return emprestimoRepository.saveAll(vencidos);
    }

    @Transactional
    public Emprestimo realizarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = buscarPorId(emprestimoId);
        if (emprestimo == null) throw new EmprestimoNaoEncontradoException();
        if (emprestimo.getStatus().equals("DEVOLVIDO")) throw new EmprestimoJaDevolvidoException();

        emprestimo.setStatus("DEVOLVIDO");
        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.getLivro().setDisponivel(true);
        livroRepository.save(emprestimo.getLivro());

        return emprestimoRepository.save(emprestimo);
    }
}
