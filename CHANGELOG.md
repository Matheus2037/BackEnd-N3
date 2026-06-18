# Changelog

Todas as mudanças relevantes deste projeto serão documentadas neste arquivo.

O formato segue o padrão [Keep a Changelog](https://keepachangelog.com/pt-BR/1.0.0/).

## [Unreleased]

### Added

- Autenticação JWT com Spring Security: endpoints `POST /auth/register` e `POST /auth/login`; todos os demais endpoints protegidos por Bearer Token
- Entidade `User` com `UserRepository`, `UserDetailsService` e `JwtService` para geração e validação de tokens HS256
- Suíte de testes unitários do zero: `DoctorServiceImplTest` (9 casos), `PatientServiceImplTest` (12 casos), `AppointmentServiceImplTest` (14 casos) e `JwtServiceTest` (4 casos) — todos com JUnit 5 e Mockito, sem contexto Spring
- Testes de integração: `AuthControllerTest` (5 casos) com `@SpringBootTest` e `@AutoConfigureMockMvc`
- Cobertura de testes medida por JaCoCo: **70%** de cobertura total, **84%** na camada de service, **96%** no pacote de segurança
- Checkstyle integrado ao build Maven com Google Style e supressão de Javadoc (alinhado aos princípios de Clean Code)
- Git Hook pre-commit que executa `mvn checkstyle:check` antes de cada commit local
- GitHub Actions CI/CD (`.github/workflows/ci.yml`) que executa Checkstyle e testes a cada push na branch `main`
- Arquivo `.env.example` documentando as variáveis de ambiente esperadas
- Arquivo `checkstyle-suppressions.xml` para calibração das regras do linter

### Changed

- Injeção de dependência migrada integralmente para construtor com campos `final` em todos os controllers e services — elimina field injection e injeção dupla inconsistente
- `GlobalExceptionHandler` migrado de `@ControllerAdvice` + `@ResponseBody` repetido para `@RestControllerAdvice`, removendo 5 anotações redundantes
- Nomenclatura unificada em inglês: variáveis `doctorsParaSalvar`, `doctorsSalvos`, `patientsParaSalvar`, `patientsSalvos` e `mensagem` renomeadas para seus equivalentes em inglês
- Endpoint de cadastro em lote renomeado de `/cad` para `/batch` em `DoctorController` e `PatientController`
- Assinaturas públicas que expunham `ArrayList` migradas para interface `List`; casts explícitos de `List` para `ArrayList` removidos
- `ServiceException` simplificada: campo `mensagem` e método `getMensagem()` removidos; substituídos por `getMessage()` herdado de `RuntimeException`
- Padrão "buscar entidade por id ou lançar `NotFoundException`" extraído para métodos privados reutilizáveis em cada service (`findDoctorOrThrow`, `findPatientOrThrow`, `findAppointmentOrThrow`), eliminando 13 ocorrências de código duplicado
- `spring.jpa.hibernate.ddl-auto` alterado de `update` para `create-drop`
- Credenciais do banco removidas do arquivo versionado e substituídas por variáveis de ambiente com fallback para desenvolvimento
- Indentação e ordem de imports padronizadas com Google Java Style via auto-formatação (491 violações → 0)

### Fixed

- Campos `patientId` e `doctorId` do `UpdateAppointmentDto` eram aceitos pela API mas silenciosamente ignorados pelo service — atualização agora efetivamente aplicada
- `@Future` sem `@NotNull` no `appointmentDate` permitia que data nula passasse na validação do Bean Validation — `@NotNull` adicionado com mensagem clara
- Endpoint `POST /patient/batch` retornava `200 OK` em vez de `201 Created` — corrigido para `ResponseEntity.status(HttpStatus.CREATED)`
- `@NotBlank` adicionado nos campos de texto obrigatórios de `CreateDoctorDto` e `CreatePatientDto`, evitando que strings vazias passassem na validação de `@Size`

### Security

- API completamente protegida com Spring Security + JWT: requisições sem token válido retornam `401 Unauthorized`
- Credenciais do banco de dados removidas do `application.properties` versionado
- `.env` adicionado ao `.gitignore` para evitar exposição acidental de credenciais
