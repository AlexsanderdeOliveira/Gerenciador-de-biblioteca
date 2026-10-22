package com.biblioteca.sistema_biblioteca;

import com.biblioteca.sistema_biblioteca.exceptions.LivroNaoEncontradoException;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.repository.LivroRepository;
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
class LivroServiceTest {

    @Mock
    LivroRepository repository;

    @InjectMocks
    LivroService service;

    Livro livro;

    @BeforeEach
    void setup() {
        livro = new Livro();
        livro.setId(1L);
        livro.setTitulo("Clean Code");
        livro.setAutor("Robert C. Martin");
        livro.setDisponivel(true);
    }

    @Test
    void deveRetornarLivroQuandoIdExistir() {
        //ARRANGE
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        // ACT
        Livro resultado = service.buscarPorId(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Clean Code", resultado.getTitulo());
    }

    @Test
    void deveLancarExcecaoQuandoIdNaoExistir() {
        // ARRANGE
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ACT + ASSERT
        assertThrows(
                LivroNaoEncontradoException.class,
                () -> service.buscarPorId(99L)
        );
    }

    @Test
    void deveSalvarLivro() {
        // ARRANGE
        when(repository.save(livro)).thenReturn(livro);

        // ACT
        Livro resultado = service.cadastrar(livro);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Clean Code", resultado.getTitulo());
    }

    @Test
    void deveDeletarLivro() {
        // ARRANGE
        when(repository.findById(1L)).thenReturn(Optional.of(livro));

        // ACT
        boolean resultado = service.deletar(1L);

        // ASSERT
        assertTrue(resultado);
        verify(repository).deleteById(1L); // Confirma se Deletou

    }

    @Test
    void deveRetornarFalseQuandoNaoEncontrarId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                LivroNaoEncontradoException.class,
                () -> service.deletar(99L)
        );
    }

    @Test
    void deveAtualizarLivro() {
        when(repository.findById(1L)).thenReturn(Optional.of(livro));
        Livro livroAtualizado = new Livro();
        livroAtualizado.setTitulo("Clean Architecture");
        when(repository.save(any())).thenReturn(livroAtualizado);

        Livro resultado = service.atualizar(1L, livroAtualizado);

        assertNotNull(resultado);
        assertEquals("Clean Architecture", resultado.getTitulo());
    }

    @Test
    void deveRetornarLivros() {
        when(repository.findAll()).thenReturn(List.of(livro));

        List<Livro> resultado = service.listarTodos();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }
}
