package com.biblioteca.sistema_biblioteca.dto;

public class RelatorioEmprestimoDTO {
    private String status;
    private Long quantidade;

    public RelatorioEmprestimoDTO(String status, Long quantidade) {
        this.status = status;
        this.quantidade = quantidade;
    }

    public String getStatus() { return status; }
    public Long getQuantidade() { return quantidade; }
}
