CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

	-- 3. CRIAÇÃO DAS TABELAS (Na ordem correta de dependência)

CREATE TABLE filmes (
                        id UUID DEFAULT uuid_generate_v4(),
                        nome VARCHAR(255) NOT NULL,
                        genero VARCHAR(255),
                        duracao VARCHAR(255),
                        faixa_etaria INTEGER,
                        valor_ingresso NUMERIC(10, 2),
                        CONSTRAINT pk_filmes PRIMARY KEY (id)
);

-- Salas vem ANTES de assentos, pois assentos depende de salas
CREATE TABLE salas (
                       id UUID DEFAULT uuid_generate_v4(),
                       nome_sala VARCHAR(100) NOT NULL,
                       filme_id UUID,
                       CONSTRAINT pk_salas PRIMARY KEY (id),
                       CONSTRAINT fk_sala_filme FOREIGN KEY (filme_id) REFERENCES filmes(id)
);

CREATE TABLE assentos (
                          id BIGSERIAL NOT NULL,
                          numero_visivel INTEGER NOT NULL,
                          ocupado BOOLEAN DEFAULT FALSE,
                          sala_id UUID NOT NULL, -- Correto, mapeando para a sala
                          CONSTRAINT pk_assentos PRIMARY KEY (id),
                          CONSTRAINT fk_assentos_sala FOREIGN KEY (sala_id) REFERENCES salas(id)
);

CREATE TABLE compras (
                         id UUID NOT NULL,
                         nome_comprador VARCHAR(255),
                         horario VARCHAR(255),
                         status VARCHAR(250),
                         assento_id BIGINT,
                         filmes_id UUID,
                         mensagem_erro VARCHAR(255),
                         CONSTRAINT pk_compras PRIMARY KEY (id),
                         CONSTRAINT fk_compra_assento FOREIGN KEY (assento_id) REFERENCES assentos(id),
                         CONSTRAINT fk_compra_filme FOREIGN KEY (filmes_id) REFERENCES filmes(id)
);

-- =========================================================================
-- 4. INSERTS NA ORDEM LÓGICA (Primeiro Filmes -> Depois Salas -> Depois Assentos)
-- =========================================================================

-- Passo A: Cadastra os Filmes primeiro
INSERT INTO filmes (nome, genero, duracao, faixa_etaria, valor_ingresso) VALUES
                                                                             ('Velozes e Furiosos', 'Ação/Drama', '3 horas', 16, 15.00),
                                                                             ('Carros', 'Animação', '2 horas', 0, 12.00);

-- Passo B: Cadastra as Salas vinculando aos filmes que ACABARAM de ser criados
INSERT INTO salas (nome_sala, filme_id) VALUES
    ('SALA 01 - IMAX', (SELECT id FROM filmes WHERE nome = 'Velozes e Furiosos' LIMIT 1)),
	('SALA 02 - VIP', (SELECT id FROM filmes WHERE nome = 'Carros' LIMIT 1));

-- Passo C: Cadastra os Assentos vinculando-os à SALA e usando a coluna correta (sala_id)
INSERT INTO assentos (numero_visivel, ocupado, sala_id) VALUES
    (1, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(2, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(3, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(4, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(5, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(6, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(7, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1)),
	(8, false, (SELECT id FROM salas WHERE nome_sala = 'SALA 01 - IMAX' LIMIT 1));