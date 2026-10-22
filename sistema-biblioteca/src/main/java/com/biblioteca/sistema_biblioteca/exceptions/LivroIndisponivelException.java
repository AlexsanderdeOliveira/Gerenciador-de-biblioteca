package com.biblioteca.sistema_biblioteca.exceptions;

public class LivroIndisponivelException extends RuntimeException{

    public LivroIndisponivelException() { super("Livro indisponivel."); }

    public LivroIndisponivelException(String message) { super(message); }
}
