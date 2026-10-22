package com.biblioteca.sistema_biblioteca.exceptions;

public class LivroNaoEncontradoException extends RuntimeException {

    public LivroNaoEncontradoException() { super("Livro não encontrado."); }

    public LivroNaoEncontradoException(String message) { super(message); }
}
