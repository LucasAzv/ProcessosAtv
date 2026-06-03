# ProcessosAtv

Script para o Banco de dados:
CREATE TABLE produto (
id BIGSERIAL PRIMARY KEY,
nome VARCHAR(100) NOT NULL,
quantidade INTEGER NOT NULL DEFAULT 0,
qtd_max INTEGER NOT NULL,
qtd_min INTEGER NOT NULL,
valor NUMERIC(10,2) NOT NULL,
validade DATE NOT NULL
);
