# Sistema de Agendamento de Consultas

Sistema web para agendamento e gerenciamento de consultas médicas, desenvolvido como parte do processo seletivo da CapGov. A aplicação permite o controle de pacientes, médicos, atendentes e seus respectivos agendamentos.

---

##  STATUS DO PROJETO

-   ✅ **CRUD de Pessoas**: Gerenciamento de Atendentes, Pacientes e Médicos (100% funcional).
-   ✅ **Autenticação**: Sistema de login e logout funcional para usuários do tipo "Atendente".
-   🟡 **CRUD de Agendamento**:
    -   Criação e listagem de agendamentos: **Funcional**.
    -   Edição e exclusão de agendamentos: **Não implementado**.
-   ❌ **CRUD de Atendimento**: Não implementado devido ao escopo do desafio.

---

## 🛠️ TECNOLOGIAS UTILIZADAS

| Tecnologia | Versão / Especificação |
| :--- | :--- |
| Java (JDK) | 24 |
| Jakarta EE | 11 (Servlet 6.1) |
| Servidor de Aplicação | Apache Tomcat 11 |
| Persistência (JPA) | Hibernate 6.6.0.Final |
| Banco de Dados | PostgreSQL |
| Build e Dependências | Apache Maven 4.0.0 |

---

## 📋 PRÉ-REQUISITOS

Antes de começar, garanta que você tenha as seguintes ferramentas instaladas e configuradas no seu ambiente:

-   Java JDK 24 ou superior
-   Apache Maven 4.0.0+
-   Apache Tomcat 11
-   PostgreSQL
-   Git

---

## 🚀 COMO EXECUTAR O PROJETO

Siga os passos abaixo para configurar e rodar a aplicação localmente.

### 1. Clonar o Repositório
Navegue até o diretório onde deseja salvar o projeto e execute o comando:
```bash
git clone [https://github.com/seu-usuario/consulta-agendamento.git](https://github.com/seu-usuario/consulta-agendamento.git)
cd consulta-agendamento
```

### 2. Configurar o Banco de Dados
A aplicação requer um banco de dados PostgreSQL para funcionar.

**a. Crie o Banco de Dados**
Execute o seguinte comando no seu cliente SQL (psql, DBeaver, etc.):
```sql
CREATE DATABASE consultas_db;
```
**Observação:** A tabela será criada automaticamente pelo Hibernate na primeira vez que a aplicação iniciar, graças à configuração `hibernate.hbm2ddl.auto=update`.

**b. Configure as Credenciais**
Abra o arquivo de configuração de persistência e ajuste com seu usuário e senha do PostgreSQL:
-   **Arquivo:** `src/main/resources/META-INF/persistence.xml`
-   **Altere as propriedades:**
    ```xml
    <property name="jakarta.persistence.jdbc.user" value="SEU_USUARIO_POSTGRES" />
    <property name="jakarta.persistence.jdbc.password" value="SUA_SENHA_POSTGRES" />
    ```

### 3. Compilar o Projeto (Build)
Use o Maven para baixar as dependências e empacotar a aplicação. Na raiz do projeto, execute:
```bash
mvn clean install
```
Este comando irá gerar um arquivo `consulta-agendamento.war` (ou similar) no diretório `target/`.

### 4. Implantar no Tomcat (Deploy)

Você pode implantar a aplicação de duas formas:

**a. Manualmente:**
   - Copie o arquivo `.war` gerado na pasta `target/` para a pasta `webapps/` do seu diretório de instalação do Tomcat.

**b. Pela IDE (Eclipse, IntelliJ, etc.):**
   - Importe o projeto como um "Existing Maven Project".
   - Configure o servidor Tomcat 11 na sua IDE.
   - Execute (Run As > Run on Server) o projeto no servidor Tomcat configurado.

### 5. Primeiro Acesso e Cadastro do Admin

A aplicação precisa de pelo menos um usuário "Atendente" para que o login funcione.

**a. Cadastre o Primeiro Usuário**
   - Inicie o servidor Tomcat.
   - Acesse a seguinte URL para ir diretamente à página de cadastro:
     **http://localhost:8080/consulta-agendamento/views/pages/cadastrar-pessoa.xhtml**
   - Preencha o formulário e crie um usuário marcando o tipo como **ATENDENTE**.

**b. Realize o Login**
   - Após cadastrar o atendente, acesse a página de login:
     **http://localhost:8080/consulta-agendamento/views/pages/index.xhtml**
   - Use o email e a senha do atendente que você acabou de criar para acessar o sistema.

---

## 📂 ESTRUTURA DO PROJETO

-   `src/main/java`: Código-fonte (Entidades, DAOs, Beans, Controllers).
-   `src/main/resources`: Arquivos de configuração (`persistence.xml`).
-   `src/main/webapp`: Páginas da aplicação (`.xhtml`), templates e recursos estáticos (CSS, JS).
-   `pom.xml`: Arquivo de configuração e dependências do Maven.
