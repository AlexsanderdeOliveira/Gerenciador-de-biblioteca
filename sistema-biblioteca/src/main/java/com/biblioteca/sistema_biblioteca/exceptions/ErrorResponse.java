package com.biblioteca.sistema_biblioteca.exceptions;
import java.time.LocalDateTime;

public record ErrorResponse (int status, String mensagem, LocalDateTime timestamp) {}
