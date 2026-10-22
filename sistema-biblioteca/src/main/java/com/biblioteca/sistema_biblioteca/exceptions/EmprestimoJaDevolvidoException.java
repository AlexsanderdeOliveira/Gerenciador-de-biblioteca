package com.biblioteca.sistema_biblioteca.exceptions;

public class EmprestimoJaDevolvidoException extends RuntimeException{

    public EmprestimoJaDevolvidoException() { super("Emprestimo já devolvido."); }

    public EmprestimoJaDevolvidoException(String message) { super(message); }
}
