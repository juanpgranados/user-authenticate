# User Authenticate :cop:

Test Paper - Part 3

This is a Spring Boot component which implements authentication control for failed attempts. It is developed in Java 
11 and Spring Boot 2.5.3, and implements JPA-Hibernate and Spring Security. It uses an H2 in memory database.

# Getting Started
### Requirements :memo:
* JDK 11
* Maven

### Installation :wrench:

Once you have cloned the repository, you can install it on your maven repository by maven command line

```bash
mvn clean install
```

And then, execute jar file
```bash
java -jar user-authenticate-0.0.1-SNAPSHOT.jar
```

### How to use :calling:
At first, you should go to http://localhost:8080/, then you can see the login page. Also, in another tab, open 
http://localhost:8080/h2-console to see H2 console.

The default users credentials are
```
jpg     123
admin   890*
```
The default access to H2 is (also can be checked in the `application.properties` file)
```
JDBC    jdbc:h2:mem:userdb
User    sa
Pass    123*
```
Now, in the login page, you can try with a given username and wrong password. Then check in the Database by 
executing the query
``` sql
SELECT * FROM USER
```
After 3 failed attempts, you should see a message `Account is locked since you have exceeded the number of attempts`.

Each hour, users locked more than 24 hours before are unlocked.
## Testing execution :zap:
Junit tests can be executed by running the command:
``` bash
mvn test
```
## Key libraries and tools

-   **Java** Programming language.
-   **Spring Data JPA** Persistence layer
-   **H2** In memory database
-   **Spring Security** Enables JWT security
-   **Maven** Build automation tool.
-   **JUnit** Unit testing framework.
-   **Lombok** Java library to avoid write repetitive code.