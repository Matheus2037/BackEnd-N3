# API de Hospital

Este projeto foca no gerenciamento de informações de um hospital, permitindo o controle de médicos, pacientes e consultas. A aplicação permite que administradores registrem informações sobre médicos e pacientes, agendem e atualizem consultas, além de fornecer rastreabilidade dos atendimentos realizados.

> **Projeto Final — Disciplina de Clean Code**
> Este repositório contém a versão refatorada do projeto. A versão original está preservada na branch [`original`](../../tree/original) para fins de comparação.

---

## Sumário

- [Tecnologias](#tecnologias-utilizadas)
- [Problemas Detectados](#problemas-detectados)
- [Refatorações Aplicadas](#refatorações-aplicadas)
- [Testes e Cobertura](#testes-e-cobertura)
- [Autenticação](#autenticação)
- [Executando o Projeto](#executando)
- [Variáveis de Ambiente](#variáveis-de-ambiente)
- [Rotas](#rotas)

---

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.3.5
- Spring Web
- Spring Data JPA
- Spring Security + JWT (JJWT 0.12.6)
- H2 Database
- Bean Validation
- Lombok
- Docker
- Checkstyle (Google Style, calibrado)
- JaCoCo
- GitHub Actions (CI/CD)

---

## Problemas Detectados

A análise do projeto original identificou **12 problemas relevantes**, classificados em quatro categorias:

### Code Smells (catálogo de Fowler)

| # | Smell | Local |
|---|---|---|
| 1 | Código Duplicado — padrão "buscar ou lançar NotFoundException" repetido em todos os services | `*ServiceImpl` |
| 2 | Código Duplicado — `ServiceException` duplicava a mensagem em campo próprio e em `super()` | `ServiceException` |
| 3 | Código Duplicado — `@ResponseBody` repetido nos 5 métodos do handler | `GlobalExceptionHandler` |
| 4 | Nome Misterioso — variáveis em português (`doctorsParaSalvar`, `mensagem`) misturadas com inglês | `DoctorServiceImpl`, `PatientServiceImpl` |
| 5 | Nome Misterioso — endpoint `/cad` como abreviação obscura de "cadastro" | `DoctorController`, `PatientController` |
| 6 | Anti-pattern de injeção — field injection inconsistente; `DoctorController` tinha injeção dupla | Controllers e Services |

### Bugs de Comportamento

| # | Bug | Local |
|---|---|---|
| 7 | `patientId` e `doctorId` do `UpdateAppointmentDto` aceitos pela API mas silenciosamente ignorados | `AppointmentServiceImpl` |
| 8 | `@Future` sem `@NotNull` — data nula passava na validação e estourava no banco | `CreateAppointmentDto` |
| 9 | Endpoint `/patient/batch` retornava `200 OK` em vez de `201 Created` | `PatientController` |

### Violações de Clean Code

| # | Violação | Local |
|---|---|---|
| 10 | `ArrayList` concreto exposto em assinaturas públicas com cast forçado | `DoctorService`, `PatientService` |
| 11 | Wildcard imports (`import ...dtos.*`) em parte dos controllers | `AppointmentController`, `DoctorController` |

### Más Práticas de Configuração e Segurança

| # | Problema | Local |
|---|---|---|
| 12 | Credenciais em texto plano e `ddl-auto=update` no arquivo versionado | `application.properties` |
| 13 | API sem autenticação — todos os endpoints públicos sem proteção (OWASP: Broken Authentication) | Todos os controllers |

---

## Refatorações Aplicadas

### Code Smells
- **Código Duplicado:** extraído método `findXOrThrow(Long id)` reutilizável em cada service; `ServiceException` simplificada para usar apenas `getMessage()` herdado; `GlobalExceptionHandler` migrado para `@RestControllerAdvice`, eliminando 5 `@ResponseBody` redundantes
- **Nomes Misteriosos:** nomenclatura unificada em inglês em todo o código; endpoint `/cad` renomeado para `/batch`
- **Injeção por campo:** toda injeção migrada para construtor com campos `final`, padronizando todas as classes e tornando-as testáveis sem o container Spring

### Bugs
- Campos `patientId` e `doctorId` do `UpdateAppointmentDto` agora efetivamente atualizam a consulta
- `@NotNull` adicionado ao `appointmentDate`, movendo a falha para a fronteira do sistema com mensagem clara
- Status HTTP do `/patient/batch` corrigido para `201 Created`
- `@NotBlank` adicionado nos campos de texto obrigatórios dos DTOs de criação

### Clean Code
- Assinaturas que expunham `ArrayList` migradas para interface `List`, com casts removidos
- Wildcard imports substituídos por imports explícitos (resolvido via auto-formatação Google Style)

### Configuração e Segurança
- Credenciais movidas para variáveis de ambiente com fallback para desenvolvimento
- `ddl-auto` ajustado para `create-drop`
- Autenticação JWT implementada com Spring Security — todos os endpoints protegidos, exceto `POST /auth/register` e `POST /auth/login`

### Linter
- Checkstyle integrado com Google Style (Javadoc desabilitado via supressões, alinhado aos princípios de Clean Code)
- Violações no projeto original: **491**
- Violações após refatoração: **0**
- Integração em três etapas: build Maven, Git Hook pre-commit e GitHub Actions CI/CD

---

## Testes e Cobertura

A suíte de testes foi construída do zero usando **JUnit 5** e **Mockito**, com cobertura medida pelo **JaCoCo**.

| Suite | Testes | Tipo |
|---|---|---|
| `DoctorServiceImplTest` | 9 | Unitário (Mockito) |
| `PatientServiceImplTest` | 12 | Unitário (Mockito) |
| `AppointmentServiceImplTest` | 14 | Unitário (Mockito) |
| `JwtServiceTest` | 4 | Unitário (Mockito) |
| `AuthControllerTest` | 5 | Integração (SpringBootTest) |
| `HospitalApiApplicationTests` | 1 | Integração (SpringBootTest) |
| **Total** | **45** | — |

**Cobertura total: 70%** (services: 84%, pacote security: 96%, DTOs: 100%)

Os testes dos casos 10 e 11 do `AppointmentServiceImplTest` cobrem especificamente os bugs corrigidos de `patientId`/`doctorId` ignorados, documentando que o comportamento correto está garantido.

---

## Autenticação

A API utiliza autenticação **JWT (Bearer Token)**. Todos os endpoints — exceto registro e login — exigem o token no header `Authorization`.

### Registrar usuário

```
POST /auth/register
Content-Type: application/json

{
  "username": "admin",
  "password": "senha123"
}
```

Resposta (`201 Created`):
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

### Login

```
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "senha123"
}
```

Resposta (`200 OK`):
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

### Usando o token

Inclua o token em todas as requisições:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## Executando

Para executar o projeto (com Docker):

1. Rode o comando `docker compose up -d` na raiz do projeto.
2. Acesse o endereço `http://localhost:8080` no seu navegador.

Para executar o projeto (sem Docker):

1. Copie o arquivo `.env.example` para `.env` na raiz do projeto e ajuste os valores se necessário.
2. Rode o comando `./mvnw spring-boot:run` na raiz do projeto.
3. Acesse o endereço `http://localhost:8080` no seu navegador.

---

## Variáveis de Ambiente

As credenciais do banco de dados são configuradas via variáveis de ambiente. Copie `.env.example` para `.env` e preencha os valores:

| Variável | Descrição | Padrão |
|---|---|---|
| `DB_USERNAME` | Usuário do banco de dados | `sa` |
| `DB_PASSWORD` | Senha do banco de dados | `sa` |

> O arquivo `.env` está no `.gitignore` e **não deve ser commitado**.

---

## Recursos

1. Auth (register/login)
2. Doctor
3. Patient
4. Appointment

---

## Regras de Negócio

- O código de CR do médico deve seguir o padrão nacional.
- A data de consulta não pode ser anterior à data atual.
- O status da consulta deve ser `SCHEDULED`, `COMPLETED` ou `CANCELED`.
- Todas as requisições (exceto `/auth/register` e `/auth/login`) exigem autenticação via Bearer Token.

---

## Endpoints

- **2 Endpoints para Auth**
- **8 Endpoints para Doctor**
- **5 Endpoints para Patient**
- **9 Endpoints para Appointment**

---

## Rotas

### Autenticação

#### `POST /auth/register`
Registra um novo usuário e retorna um token JWT.

Requisição:
```json
{ "username": "admin", "password": "senha123" }
```

Resposta (`201 Created`):
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

#### `POST /auth/login`
Autentica um usuário e retorna um token JWT.

Requisição:
```json
{ "username": "admin", "password": "senha123" }
```

Resposta (`200 OK`):
```json
{ "token": "eyJhbGciOiJIUzI1NiJ9..." }
```

---

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
  "pageable": { "pageNumber": 0, "pageSize": 20 },
  "totalElements": 1,
  "totalPages": 1
}
```

#### `GET /doctor/{id}`
Busca um médico pelo ID.

#### `GET /doctor/first-name/{first-name}`
Busca um médico pelo primeiro nome.

#### `GET /doctor/registration/{registration}`
Busca um médico pelo código de CR.

#### `GET /doctor/email/{email}`
Busca um médico pelo email.

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

#### `POST /doctor/batch`
Cria múltiplos médicos em uma única requisição.

Requisição: array de objetos com o mesmo formato de `POST /doctor`.

#### `PATCH /doctor`
Edita um médico já registrado.

#### `DELETE /doctor/{id}`
Deleta um médico pelo ID.

---

### Pacientes

#### `GET /patient`
Lista todos os pacientes com paginação.

#### `GET /patient/{id}`
Busca um paciente pelo ID.

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

#### `POST /patient/batch`
Cria múltiplos pacientes em uma única requisição. Retorna `201 Created`.

#### `PATCH /patient`
Edita um paciente já registrado.

#### `DELETE /patient/{id}`
Deleta um paciente pelo ID.

---

### Consultas

#### `GET /appointment`
Lista todas as consultas com paginação.

#### `GET /appointment/{id}`
Busca uma consulta pelo ID.

#### `GET /appointment/date/{date}`
Busca consultas pela data (`YYYY-MM-DD`).

#### `GET /appointment/status/{status}`
Busca consultas pelo status (`SCHEDULED`, `COMPLETED`, `CANCELED`).

#### `GET /appointment/patient/{patientId}`
Busca consultas pelo ID do paciente.

#### `GET /appointment/doctor/{doctorId}`
Busca consultas pelo ID do médico.

#### `POST /appointment`
Cria uma nova consulta.

Requisição:
```json
{
  "patientId": 1,
  "doctorId": 1,
  "appointmentDate": "2025-12-24T22:30:00",
  "status": "SCHEDULED"
}
```

#### `PATCH /appointment`
Atualiza uma consulta existente. Os campos `patientId` e `doctorId` são opcionais — se informados, atualizam o paciente e médico da consulta.

#### `DELETE /appointment/{id}`
Deleta uma consulta pelo ID.
