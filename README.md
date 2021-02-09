# Laborat&oacute;rio 1 - Criando uma API de mensagens

## Descri&ccedil;&atilde;o
**Objetivo:** Criar uma API que obt&eacute;m, cria e exclui uma mensagem.
|M&eacute;todo|Caminho|Par&acirc;metros|Corpo da requisi&ccedil;&atilde;o|Corpo da resposta (Sucesso)|Corpo da resposta (Falha)|Descri&ccedil;&atilde;o|
|------|-----------|-----------|-----------|-----------|-----------|-----------|
|POST|/message|-|{"text": "Alo mundo!"}|{"result": {"id": 1, "text": "Alo mundo!"}, "status": "success"}|{"result": {"message": "Ocorreu um erro na solicitação"}, "status": "fail"}|Cria/Cadastra uma mensagem no banco de dados|
|GET|/message|-|-|{"result": [{"id": 1, "text": "Alo mundo!"},{"id": 2, "text": "Hello world!"}], "status": "success"}|{"result": {"message": "Ocorreu um erro na solicitação"}, "status": "fail"}|Obt&eacute;m todas as mensagens cadastradas|
|GET|/message/{id}|**id**: Inteiro = ID da mensagem|-|{"result": {"id": 1, "text": "Alo mundo!"}, "status": "success"}|{"result": {"message": "Ocorreu um erro na solicitação"}, "status": "fail"}|Obt&eacute;m a mensagem cadastrada pelo ID|
|DELETE|/message/{id}|**id**: Inteiro = ID da mensagem|-|{"result": {"id": 1, "text": "Alo mundo!"}, "status": "success"}|{"result": {"message": "Ocorreu um erro na solicitação"}, "status": "fail"}|Exclui a mensagem cadastrada pelo ID|

## 1. Adicionando bibliotecas no pom.xml
Abra o arquivo **pom.xml**

Na parte **&lt;dependencies&gt;** adicione as seguintes bibliotecas que ser&atilde;o usadas na aplica&ccedil;&atilde;o:
- org.springframework.boot.spring-boot-starter-test 2.4.2
- org.springframework.boot.spring-boot-starter-web 2.4.2
- org.springframework.boot.spring-boot-starter-data-jpa 2.4.2
- com.h2database.h2 1.4.200
- org.liquibase.liquibase-core 4.2.2
- junit.junit 4.13.1

Exemplo:
```xml
<dependency>
    <groupId>junit</groupId>
    <artifactId>junit</artifactId>
    <version>4.13.1</version>
    <scope>test</scope>
</dependency>
```

## 2. Adicionando plugins no pom.xml
Abra o arquivo **pom.xml**

Na parte **&lt;plugins&gt;** adicione os seguintes plugins que ser&atilde;o usados na hora da compila&ccedil;&atilde;o, build e testes no maven:
- org.apache.maven.plugins.maven-jar-plugin 3.2.0
- org.apache.maven.plugins.maven-compiler-plugin 3.8.1
- org.apache.maven.plugins.maven-surefire-plugin 3.0.0-M5

Exemplo:
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.2.0</version>
</plugin>
```

## 3. Criando a classe principal (Main)
Passo a passo:
- Criar a classe p&uacute;blica **Main** no pacote **com.sensedia.poc.message**;
- Adicionar as seguintes anota&ccedil;&otilde;es na classe:
```java
//Para que o Spring "busque" as classes para injecao de dependencias (@Autowired) a partir do pacote declarado
@SpringBootApplication(scanBasePackages = "com.sensedia.poc.message")
//Habilita o JPA (Banco de dados)
@EnableJpaRepositories
```
- Nesta classe, criar um m&eacute;todo p&uacute;blico e est&aacute;tico chamado **main**, com um par&acirc;metro do tipo array de String (String[]) e que pode lan&ccedil;ar a exce&ccedil;&atilde;o gen&eacute;rica **Exception**;
- Adicionar a seguinte instru&ccedil;&atilde;o no corpo deste m&eacute;todo:
```java
SpringApplication.run(Main.class);
```

## 4. Criando a classe de entidade Message
Passo a passo:
- Criar a classe p&uacute;blica **Message** no pacote **com.sensedia.poc.message.repository.model**;
- Adicionar as seguintes anota&ccedil;&otilde;es na classe:
```java
//Para que o JPA saiba que a entidade se referencia a tabela TB_MENSAGEM
@Table(name = "TB_MENSAGEM")
//Indica que a classe eh uma entidade para o Spring
@Entity
```
- Nesta classe, criar as seguintes propriedades (com get/set):
```java
//Indica para o JPA que esta propriedade eh o identificador unico da tabela.
@Id
//Indica que esta propriedade se referencia a coluna TB_MENSAGEM.ID
@Column(name = "ID", nullable = false, unique = true)
//Indica que o valor do campo eh gerado automaticamente
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer id;

//Indica que esta propriedade se referencia a coluna TB_MENSAGEM.TEXTO
@Column(name = "TEXTO", nullable = false)
private String text;
```
- Criar dois construtores, um sem par&acirc;metro, outro passando os valores para todas as propriedades;
- Adicionar o m&eacute;todo **equals** nesta classe (geralmente, pode ser gerado automaticamente por uma IDE):
```java
@Override
public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    Message other = (Message) obj;
    if (id == null) {
        if (other.id != null)
            return false;
    } else if (!id.equals(other.id))
        return false;
    if (text == null) {
        if (other.text != null)
            return false;
    } else if (!text.equals(other.text))
        return false;
    return true;
}
```

## 5. Criando a interface de repositório MessageRepository
Passo a passo:
- Criar a interface p&uacute;blica **MessageRepository** no pacote **com.sensedia.poc.message.repository**;
- Estender da interface **JpaRepository**:
```java
//Contem metodos de buscas, exclusoes e salvamentos
@Repository
public interface MessageRepository extends org.springframework.data.jpa.repository.JpaRepository<Message,Integer> {
}
```
- Adicionar a seguinte anota&ccedil;&atilde;o na interface:
```java
//Indica que a interface eh um repositorio para o Spring
@Repository
```

## 6. Criando a interface de servi&ccedil;o MessageService
Passo a passo:
- Criar a interface p&uacute;blica **MessageService** no pacote **com.sensedia.poc.message.service**;
- Criar os seguintes m&eacute;todos:

|M&eacute;todo|Par&acirc;metros|Retorno|Lan&ccedil;a exce&ccedil;&atilde;o?|Descri&ccedil;&atilde;o|
|----------|----------|----------|----------|-------------------------------|
|createMessage|text: String|Message|N&atilde;o|Cria uma nova mensagem no banco de dados|
|getMessages|-|List&lt;Message&gt;|N&atilde;o|Obt&eacute;m todas as mensagens cadastradas|
|getMessage|id: Integer|Message|Sim (lan&ccedil;a quando n&atilde;o acha a mensagem)|Obt&eacute;m a mensagem cadastrada atrav&eacute;s de seu ID|
|removeMessage|id: Integer|Message|Sim (lan&ccedil;a quando n&atilde;o acha a mensagem)|Remove a mensagem cadastrada pelo ID|

## 7. Implementando a interface MessageService na classe de servi&ccedil;o MessageServiceImpl
Passo a passo:
- Criar a classe p&uacute;blica **MessageServiceImpl** no pacote **com.sensedia.poc.message.service.impl**;
- Colocar a seguinte anota&ccedil;&atilde;o na classe MessageServiceImpl:
```java
//Indica ao Spring que esta classe eh de servico
@Service
```
- Adicionar a vari&aacute;vel:
```java
//Indica para o Spring injetar a dependencia
@Autowired
private MessageRepository messageRepository;
```
- Estender e implementar os m&eacute;todos da interface **MessageService** na classe **MessageServiceImpl**. Por exemplo:
```java
//...
@Autowired
private MessageRepository messageRepository;
//...
//Indica a sobrecarga no metodo (reescrita)
@Override
//Indica a transacao escolhida para o JPA (neste caso, uma transacao [begin/commit ou begin/rollback] eh aberta caso nao exista mas, se existir, ela usa a correntr)
@Transactional(propagation = Propagation.REQUIRED)
public List<Message> getMessages() {
   return messageRepository.findAll();
}
```

## 8. Criando a enumera&ccedil;&atilde;o Status
Passo a passo:
- Criar a enumera&ccedil;&atilde;o p&uacute;blica **Status** no pacote **com.sensedia.poc.message.controller.bean** com dois valores: **success** e **fail**;

## 9. Criando as classes de beans
### Criando a classe ResultResponseBean
Passo a passo:
- Criar a classe p&uacute;blica **ResultResponseBean** no pacote **com.sensedia.poc.message.controller.bean** com as seguintes propriedades (com get/set):
```java
private Object result;
private Status status;
```
- Criar dois construtores, um sem par&acirc;metro, outro passando os valores para todas as propriedades;
### Criando a classe ErrorResponseBean
Passo a passo:
- Criar a classe p&uacute;blica **ErrorResponseBean** no pacote **com.sensedia.poc.message.controller.bean** com a seguinte propriedade (com get/set):
```java
private String message;
```
- Criar dois construtores, um sem par&acirc;metro, outro passando os valores para todas as propriedades;
### Criando a classe MessageBodyRequestBean
Passo a passo:
- Criar a classe p&uacute;blica **MessageBodyRequestBean** no pacote **com.sensedia.poc.message.controller.bean** com a seguinte propriedade (com get/set):
```java
private String text;
```
- Criar dois construtores, um sem par&acirc;metro, outro passando os valores para todas as propriedades;

## 10. Criando a classe de controller
Passo a passo:
- Criar a classe **MessageController** no pacote **com.sensedia.poc.message.controller**;
- Adicionar a vari&aacute;vel:
```java
//Indica para o Spring injetar a dependencia
@Autowired
private MessageService messageService;
```
- Criar os seguintes m&eacute;todos:

|M&eacute;todo|Par&acirc;metros|Retorno|Anota&ccedil;&otilde;es|Descri&ccedil;&atilde;o|
|----------|--------------------|--------------------|----------|-------------------------------|
|createMessage|@RequestBody body: MessageBodyRequestBean|ResponseEntity&lt;ResultResponseBean&gt;|@PostMapping("/message")|Cria uma nova mensagem no banco de dados atrav&eacute;s do endpoint POST /message|
|getMessages|-|ResponseEntity&lt;ResultResponseBean&gt;|@GetMapping("/message")|Obt&eacute;m todas as mensagens cadastradas atrav&eacute;s do endpoint GET /message|
|getMessage|@PathVariable("id") id: Integer|ResponseEntity&lt;ResultResponseBean&gt;|@GetMapping("/message/{id}")|Obt&eacute;m a mensagem cadastrada atrav&eacute;s do endpoint GET /message/{id}|
|removeMessage|@PathVariable("id") id: Integer|ResponseEntity&lt;ResultResponseBean&gt;|@DeleteMapping("/message/{id}")|Remove a mensagem cadastrada atrav&eacute;s do endpoint DELETE /message/{id}|

Exemplo:
```java
@PostMapping("/message")
public ResponseEntity<ResultResponseBean> createMessage(@RequestBody MessageBodyRequestBean message) {
    try {
        final ResultResponseBean result = new ResultResponseBean(messageService.createMessage(message.getText()), Status.success);
        return new ResponseEntity<ResultResponseBean>(result, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<ResultResponseBean>(new ResultResponseBean(new ErrorResponseBean("Ocorreu um erro na solicitação"), Status.fail), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

## 11. Criando arquivo de configurações (application.properties)
Passo a passo:
- Criar o arquivo **application.properties** na pasta **src/main/resources/** com as seguintes propriedades:
```properties
#Classe(Driver) do banco de dados H2
spring.datasource.driverClassName=org.h2.Driver
#URL de conexao para o banco de dados H2
spring.datasource.url=jdbc:h2:mem:mensagem
#Usuario de conexao para o banco de dados H2
spring.datasource.username=sa
#Senha de conexao para o banco de dados H2
spring.datasource.password=
#Dialeto SQL (instrucoes) usadas (H2)
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
```

## 12. Criando arquivos de preparação (esquema/estrutura/tabelas) do banco de dados (liquibase)
Passo a passo:
- Criar o arquivo **001-schema.yaml** na pasta **src/main/resources/db/changelog/schema/** com as seguintes instru&ccedil;&otilde;es:
```yaml
databaseChangeLog:
  - changeSet:
      id: 1
      author: ricardo.takemura@sensedia.com
      #Verifica se a tabela TB_MENSAGEM nao existe, se existir eh interrompido o processo
      preConditions:
        onFail: HALT
        not:
          tableExists:
            tableName: TB_MENSAGEM
      #Cria a tabela TB_MENSAGEM com as colunas ID e TEXTO, se nao existir
      changes:
        - createTable:
            tableName: TB_MENSAGEM
            columns:
              - column:
                  name: ID
                  type: int
                  constraints:
                    primaryKey: true
                    nullable: false
              - column:
                  name: TEXTO
                  type: varchar(255)
                  constraints:
                    nullable: false
        - addAutoIncrement:
            tableName: TB_MENSAGEM
            columnName: ID
            columnDataType: int
            incrementBy: 1
```
- Criar o arquivo **db.changelog-master.yaml** na pasta **src/main/resources/db/changelog/** com as seguintes instru&ccedil;&otilde;es:
```yaml
databaseChangeLog:
  #inclui o arquivo acima para execucao no inicio da aplicacao
  - include:
      file: /db/changelog/schema/001-schema.yaml
```
