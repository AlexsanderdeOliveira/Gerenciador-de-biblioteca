package com.biblioteca.sistema_biblioteca.exceptions;

public class UsuarioNaoEncontradoException extends RuntimeException{

    public UsuarioNaoEncontradoException() { super("Usuario não encontrado."); }

    public UsuarioNaoEncontradoException(String message) { super(message);}
}
