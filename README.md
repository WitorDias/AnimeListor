<h1 align="center">
  AnimeListor
</h1>

API para gerenciar animes (CRUD) com a utilização de classes para tratar excessões, testes unitários, testes de integração, segurança utilizando autenticação em nível de memória, e ferramentas como docker para o uso banco de dados além de prometheus e grafana para monitoramento da api.

O projeto foi desenvolvido durante minha trajetória contínua de aprendizado do universo Spring. O principal objetivo deste projeto é exercitar todo o conteúdo absorvido de diversas fontes como documentações, fóruns e alguns cursos e palestras de desenvolvedores como Amigoscode, Devoxx, DevDojo.

## Tecnologias
 
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Web MVC](https://docs.spring.io/spring-framework/reference/web/webmvc.html)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Junit5](https://junit.org/junit5/docs/current/user-guide/)
- [Mockito](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)
- [SpringDoc OpenAPI 3](https://springdoc.org/v2/#spring-webflux-support)
- [Docker](https://www.docker.com/products/docker-desktop/)
- [Mysql](https://dev.mysql.com/downloads/)
- [Prometheus](https://prometheus.io/download/)
- [Grafana](https://grafana.com/get/?plcmt=top-nav&cta=downloads)

## Práticas adotadas

- SOLID, DRY, KISS
- API REST
- Consultas com Spring Data JPA
- Injeção de Dependências
- Tratamento de excessões
- Testes Unitários
- Testes de Integração
- Autenticação Basic Auth em mémoria com Spring Security
- Geração automática do Swagger com a OpenAPI 3
- Monitoramento com Grafana e Prometheus
- Docker Compose
- Criação de imagem para o projeto com JIB

 ## Login
Esta API utiliza autenticação em memória e possui 2 tipos de perfis: <br>

Admin possui acesso total as requisições <br>
login: admin <br>
senha: 123 <br>

User não é habilitado a fazer requisições para DELETE ou PUT <br>
login: user <br>
senha: 123 <br>

 ## Como Executar a Aplicação com Docker

- Certifique-se de ter o docker instalado em seu computador
- Clone o repositório git
- Acesse a pasta do projeto através de um terminal (prompt de comando)
- Caso esteja utilizando windows, abra a pasta do AnimeListor, copie o caminho absoluto e em um terminal escreva cd e cole o caminho absoluto
- Exemplo: cd C:\Users\UserA\eclipse-workspace-j17\AnimeListor
- Em seguida execute o comando a seguir no terminal:
- Subir o projeto através do docker compose:
```
docker compose up
```
- O comando acima baixa as imagens do MySQL, Prometheus, Grafana e o projeto do AnimeListor, cria locais para o armazenamento de dados do MySQL e Prometheus, e instancia as imagens em contêineres
- Para acessar estes serviços:
- AnimeListor - [localhost:8080/api/v1/animes](http://localhost:8080/api/v1/animes)
- Prometheus - [localhost:9090](http://localhost:9090)
- Grafana - [localhost:3000](http://localhost:3000)
- Swagger Ui - [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- [Ir para endpoints](#api-endpoints) para mais informações

### Observação

- Se o conteiner do AnimeListor iniciar antes do conteiner do MySql pode ocorrer um erro ao se conectar ao banco de dados
- Caso ocorra, aperte Ctrl C no terminal para parar o Docker e execute o comando:
```
docker compose up
```
- Quando desejar parar a execução dos contêineres execute o comando:

```
docker compose down
```
- Caso deseje excluir as imagens execute os seguintes comando:
- Para Listar todas as imagens:
```
docker image ls
```
- Selecione e copie o IMAGE ID da imagem que deseja excluir e execute o seguinte comando:
```
docker rmi aqui_o_id_da_imagem
```

## Prometheus

- Com a aplicaçao sendo executada acesse [localhost:9090](http://localhost:9090)
- No campo de pesquisa, escreva a métrica desejada ou utilize o "<b>Open metrics explore</b>" mais a direita e selecione a métrica desejada.
- Execute o comando
- Clique em "<b>Graph</b>" e as informações da métrica serão exibidas em um gráfico

 ## Grafana

- Com a aplicaçao sendo executada acesse [localhost:3000](http://localhost:3000)
- Conecte-se com Login: admin Senha: admin
- Clique em Skip 
- Clique em Data Source para adicionar o Prometheus como uma fonte de dados
- Selecione o Prometheus
- Em HTTP escreva <b>http://host.docker.internal:9090</b>
- Desça até o fim da página e clique em "<b>Save & test</b>"
- No topo a esquerda da página clique no toggle menu
- Clique em Dashboards
- Clique no botão New e em seguida Import
- Adicionaremos uma Dashboard personalizada, escreva: 4701 e clique em Load
- Selecione o Prometheus
- Clique em Import
- Agora existe uma Dashboard mostrando os dados que estão sendo coletados pelo Prometheus

## Como Executar a Aplicação com o Maven

- Clone o repositório git
- Dentro da pasta do AnimeListor, abra o arquivo docker-compose.yml e remova ou comente a imagem do anime-listor
- A porta do host para o MySQL do docker-compose está configurada para ser a 3307
- Caso deseje utilizar o MySQL instalado em seu computador, comente ou remova a imagem MySQL do docker-compose, vá ao arquivo application.properties no caminho: AnimeListor\src\main\resources e altere as propriedades: url, username, password de modo que satisfaçam as configurações do MySQL do seu computador
- Certifique-se de ter alterado a porta na propriedade url de 3307 para a padrão 3306 ou outra que você utilizar
- Acesse a pasta do projeto através de um terminal (prompt de comando)
- Caso esteja utilizando windows, abra a pasta do AnimeListor, copie o caminho absoluto e em um terminal escreva cd e cole o caminho absoluto
- Exemplo: cd C:\Users\UserA\eclipse-workspace-j17\AnimeListor
- Em seguida execute os comandos a seguir no terminal:
- Subir os contêineres: mysql, prometheus e grafana
```
docker compose up
```
- Aguarde os contêineres iniciarem
- Abra outro terminal e execute os seguintes comandos:
- Construir o projeto:
```
mvnw clean package
```
- Executar a aplicação:
```
java -jar target/AnimeListor-0.0.1-SNAPSHOT.jar
```
- Para encerrar a execução do projeto aperte Ctrl C no terminal.
- A API poderá ser acessada em [localhost:8080/api/v1/animes](http://localhost:8080/api/v1/animes)

## Testes com o Maven

- Abra outro terminal dentro da pasta do projeto AnimeListor
- Para executar os testes de integração utilizar o seguinte comando no terminal:
```
mvn test -Pintegration-tests
```
- Para executar os testes unitários utilizar o seguinte comando no terminal:
```
mvn test
```
## API Endpoints

Para fazer as requisições HTTP abaixo, foi utilizada a ferramenta [postman](https://www.postman.com) <br>
Ao utilizar uma ferramenta como o postman, certifique-se de fazer a autenticação na requisição através da aba "<b>Authorization</b>" informando que ela é uma "<b>Basic Auth</b>" e com o login e senha de um dos perfis mencionados na sessão [Login](#login) <br>
Para mais informações a respeito dos endpoints acesse a api diratamente pelo navegador utilizando o swagger: [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- Criar Anime (Cria um novo anime de acordo com o "name" passado no corpo da requisição)
```
POST localhost:8080/api/v1/animes

{
  "name":"Shingeki no Kyojin"
}

```

- Listar Animes com Paginação
```
GET localhost:8080/api/v1/animes

```

- Listar todos os Animes sem Paginação
```
GET localhost:8080/api/v1/animes/all

```

- Listar Animes por Nome
```
GET localhost:8080/api/v1/animes/findByName?name=Shingeki no Kyojin

```

- Listar Animes por Id
```
GET localhost:8080/api/v1/animes/1

```

- Atualizar Anime (Atualiza um anime de acordo com o "name" e "id" passados no corpo da requisição)
```
PUT localhost:8080/api/v1/animes

{
  "name":"Attack on Titan",
  "id": 1
}

```

- Remover Anime
```
DELETE localhost:8080/api/v1/animes/1

```
