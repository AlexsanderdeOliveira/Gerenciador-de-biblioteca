package com.biblioteca.sistema_biblioteca.repository;

import com.biblioteca.sistema_biblioteca.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDisponivel(boolean disponivel);
    List<Livro> findByAutorContaining(String autor);
    List<Livro> findByAnoPublicacaoGreaterThan(int anoPublicacao);
    @Query("SELECT e FROM Livro e WHERE LOWER(e.autor) LIKE LOWER(CONCAT('%', :autor, '%')) AND e.disponivel = true")
    List<Livro> findByAutor(@Param("autor") String autor);
    @Query("SELECT e FROM Livro e WHERE LOWER(e.categoria) LIKE LOWER(CONCAT('%', :categoria, '%'))")
    Page<Livro> findByCategoria(@Param("categoria") String categoria, Pageable pageable);
}
