CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- 2. Cria a tabela de Filmes usando a função v4
CREATE TABLE filmes (
                        idd UUID DEFAULT uuid_generate_v4(), -- Mudança aqui!
                        nome VARCHAR(255) NOT NULL,
                        genero VARCHAR(255),
                        duracao VARCHAR(255),
                        faixa_etaria INTEGER,
                        valor_ingresso NUMERIC(10, 2),
                        CONSTRAINT pk_filmes PRIMARY KEY (idd)
);

CREATE table assentos(
    idd BIGSERIAL NOT NULL,
    numero INTEGER NOT NULL,
    ocupado BOOLEAN DEFAULT FALSE,
    filme_id UUID,
    CONSTRAINT pk_assentos PRIMARY KEY (idd),
    CONSTRAINT fk_assentos_filme FOREIGN KEY (filme_id) REFERENCES filmes(idd)
);

CREATE table compras(
    idd UUID NOT NULL,
    nome_comprador VARCHAR(255),
    horario VARCHAR(255),
    status VARCHAR(250),
    assento_id BIGINT,
    mensagem_erro VARCHAR(255),
    CONSTRAINT pk_compras PRIMARY KEY (idd),
    CONSTRAINT fk_compra_assento FOREIGN KEY (assento_id) REFERENCES assentos(idd)
);

INSERT INTO filmes (nome, genero, duracao, faixa_etaria, valor_ingresso)
VALUES ('Batman', 'Ação/Drama', '2 horas', 16, 12.20);