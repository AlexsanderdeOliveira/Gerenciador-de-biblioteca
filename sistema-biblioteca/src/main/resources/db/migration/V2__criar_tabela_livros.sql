CREATE TABLE livros (
    id            BIGSERIAL PRIMARY KEY,
    titulo        VARCHAR(255) NOT NULL,
    autor         VARCHAR(255) NOT NULL,
    isbn          VARCHAR(20)  NOT NULL UNIQUE,
    ano_publicacao INTEGER     NOT NULL,
    disponivel    BOOLEAN      NOT NULL DEFAULT TRUE
)