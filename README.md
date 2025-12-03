# Vida Plus

Vida Plus é um Sistema de Gestão Hospitalar e de Serviços de Saúde. Ele centraliza o cadastro de pacientes, profissionais da saúde e administradores, permitindo gerenciar agendamentos de consultas em unidades hospitalares.

## 🚀 Tecnologias Principais
* Java 25
* Spring Boot
* Spring Data JPA
* MySQL 8.0.32
* Maven (latest)
* Docker (latest)
* Flyway para versionamento de banco

### 📋 Pré-requisitos

#### Java 25
  * Verifique se a versão correta está instalada em sua máquina ou aponte para o JDK através da sua IDE
```
java -version
```

#### MySQL 8.0.32
Você pode instalar o MySQL na máquina ou utilizar um container Docker. O projeto espera:
  * Conexão em 127.0.0.1:3306
  * Um banco chamado vida_plus
  * Credenciais padrão root / root
  * Propriedade JDBC allowPublicKeyRetrieval=true habilitada (MySQL 8 exige envio de chave pública durante a autenticação)
  * Segue comando docker com toda a parametrização necessária para criar o container
```
docker run \
  --name mysql-vida-plus \
  -p 127.0.0.1:3306:3306 \
  -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=vida_plus \
  -e MYSQL_USER=root \
  -e MYSQL_PASSWORD=root \
  -e MYSQL_ROOT_HOST=% \
  -e MYSQL_ALLOW_PUBLIC_KEY_RETRIEVAL=true \
  -v mysql_data:/var/lib/mysql \
  -d mysql:8.0.32
```
#### 👤 Usuário administrador inicial
- O Flyway insere um usuário administrador para facilitar os testes. Como o ambiente é estritamente acadêmico irei expor o usuário e senha.
```
email: admin@email.com
senha: VidaPlus2025!@#
```

### ▶️ Como rodar o projeto
1. Clonar o repositório
```
git clone https://github.com/ArthurPeruzzo/vidaplus.git
```
2. Com o comando abaixo é possível criar a variável de ambiente JWT, mas é preferível que a configuração seja feita via IDE ou até mesmo somente colocando uma valor qualquer na variável "jwt.secret".
```
export JWT_SECRET="minhaChaveSecreta"
```
3. Compilar via terminal ou IDE
```
mvn clean install
```
4. Executar via terminal ou IDE
```
mvn spring-boot:start
```
5. Acessar a aplicação
```
http://localhost:8080
```


## ⚙️ Executando os testes
O projeto possui testes unitários e integrados. Para rodar os testes integrados é necessário:
- Docker instalado
- Usuário pertencendo ao grupo docker (para execução sem sudo)

- Comando para adicionar seu usuário a um grupo
```
sudo usermod -aG docker $USER
```
- Se for possível rodar o comando abaixo sem o "sudo" os testes integrados também devem funcionar
```
docker ps
```
## 📋 Documentação dos endpoints
A aplicação utiliza Swagger para documentar e visualizar os endpoints
- Após iniciar o projeto, acesse:
```
http://localhost:8080/swagger-ui/index.html
```
### 📜 Licença
Este projeto é de uso acadêmico e educativo. Pode ser utilizado para estudos, melhorias e referências.
