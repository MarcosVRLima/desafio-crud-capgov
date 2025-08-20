# Consulta Agendamento

Sistema web de agendamento de consultas médicas, desenvolvido com Java, Jakarta EE, Hibernate e PostgreSQL.

---

## Tecnologias Utilizadas

| Tecnologia               | Versão                 |
|--------------------------|-----------------------|
| Java JDK                 | 24                    |
| Jakarta EE / Servlet API | 10 / 6.0              |
| Hibernate (JPA)          | 6.6.0.Final           |
| Maven                    | 4.0.0                 |
| PostgreSQL Driver        | 42.7.3                |
| Tomcat                   | 11                    |
| Git                      | Última disponível     |

---

## Estrutura do Projeto

- `src/main/java` → código-fonte das entidades, DAOs e testes  
- `src/main/resources/META-INF/persistence.xml` → configuração JPA/Hibernate  
- `pom.xml` → gerenciador de dependências Maven  

---

## Como Rodar

### 1. Configurar PostgreSQL

- Crie o banco `consultasdb`  
- Configure usuário e senha no arquivo `persistence.xml`  

### 2. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/consulta-agendamento.git
cd consulta-agendamento
