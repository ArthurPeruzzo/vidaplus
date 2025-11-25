# Vida Plus

Vida Plus é um Sistema de Gestão Hospitalar e de Serviços de Saúde. Ele centraliza o cadastro de pacientes, profissionais da saúde e administradores. Através dele é possível gerenciar agendamentos para consultas em unidades hospitalares.

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
  * É possível baixar o MySQL em sua máquina ou simplesmente criar uma imagem docker. Independente da forma que isso seja feito o projeto espera o seguinte:
  * Uma conexão em 127.0.0.1(localhost) e na porta 3306
  * Um banco chamado "vida_plus"
  * Que as credenciais do banco seja definida como "root". Isso pode mudar, mas será necessário alterar as propriedade contidas dentro do application.properites do projeto 
  * Que a propriedade "allowPublicKeyRetrieval" esteja marcada como TRUE. O MySQL 8 exige enviar a chave pública ao cliente para autenticar a senha, e o      driver JDBC bloqueia isso por padrão

  * Segue comando docker com toda a parametrização necessária para criar a imagem
```
colocar comando docker aqui
```
- Após criar a conexão é necessário criar o banco para que a aplicação se conecte.

```
CREATE DATABASE vida_plus;
```
- Nas migrations do Flyway existe um usuário administrador já inserido. Com ele será possível criar os outros tipos de usuários e executar todas as rotinas da aplicação. Como o projeto é de carater de teste irei expor o usuário e senha.

```
email: admin@email.com
senha: VidaPlus2025!@#
```

### ▶️ Como rodar o projeto
1. Clonar o repositório
```
git clone https://github.com/ArthurPeruzzo/vidaplus.git
```
2. Compilar. Isso pode ser feito com o cógio baixo ou através da IDE
```
mvn clean install
```
3. Executar. Isso pode ser feito com o cógio baixo ou através da IDE
```
mvn spring-boot:run
```
4. Acessar. A aplicação iniciará normalmente em
```
http://localhost:8080
```


## ⚙️ Executando os testes
O projeto contém testes unitários e testes integrados. Para rodá-los com sucesso é necessário ter o docker instalado, pois existem testes integrados que utilizam da ferramenta de TestContainers para subir o banco de dados e assim executar a bateria de testes. Além do docker é necessário que o usuário esteja vinculado a um grupo. Assim a ferramenta consegue ter o permissionamento necessáiro para rodar os testes.

- Comando para adicionar seu usuário a um grupo
```
sudo usermod -aG docker $USER
```
- Se for possível rodar o comando abaixo sem o "sudo" os testes integrados também devem funcionar
```
docker ps
```
## 📋 Documentação dos endpoints
O projeto utiliza a swagger para documentação dos endpoints. Essa ferramenta organiza e documenta de forma clara cada endpoint que existe dentro da aplicação. Em cada endpoint há a sua descrição, seu propósito. Junto a isso também está documentado a entrada de dados e retornos esperados. Após compilar a aplicação será possível acessar a documentação através do link abaixo
```
http://localhost:8080/swagger-ui/index.html
```
