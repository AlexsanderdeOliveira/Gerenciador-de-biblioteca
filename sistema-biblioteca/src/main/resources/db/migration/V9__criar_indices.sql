-- Otimiza buscas por autor via findByAutorContaining e JPQL
CREATE INDEX IF NOT EXISTS idx_livros_autor ON livros(autor);
-- Otimiza buscas por autor titulo - uso frequente pela biblioteca
CREATE INDEX IF NOT EXISTS idx_livros_titulo ON livros(titulo);
-- Otimiza buscas por categoria - novo endpoint GET /livros/categoria
CREATE INDEX IF NOT EXISTS idx_livros_categoria ON livros(categoria);
-- Otimiza filtros por status - usado em CountByStatus e relatorio
CREATE INDEX IF NOT EXISTS idx_emprestimos_status ON emprestimos(status);