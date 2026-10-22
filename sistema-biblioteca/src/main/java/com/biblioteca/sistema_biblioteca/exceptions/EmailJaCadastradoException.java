package com.biblioteca.sistema_biblioteca.exceptions;

public class EmailJaCadastradoException extends RuntimeException{

    public EmailJaCadastradoException() { super("Email ou senha já cadastrado.");}

    public EmailJaCadastradoException(String message) { super(message);}
}
