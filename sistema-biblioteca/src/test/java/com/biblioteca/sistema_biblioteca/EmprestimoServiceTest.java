package com.biblioteca.sistema_biblioteca;

import com.biblioteca.sistema_biblioteca.exceptions.LimiteEmprestimosException;
import com.biblioteca.sistema_biblioteca.exceptions.LivroIndisponivelException;
import com.biblioteca.sistema_biblioteca.exceptions.LivroNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.exceptions.UsuarioNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.model.Emprestimo;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import com.biblioteca.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import com.biblioteca.sistema_biblioteca.repository.UsuarioRepository;
import com.biblioteca.sistema_biblioteca.service.EmprestimoService;
import com.biblioteca.sistema_biblioteca.service.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmprestimoServiceTest {
    @Mock
    EmprestimoRepository emprestimoRepository;

    @Mock
    LivroRepository livroRepository;

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    EmprestimoService service;

    Livro livro;
    Usuario usuario;

    @BeforeEach
    void setup() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setDisponivel(true);

        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alexsander");
        usuario.setEmail("alexsander@gmail.com");
        usuario.setTelefone("21999999999");
    }

    @Test
    void deveRealizarEmprestimo() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.countByUsuarioAndStatus(usuario, "ATIVO")).thenReturn(0L);

        Emprestimo emprestimo = new Emprestimo(livro, usuario);
        when(emprestimoRepository.save(any())).thenReturn(emprestimo);

        //ACT
        Emprestimo resultado = service.realizarEmprestimo(1L, 1L);

        //ASSERT
        assertNotNull(resultado);
        verify(emprestimoRepository).save(any());

    }

    @Test
    void deveDarExcecaoLivroIndisponivel() {
        livro.setDisponivel(false);
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        assertThrows(
                LivroIndisponivelException.class,
                () -> service.realizarEmprestimo(1L, 1L)
        );

    }

    @Test
    void deveLancarLimiteAtingido() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(emprestimoRepository.countByUsuarioAndStatus(usuario, "ATIVO")).thenReturn(3L);

        assertThrows(
                LimiteEmprestimosException.class,
                () -> service.realizarEmprestimo(1L, 1L)
        );
    }

    @Test
    void deveRetornarLivroNaoEncontrado() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                LivroNaoEncontradoException.class,
                () -> service.realizarEmprestimo(99L, 1L)
        );
    }

    @Test
    void deveRetornarUsuarioNaoEncontrado() {
        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> service.realizarEmprestimo(1L, 99L)
        );
    }
}
