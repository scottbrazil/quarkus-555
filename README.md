# quarkus-555

This project uses Quarkus, the Supersonic Subatomic Java Framework.

It's the final project for the CAIXAVERSO Quarkus Avançado do Prof. Matheus

### Postgres container
```
In the integrated terminal, being in the root folder:
docker compose -f ./postgres/docker-compose.yml up -d
```
### Kafka container
```
In the integrated terminal, being in the root folder::
docker compose -f ./kafka/docker-compose.yml up -d
```

### Running the application in dev mode
```shell script
./mvnw quarkus:dev
```
### Endpoints
```'
POST http://localhost:8080/auth/login

{
"email": "admin@loja.com",
"senha": "admin123"
}

{
"email": "user@loja.com",
"senha": "user123"
}

Will return the JWT token that must be passed 
as Bearer Token in the header of the produtos endpoints
```
```
http://localhost:8080/auth/register

{
"nome": "maria",
"email": "maria@loja.com",
"senha": "maria123",
"role": "ADMIN"
}

{
"nome": "João",
"email": "joao@loja.com",
"senha": "joao123",
"role": "USER"
}
```

```
GET http://localhost:8080/produtos
```
```
POST http://localhost:8080/produtos
{
  "nome": "Morango",
  "descricao": "Morango jumbo",
  "preco": 25.00,
  "estoque": 4
}
Will print the kafka message in the log
```

```
PUT http://localhost:8080/produtos/1
{
  "nome": "Maçã",
  "descricao": "Maçã Fuji",
  "preco": 15.00,
  "estoque": 4
}
Will print the kafka message in the log
```

```
DELETE localhost:8080/produtos/3
```
### Caching
```
Executing GET http://localhost:8080/produtos twice 
will show the caching in the log in the second run

```
