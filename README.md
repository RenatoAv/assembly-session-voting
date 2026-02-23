# Spring Boot – Aplicação simples de sessão de votos de assembleia

Esta é uma aplicação exemplo de votos de sessões de assembleias

---

## Bancos de Dados

| Banco                | Finalidade                             | Tipo |
|----------------------|----------------------------------------|------|
| **Account Database** | Criação e Consulta de contas           | H2   |

O banco da dados pode ser acessado via log do h2, as configurações estão no arquivo application.properties

>Obs: O banco de dados é **in-memory**, mas os dados não são perdidos ao reiniciar a aplicação.

---

### Endpoints da Aplicação
A aplicação possui 8 endpoints principais para a consulta e persistencia de dados relacionados a assembleia, pautas e 
eleitores.

>Uma **collection do insomnia** está disponibilizada na raiz do projeto.

---
### GET - Listar Agendas

Endpoint responsável por listar pautas disponiveis para serem votadas pela assembleia.

| Método | URL           |
|--------|---------------|
| `GET`  | `/v1/agendas` |

#### Response Body

```json
{
  "agendas": [
    {
      "id": 1,
      "title": "Agenda1",
      "description": "description for Agenda1"
    }
  ]
}
```

### POST - Criação de Agenda

Endpoint responsável por persistir agendas.

| Método | URL |
|--------|-----|
| `POST` | `/v1/agendas` |

#### Request Body
```json
{
  "title" : "Agenda1",
  "description" : "description for Agenda1"
}
```

### GET - Listar Assembleias

Endpoint responsável por listar assembleias, juntamente com seu tempo de duração, data de inicio e data fim

| Método | URL           |
|--------|---------------|
| `GET`  | `/v1/assemblies` |

#### Response Body

```json
{
  "assemblies": [
    {
      "duration": 5,
      "startedAt": "23-02-2026 11:19",
      "endAt": "23-02-2026 11:24"
    }
  ]
}
```

### POST - Criação de Assembleias

Endpoint responsável por persistir assembleias.

| Método | URL |
|--------|-----|
| `POST` | `/v1/assemblies` |

#### Request Body
```json
{
  "agendaId": 1,
  "duration": 5
}
```

### GET - Listar Assembleias

Endpoint responsável por retornar o resultado da votação por um id de assembleia, juntamente com os eleitores que votaram

| Método | URL                                  |
|--------|--------------------------------------|
| `GET`  | `/v1/assemblies/{assemblyId}/result` |

#### Response Body

```json
{
  "title": "Agenda1",
  "result": "Approved",
  "votes": [
    {
      "name": "Samuel",
      "document": "01767992068",
      "approved": false
    },
    {
      "name": "Miguelito",
      "document": "03800363046",
      "approved": true
    },
    {
      "name": "Rafael",
      "document": "26403436023",
      "approved": true
    },
    {
      "name": "Jonas",
      "document": "22120413061",
      "approved": true
    },
    {
      "name": "Natanael",
      "document": "23389357050",
      "approved": false
    }
  ]
}
```

### GET - Listar Eleitores

Endpoint responsável por listar eleitores.

| Método | URL           |
|--------|---------------|
| `GET`  | `/v1/voters` |

#### Response Body

```json
{
  "voters": [
    {
      "id": 4,
      "name": "Natanael"
    },
    {
      "id": 5,
      "name": "Samuel"
    },
    {
      "id": 1,
      "name": "Miguelito"
    },
    {
      "id": 3,
      "name": "Rafael"
    },
    {
      "id": 2,
      "name": "Jonas"
    }
  ]
}
```

### POST - Criação de Eleitores

Endpoint responsável por persistir eleitores.

| Método | URL          |
|--------|--------------|
| `POST` | `/v1/voters` |

#### Request Body
```json
{
  "name": "Samuel",
  "document": "01767992068"
}
```

### POST - Efetuar Voto de Eleitor

Endpoint responsável por efetuar um voto em uma assembleia por um eleitor.

| Método | URL                         |
|--------|-----------------------------|
| `POST` | `/v1/voters/{voterId}/vote` |

#### Request Body
```json
{
  "assemblyId": 1,
  "approved": false
}
```

---

## Troubleshooting
Dentro do diretório /db/h2 é onde ficam salvos os arquivos de persistencia do H2, em caso de falha na geração de scripts ou manipulação de dados, excluir os arquivos dentro desse diretório.

