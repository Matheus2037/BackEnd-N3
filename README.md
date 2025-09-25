# API de Hospital

Este projeto foca no gerenciamento de informações de um hospital, permitindo o controle de médicos, pacientes e consultas. A aplicação permitirá que os administradores registrem informações sobre médicos e pacientes, agendem e atualizem consultas, além de fornecer a rastreabilidade dos atendimentos realizados.

## Executando

Para executar o projeto (com Docker):

1. Rode o comando `docker compose up -d` na raiz do projeto.
2. Acesse o endereço `http://localhost:8080` no seu navegador.

Para executar o projeto (sem Docker):

1. Configure o arquivo `application.properties` com as informações do banco de dados.
2. Rode o comando `./mvnw spring-boot:run` na raiz do projeto.
3. Acesse o endereço `http://localhost:8080` no seu navegador.

## Tecnologias Utilizadas

* Spring Boot
* Spring Web
* Spring Data JPA
* H2 Database
* Validation
* Docker
* Lombok

## Recursos

1. Doctor
2. Patient
3. Appointment

## Regras de Negócio

* O código de CR do médico deve seguir o padrão nacional.
* A data de consulta não pode ser anterior à data atual.
* O status da consulta deve ser "Scheduled", "Completed" ou "Canceled".

## Endpoints

* #### 8 Endpoints para Doctor

* #### 5 Endpoints para Patient

* #### 9 Endpoints para Appointment

## Rotas

### Médico

#### `GET /doctor`
Lista todos os médicos disponíveis com paginação.

Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "firstName": "Pedro",
      "lastName": "Cardoso",
      "registration": "12345-SC",
      "email": "pedro@gmail.com",
      "gender": "MALE",
      "totalAppointments": 0
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

#### `GET /doctor/{id}`
Busca um médico pelo ID.

Requisição:
```
GET /doctor/1
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com"
}
```

#### `GET /doctor/first-name/{first-name}`
Busca um médico pelo primeiro nome.

Requisição:
```
GET /doctor/first-name/Pedro
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com"
}
```

#### `GET /doctor/registration/{registration}`
Busca um médico pelo código de CR.

Requisição:
```
GET /doctor/registration/SC-12345
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com"
}
```

#### `GET /doctor/email/{email}`
Busca um médico pelo email.

Requisição:
```
GET /doctor/email/pedro@gmail.com
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com"
}
```

#### `POST /doctor`
Cria um novo médico.

Requisição:
```json
{
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com",
  "gender": "MALE"
}
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Pedro",
  "lastName": "Cardoso",
  "registration": "12345-SC",
  "email": "pedro@gmail.com",
  "gender": "MALE",
  "totalAppointments": 0
}
```

#### `POST /doctor/cad`
Cria novos médicos com apenas uma requisição.

Requisição:
```json
[
  {
    "firstName": "Pedro",
    "lastName": "Cardoso",
    "registration": "12345-SC",
    "email": "pedro@gmail.com",
    "gender": "MALE"
  },
  {
    "firstName": "Lucas",
    "lastName": "Martins",
    "registration": "67891-SC",
    "email": "lucas@gmail.com",
    "gender": "MALE"
  },
  {
    "firstName": "Carla",
    "lastName": "Rodrigues",
    "registration": "54321-SC",
    "email": "carla@gmail.com",
    "gender": "FEMALE"
  }
]
```

Resposta:
```json
[
  {
    "id": 1,
    "firstName": "Pedro",
    "lastName": "Cardoso",
    "registration": "12345-SC",
    "email": "pedro@gmail.com",
    "gender": "MALE"
    "totalAppointments": 0
  },
  {
    "id": 2,
    "firstName": "Lucas",
    "lastName": "Martins",
    "registration": "67891-SC",
    "email": "lucas@gmail.com",
    "gender": "MALE"
    "totalAppointments": 0
  },
  {
    "id": 3,
    "firstName": "Carla",
    "lastName": "Rodrigues",
    "registration": "54321-SC",
    "email": "carla@gmail.com",
    "gender": "FEMALE"
    "totalAppointments": 0
  }
]
```

#### `PATCH /doctor`
Edita um novo médico já registrado.

Requisição:
```json
{
  "firstName": "João",
  "lastName": "Cardoso",
  "registration": "12345-MG",
  "email": "joao@gmail.com",
  "gender": "MALE"
}
```

Resposta:
```json
{
  "id": 1,
  "firstName": "João",
  "lastName": "Cardoso",
  "registration": "12345-MG",
  "email": "joao@gmail.com",
  "gender": "MALE",
  "totalAppointments": 0
}
```

#### `DELETE /doctor/{id}`
Deleta um médico pelo ID.

Resposta:
```
<Corpo vazio>
```

### Pacientes

#### `GET /patient`
Lista todos os médicos disponíveis com paginação.

Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "firstName": "Fernanda",
      "lastName": "Martins",
      "email": "fernanda@email.com",
      "gender": "FEMALE",
      "totalAppointments": 0
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 1,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 1,
  "first": true,
  "empty": false
}
```

#### `GET /patient/{id}`
Busca um paciente pelo ID.

Requisição:

`GET /patient/1`

Resposta:
```json
{
  "id": 1,
  "firstName": "Fernanda",
  "lastName": "Martins",
  "email": "fernanda@email.com",
  "gender": "FEMALE",
  "totalAppointments": 0
}
```

#### `POST /patient`
Cria um novo paciente.

Requisição:
```json
{
  "firstName": "Fernanda",
  "lastName": "Martins",
  "email": "fernanda@email.com",
  "gender": "FEMALE"
}
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Fernanda",
  "lastName": "Martins",
  "email": "fernanda@email.com",
  "gender": "FEMALE",
  "totalAppointments": 0
}
```

#### `PATCH /patient`
Edita um novo médico já registrado.

Requisição:
```json
{
  "firstName": "Juliana",
  "lastName": "Fernandes",
  "email": "juliana@email.com",
  "gender": "FEMALE"
}
```

Resposta:
```json
{
  "id": 1,
  "firstName": "Juliana",
  "lastName": "Fernandes",
  "email": "juliana@email.com",
  "gender": "FEMALE",
  "totalAppointments": 0
}
```

#### `DELETE /patient/{id}`
Deleta um médico pelo ID.

Requisição:

`DELETE /patient/1`

Resposta:
```
<Corpo vazio>
```

### Consultas

#### `GET /appointment`
Lista todas as consultas disponíveis com paginação.

Resposta:
```json
{
  "content": [
    {
      "id": 1,
      "patientId": 2,
      "patientName": "Fernanda Martins",
      "doctorId": 1,
      "doctorName": "Pedro Cardoso",
      "appointmentDate": "2024-12-24T22:30:00",
      "status": "SCHEDULED"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalElements": 1,
  "totalPages": 1,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 1,
  "empty": false
}
```

#### `GET /appointment/{id}`
Busca uma consulta pelo ID.

Requisição:
```
GET /appointment/1
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24T22:30:00",
  "status": "SCHEDULED"
}
```

#### `GET /appointment/date/{date}`
Busca uma consulta pela data.

Requisição:
```
GET /appointment/date/2024-12-24
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24",
  "status": "SCHEDULED"
}
```

#### `GET /appointment/status/{status}`
Busca uma consulta pelo status.

Requisição:
```
GET /appointment/status/SCHEDULED
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24",
  "status": "SCHEDULED"
}
```

#### `GET /appointment/patient/{patientId}`
Busca uma consulta pelo ID do paciente.

Requisição:
```
GET /appointment/patient/2
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24",
  "status": "SCHEDULED"
}
```

#### `GET /appointment/doctor/{doctorId}`
Busca uma consulta pelo ID do médico.

Requisição:
```
GET /appointment/doctor/1
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24",
  "status": "SCHEDULED"
}
```

#### `POST /appointment`
Cria uma nova consulta.

Requisição:
```json
{
  "patientId": 1,
  "doctorId": 1,
  "appointmentDate": "2024-12-24T22:30:00",
  "status": "SCHEDULED"
}
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24T22:30:00",
  "status": "SCHEDULED"
}
```

#### `PATCH /appointment`
Edita uma consulta já registrada.

Requisição:
```json
{
  "patientId": 1,
  "doctorId": 1,
  "appointmentDate": "2024-12-24T22:30:00",
  "status": "COMPLETED"
}
```

Resposta:
```json
{
  "id": 1,
  "patientId": 2,
  "patientName": "Fernanda Martins",
  "doctorId": 1,
  "doctorName": "Pedro Cardoso",
  "appointmentDate": "2024-12-24T22:30:00",
  "status": "COMPLETED"
}
```

#### `DELETE /appointment/{id}`
Deleta uma consulta pelo ID.

Resposta:
```
<Corpo vazio>
```
