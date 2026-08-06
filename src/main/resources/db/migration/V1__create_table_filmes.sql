CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE filmes (
    id UUID DEFAULT uuid_generate_v4(),
    nome VARCHAR(255) NOT NULL,
    genero VARCHAR(255),
    duracao VARCHAR(255),
    faixa_etaria INTEGER,
    valor_ingresso NUMERIC(10, 2),
    CONSTRAINT pk_filmes PRIMARY KEY (id)
);

CREATE TABLE salas (
    id UUID DEFAULT uuid_generate_v4(),
    nome_sala VARCHAR(100) NOT NULL,
    filme_id UUID,
    CONSTRAINT pk_salas PRIMARY KEY (id),
    CONSTRAINT fk_sala_filme FOREIGN KEY (filme_id) REFERENCES filmes(id)
);

CREATE TABLE assentos (
    id UUID DEFAULT uuid_generate_v4(),
    numero_visivel INTEGER NOT NULL,
    ocupado BOOLEAN DEFAULT FALSE,
    sala_id UUID NOT NULL,
    CONSTRAINT pk_assentos PRIMARY KEY (id),
    CONSTRAINT fk_assentos_sala FOREIGN KEY (sala_id) REFERENCES salas(id)
);

CREATE TABLE produtos (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2) NOT NULL,
    imagem_url VARCHAR(250),
    categoria VARCHAR(50),
    estoque INT DEFAULT 0
);

CREATE TABLE compras (
    id UUID DEFAULT uuid_generate_v4(),
    nome_comprador VARCHAR(255),
    horario VARCHAR(255),
    status VARCHAR(250),
    filme_id UUID,
    sala_id UUID,
    valor_total DECIMAL(10,2) DEFAULT 0.00,
    mensagem_erro VARCHAR(255),
    CONSTRAINT pk_compras PRIMARY KEY (id),
    CONSTRAINT fk_compras_sala FOREIGN KEY (sala_id) REFERENCES public.salas(id),
    CONSTRAINT fk_compras_filme FOREIGN KEY (filme_id) REFERENCES public.filmes(id)
);


CREATE TABLE compra_assento (
    compra_id UUID NOT NULL,
    assento_id UUID NOT NULL,
    CONSTRAINT pk_compra_assento PRIMARY KEY (compra_id, assento_id),
    CONSTRAINT fk_compra_assento_compra FOREIGN KEY (compra_id) REFERENCES compras(id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_assento_assento FOREIGN KEY (assento_id) REFERENCES assentos(id)
);

CREATE TABLE compra_produtos (
    id UUID DEFAULT uuid_generate_v4(),
    compra_id UUID NOT NULL,
    produto_id UUID NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    preco_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_compra_produtos PRIMARY KEY (id),
    CONSTRAINT fk_compra_produtos_compra FOREIGN KEY (compra_id) REFERENCES compras(id) ON DELETE CASCADE,
    CONSTRAINT fk_compra_produtos_produto FOREIGN KEY (produto_id) REFERENCES produtos(id)
);


INSERT INTO filmes (nome, genero, duracao, faixa_etaria, valor_ingresso) VALUES
    ('Velozes e Furiosos', 'Ação/Drama', '3 horas', 16, 15.00),
    ('Carros', 'Animação', '2 horas', 0, 12.00);

INSERT INTO salas (nome_sala, filme_id) VALUES
    ('SALA 01 - IMAX', (SELECT id FROM filmes WHERE nome = 'Velozes e Furiosos' LIMIT 1)),
    ('SALA 02 - VIP', (SELECT id FROM filmes WHERE nome = 'Velozes e Furiosos' LIMIT 1));

INSERT INTO assentos (numero_visivel, ocupado, sala_id) VALUES
    (1, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (2, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (3, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (4, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (5, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (6, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (7, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (8, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (9, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (10, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (11, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (12, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (13, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (14, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (15, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (16, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (17, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (18, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (19, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
    (20, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1));

INSERT INTO assentos (numero_visivel, ocupado, sala_id) VALUES
    (1, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (2, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (3, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (4, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (5, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (6, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (7, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (8, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (9, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1)),
    (10, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 02 - VIP' LIMIT 1));

INSERT INTO produtos (nome, descricao, preco, categoria, estoque) VALUES
    ('Pipoca de Cinema G', 'Pipoca salgada quentinha na manteiga', 25.00, 'PIPOCA', 100),
    ('Refrigerante 700ml', 'Refrigerante gelado', 14.00, 'BEBIDA', 200),
    ('Chocolate com amendoim', 'tradicional', 12.00, 'DOCE', 150);