package com.biblioteca.sistema_biblioteca;

import com.biblioteca.sistema_biblioteca.exceptions.*;
import com.biblioteca.sistema_biblioteca.model.Emprestimo;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import com.biblioteca.sistema_biblioteca.repository.EmprestimoRepository;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
import com.biblioteca.sistema_biblioteca.repository.UsuarioRepository;
import com.biblioteca.sistema_biblioteca.service.EmprestimoService;
import com.biblioteca.sistema_biblioteca.service.LivroService;
import com.biblioteca.sistema_biblioteca.service.UsuarioService;
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
public class UsuarioServiceTest {

    @Mock
    UsuarioRepository usuarioRepository;

    @InjectMocks
    UsuarioService service;

    Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNome("Alexsander");
        usuario.setEmail("alexsander@gmail.com");
        usuario.setTelefone("21999999999");
    }

    @Test
    void deveRetornarUsuarioQuandoIdExistir() {
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        Usuario resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Alexsander", resultado.getNome());
    }

    @Test
    void deveRetornarExcecaoQuandoIdNaoExistir() {
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                UsuarioNaoEncontradoException.class,
                () -> service.buscarPorId(99L)
        );
    }

    @Test
    void deveSalvarUsuario() {
        when(usuarioRepository.save(usuario)).thenReturn(usuario);
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(false);

        Usuario resultado = service.cadastrar(usuario);

        assertNotNull(resultado);
        assertEquals("Alexsander", resultado.getNome());
    }

    @Test
    void deveRetornarEmailJaCadastrado() {
        when(usuarioRepository.existsByEmail(usuario.getEmail())).thenReturn(true);

        assertThrows(
                EmailJaCadastradoException.class,
                () -> service.cadastrar(usuario)
        );
    }
}
