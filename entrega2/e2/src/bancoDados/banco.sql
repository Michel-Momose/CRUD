CREATE DATABASE clientesdb;

CREATE TABLE cliente(
    id SERIAL PRIMARY KEY,
    nome VARCHAR (100) NOT NULL,
    idade VARCHAR (3) NOT NULL,
    sexo  VARCHAR (20) NOT NULL,
    profissao VARCHAR (100) NOT NULL,
)