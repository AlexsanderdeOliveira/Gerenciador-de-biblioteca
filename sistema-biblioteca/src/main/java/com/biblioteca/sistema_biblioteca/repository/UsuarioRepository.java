package com.biblioteca.sistema_biblioteca.repository;
import com.biblioteca.sistema_biblioteca.model.Livro;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsuarioRepository extends JpaRepository <Usuario, Long> {
    boolean existsByEmail(String email);
    @Query("SELECT e FROM Usuario e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Usuario> findByNome(@Param("nome") String nome, Pageable pageable);
}
