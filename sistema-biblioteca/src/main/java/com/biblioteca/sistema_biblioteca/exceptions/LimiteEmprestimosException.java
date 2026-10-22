package com.biblioteca.sistema_biblioteca.exceptions;

public class LimiteEmprestimosException extends RuntimeException{

    public LimiteEmprestimosException() { super("Limite de emprestimo atingido pelo usuario"); }

    public LimiteEmprestimosException(String message) { super(message); }
}
