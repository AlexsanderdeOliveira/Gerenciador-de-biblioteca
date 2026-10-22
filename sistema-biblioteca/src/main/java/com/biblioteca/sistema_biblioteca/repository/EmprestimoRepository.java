package com.biblioteca.sistema_biblioteca.repository;

import com.biblioteca.sistema_biblioteca.dto.RelatorioEmprestimoDTO;
import com.biblioteca.sistema_biblioteca.model.Emprestimo;
import com.biblioteca.sistema_biblioteca.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByUsuario(Usuario usuario);
    long countByUsuarioAndStatus(Usuario usuario, String status);
    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'ATIVO' AND e.dataDevolucao < CURRENT_DATE")
    List<Emprestimo> findEmprestimosVencidos();
    @Query("SELECT e FROM Emprestimo e WHERE e.usuario.nome = :nome")
    List<Emprestimo> findByUsuarioNome(@Param("nome") String nome);
    @Query("SELECT e FROM Emprestimo e WHERE e.status = 'VENCIDO'")
    List<Emprestimo> findEmprestimosStatusVencido();
    @Query("SELECT new com.biblioteca.sistema_biblioteca.dto.RelatorioEmprestimoDTO(e.status, COUNT(e)) FROM Emprestimo e GROUP BY e.status")
    List<RelatorioEmprestimoDTO> findByRelatorio();
}
