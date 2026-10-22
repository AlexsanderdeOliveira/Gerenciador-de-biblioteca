CREATE TABLE emprestimos (
    id             BIGSERIAL PRIMARY KEY,
    livros_id      BIGINT NOT NULL,
    usuarios_id    BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao  DATE,
    status         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_livro    FOREIGN KEY (livros_id)   REFERENCES livros(id),
    CONSTRAINT fk_usuario  FOREIGN KEY (usuarios_id) REFERENCES usuarios(id)
);