package com.biblioteca.sistema_biblioteca.infra;
import com.biblioteca.sistema_biblioteca.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            String mensagem = error.getDefaultMessage();
            erros.put(campo, mensagem);
        });
        return erros;
    }

    // Exceptions de EMPRESTIMOS
    @ExceptionHandler(EmprestimoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> emprestimoNaoEncontradoHandler(EmprestimoNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(EmprestimoJaDevolvidoException.class)
    public ResponseEntity<ErrorResponse> emprestimoJaDevolvidoHandler(EmprestimoJaDevolvidoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(400, exception.getMessage(), LocalDateTime.now()));
    }

    // Exceptions de LIVROS
    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> livroNaoEncontradoHandler(LivroNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(LivroIndisponivelException.class)
    public ResponseEntity<ErrorResponse> livroIndisponivelHandler(LivroIndisponivelException exception){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(409, exception.getMessage(), LocalDateTime.now()));
    }

    // Exceptions de USUARIOS
    @ExceptionHandler(UsuarioNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> usuarioNaoEncontradoHandler(UsuarioNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> emailJaCadastradoHandler(EmailJaCadastradoException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(409, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(LimiteEmprestimosException.class)
    public ResponseEntity<ErrorResponse> limiteAtingidoHandler(LimiteEmprestimosException exception) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(new ErrorResponse(422, exception.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> todasExceptionHandler(Exception exception){
        logger.error("Erro Inesperado: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(500, "Erro Interno", LocalDateTime.now()));
    }
}
