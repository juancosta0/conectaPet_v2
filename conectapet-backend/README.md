# ConectaPet Backend

Backend da aplicação ConectaPet - Sistema de adoção de pets desenvolvido com Spring Boot.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** (JWT Authentication)
- **Spring Data JPA**
- **H2 Database** (desenvolvimento)
- **PostgreSQL** (produção)
- **Maven**

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior

## 🔧 Instalação e Execução

### 1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd conectapet-backend
```

### 2. Execute o projeto
```bash
mvn spring-boot:run
```

### 3. Acesse a aplicação
- **API**: http://localhost:8080
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:conectapet`
  - Username: `sa`
  - Password: *(vazio)*

## 🗄️ Banco de Dados

### Configuração H2 (Desenvolvimento)
O projeto usa H2 em memória por padrão. Os dados são inseridos automaticamente via `data.sql`.

### Usuários de Teste
- **Adotante**: `joao@email.com` / `123456`
- **ONG**: `ong@amigos.com` / `123456`

## 📚 API Endpoints

### 🔐 Autenticação
```
POST /auth/login
POST /auth/register
```

### 🐾 Pets
```
GET    /api/pets              # Listar todos os pets disponíveis
GET    /api/pets/{id}         # Buscar pet por ID
GET    /api/pets/favorites    # Buscar pets favoritos por IDs
POST   /api/pets              # Criar novo pet (apenas ONGs)
PUT    /api/pets/{id}         # Atualizar pet (apenas dono)
DELETE /api/pets/{id}         # Deletar pet (apenas dono)
GET    /api/pets/search       # Buscar pets com filtros
GET    /api/pets/my-pets      # Listar meus pets (apenas ONGs)
```

### 👤 Usuários
```
GET /api/users/profile        # Obter perfil do usuário logado
PUT /api/users/profile        # Atualizar perfil do usuário logado
GET /api/users/{id}           # Buscar usuário por ID
```

## 🔑 Autenticação JWT

### Login
```json
POST /auth/login
{
  "email": "joao@email.com",
  "password": "123456"
}
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "name": "João Silva",
  "email": "joao@email.com",
  "userType": "adotante"
}
```

### Registro
```json
POST /auth/register
{
  "name": "Maria Silva",
  "email": "maria@email.com",
  "password": "123456",
  "userType": "adotante"
}
```

### Registro ONG
```json
POST /auth/register
{
  "name": "ONG Patinhas Carentes",
  "email": "contato@patinhas.org",
  "password": "123456",
  "userType": "ong",
  "cnpj": "12.345.678/0001-90",
  "description": "ONG dedicada ao resgate de animais"
}
```

## 🐕 Cadastro de Pet

```json
POST /api/pets
Authorization: Bearer <token>
{
  "name": "Buddy",
  "species": "Cachorro",
  "breed": "Labrador",
  "age": 3,
  "size": "Grande",
  "color": "Dourado",
  "description": "Cão muito carinhoso e brincalhão",
  "imageUrls": [
    "https://example.com/image1.jpg",
    "https://example.com/image2.jpg"
  ]
}
```

## 🔍 Busca com Filtros

```
GET /api/pets/search?species=Cachorro&size=Grande&minAge=2&maxAge=5
```

## 📊 Estrutura do Banco

### Tabelas Principais
- `users` - Usuários (adotantes e ONGs)
- `pets` - Pets disponíveis para adoção
- `pet_images` - Imagens dos pets
- `adoption_requests` - Solicitações de adoção

### Relacionamentos
- User 1:N Pet (ONG pode ter vários pets)
- Pet 1:N PetImage (Pet pode ter várias imagens)
- User 1:N AdoptionRequest (Usuário pode fazer várias solicitações)
- Pet 1:N AdoptionRequest (Pet pode receber várias solicitações)

## 🛡️ Segurança

- **JWT Authentication** para todas as rotas protegidas
- **BCrypt** para hash de senhas
- **CORS** configurado para frontend Angular
- **Validação** de dados com Bean Validation

## 🚀 Deploy

### Configuração para Produção (PostgreSQL)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/conectapet
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
    hibernate:
      ddl-auto: update
```

### Variáveis de Ambiente
```bash
DB_USERNAME=seu_usuario
DB_PASSWORD=sua_senha
JWT_SECRET=sua_chave_secreta_muito_longa
JWT_EXPIRATION=86400000
```

## 📝 Logs

O projeto está configurado com logs detalhados para:
- Requisições HTTP
- Autenticação JWT
- Queries SQL
- Erros da aplicação

## 🧪 Testes

```bash
mvn test
```

## 📞 Suporte

Para dúvidas ou problemas:
1. Verifique os logs da aplicação
2. Confirme se o H2 Console está acessível
3. Teste os endpoints com Postman/Insomnia
4. Verifique se o JWT está sendo enviado corretamente

## 🎯 Próximas Funcionalidades

- [ ] Sistema de notificações
- [ ] Upload real de imagens
- [ ] Sistema de chat entre adotantes e ONGs
- [ ] Relatórios e dashboards
- [ ] API de geolocalização