package com.biblioteca.sistema_biblioteca.exceptions;

public class EmprestimoNaoEncontradoException extends RuntimeException {

    public EmprestimoNaoEncontradoException() { super("Emprestimo não encontrado."); }

    public EmprestimoNaoEncontradoException(String message) { super(message); }
}
