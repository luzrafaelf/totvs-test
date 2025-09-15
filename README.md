# Read-me

Projeto de testes criado para TOTVS

# Sistema de Veículos Fictious Clean

## Lombok

Neste projeto foi "apimentado" com lombok.

## Swagger

Neste projeto foi usado swagger, o link para o acesso fica em: http://{URL_APLICACAO}/swagger-ui/index.html#/

## Testes unitários

Foram utilizados Mockito e Junit 5 para testes unitários, cobrindo os serviços implementados.

## Base de dados


### Flyway

Sistema configurado para o banco de dados postgreSQL.
Ao iniciar a aplicação, o flyway irá criar automaticamente as tabelas.

### SQL com dados de teste para a aplicação
```
INSERT INTO marca (id, nome) VALUES
(gen_random_uuid(), 'Japonesa'),
(gen_random_uuid(), 'Alemã'),
(gen_random_uuid(), 'Italiana');

INSERT INTO modelo (id, nome) VALUES
(gen_random_uuid(), 'Popular'),
(gen_random_uuid(), 'Quinhentos'),
(gen_random_uuid(), 'Comprido'),
(gen_random_uuid(), 'Largo');

INSERT INTO veiculo (id, nome, data_fabricacao, consumo_medio_cidade, consumo_medio_rodovias, marca_id, modelo_id) VALUES
(gen_random_uuid(), 'Vendas 1', '2016-03-10', 8.5, 12.0, (SELECT id FROM marca WHERE nome = 'Japonesa'), (SELECT id FROM modelo WHERE nome = 'Comprido')),
(gen_random_uuid(), 'Vendas 2', '2019-07-25', 9.2, 13.5, (SELECT id FROM marca WHERE nome = 'Japonesa'), (SELECT id FROM modelo WHERE nome = 'Comprido')),
(gen_random_uuid(), 'Manutenção 1', '2015-05-18', 8.0, 11.8, (SELECT id FROM marca WHERE nome = 'Alemã'), (SELECT id FROM modelo WHERE nome = 'Largo')),
(gen_random_uuid(), 'Manutenção 2', '2017-11-05', 8.8, 12.5, (SELECT id FROM marca WHERE nome = 'Alemã'), (SELECT id FROM modelo WHERE nome = 'Popular')),
(gen_random_uuid(), 'Logística 1', '2018-09-20', 9.5, 14.0, (SELECT id FROM marca WHERE nome = 'Italiana'), (SELECT id FROM modelo WHERE nome = 'Quinhentos'));
```

## CURL básicos

### Listar veículos
```
curl --location 'localhost:8080/veiculos'
```

### Criar veículo

Caso informe um modelo ou marca nova, o sistema irá cadastrá-los automaticamente.
```
curl --location 'localhost:8080/veiculos' \
--header 'Content-Type: application/json' \
--data '{
    "nome": "Viagens longas",
    "dataFabricacao": "2016-03-10",
    "consumoMedioCidade": 9.5,
    "consumoMedioRodovias": 12.0,
    "marca": {
        "nome": "Indiana"
    },
    "modelo": {
        "nome": "Super 4x4"
    }
}'
```

### Atualizar veículos

Caso informe um modelo ou marca nova, o sistema irá cadastrá-los automaticamente.
```
curl --location --request PUT 'localhost:8080/veiculos/ID_DO_VEICULO' \
--header 'Content-Type: application/json' \
--data '{
    "id": "ID_DO_VEICULO",
    "nome": "Nome do veiculo",
    "dataFabricacao": "2016-03-10",
    "consumoMedioCidade": 9.5,
    "consumoMedioRodovias": 12.0,
    "marca": {
        "nome": "Marca nova"
    },
    "modelo": {
        "id": "ID_DO_MODELO",
        "nome": "Modelo existente"
    }
}'
```

### Calcular previsão de gastos
```
curl --location 'localhost:8080/previsao-gastos' \
--header 'Content-Type: application/json' \
--data '{
    "precoGasolina": 7.50,
    "totalPercorridoCidade": 20,
    "totalPercorridoRodovia": 3
}'
```
