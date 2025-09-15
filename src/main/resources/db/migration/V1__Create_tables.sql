CREATE TABLE marca (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE modelo (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE veiculo (
    id UUID PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    data_fabricacao DATE NOT NULL,
    consumo_medio_cidade DECIMAL,
    consumo_medio_rodovias DECIMAL,
    marca_id UUID NOT NULL,
    modelo_id UUID NOT NULL
);

ALTER TABLE veiculo
ADD CONSTRAINT fk_veiculo_marca
FOREIGN KEY (marca_id) REFERENCES marca (id);

ALTER TABLE veiculo
ADD CONSTRAINT fk_veiculo_modelo
FOREIGN KEY (modelo_id) REFERENCES modelo (id);