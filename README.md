# Learn CRUD with Quarkus

This project demonstrates a basic CRUD (Create, Read, Update, Delete) application using [Quarkus](https://quarkus.io/). It integrates with MySQL, Flyway for database migrations, and includes an OpenAPI specification for API documentation.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.8+
- Docker (optional, for running MySQL)
- MySQL database

## Configuration

The application configuration is defined in the `application.yaml` file:

```yaml
quarkus:
  datasource:
    db-kind: mysql
    username: root
    password: 
    jdbc:
      url: jdbc:mysql://localhost:3306/learn-crud-teacher
  hibernate-orm:
    dialect: org.hibernate.dialect.MySQLDialect
    log:
      sql: true
    sql-load-script: import.sql
  http:
    port: 8083
  flyway:
    migrate-at-start: true
    clean-at-start: false

logging:
  level: INFO

smallrye-openapi:
  info:
    title: Your API Title
    version: 1.0.0
  path: /openapi
```

## Database Schema

The following schema is used for creating the teacher table:

```
CREATE TABLE teacher
(
    nip      VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NULL,
    classes  VARCHAR(255) NOT NULL,
    dob      DATE NULL,
    phoneNo  VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    subjects VARCHAR(255) NOT NULL,
    CONSTRAINT pk_teacher PRIMARY KEY (nip)
);
```

## Running the Application
### Start the MySQL Database

You can use Docker to run a MySQL instance:

```
docker run --name quarkus-mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=learn-crud-quarkus -p 3306:3306 -d mysql:8.0
```

## Build and Run the Application
### Build and run the application in development mode:

```
mvn clean compile quarkus:dev
```

### Access the API

*   **API Base URL**: http://localhost:8083

*   **OpenAPI documentation**: http://localhost:8083/openapi


Maven Configuration
-------------------

The pom.xml is configured to include dependencies for Quarkus, Flyway, Hibernate, and more:

*   Quarkus Hibernate ORM

*   Quarkus Flyway

*   Quarkus RESTEasy

*   Quarkus SmallRye OpenAPI


Refer to the pom.xml file in the repository for full details.

API Endpoints
-------------

### 1\. **Create Teacher**

**POST** /teacher

*   **Request Body**:

```
{
  
      "name": "string",
      "nip": "string",
      "classes": "string",
      "dob": "2022-03-10",
      "phoneNo": "string",
      "email": "string",
      "subjects": "string"

}
```


### 2\. **Get All Teacher**

**GET** /teacher

### 3\. **Get Teacher by NIP**

**GET** /teacher/{nip}

### 4\. **Update Teacher**

**PUT** /teacher/{nip}

*   **Request Body**:

```
{
      "name": "string",
      "nip": "string",
      "classes": "string",
      "dob": "2022-03-10",
      "phoneNo": "string",
      "email": "string",
      "subjects": "string"
}
```

### 5\. **Delete Teacher**

**DELETE** /teacher/{nip}

### 6\. **Get Teacher By Subjects **

**GET** /teacher/{subjects}



Development Notes
-----------------

*   **Live reload**: Quarkus automatically reloads changes in dev mode (quarkus:dev).

*   **Database migrations**: Flyway is configured to automatically handle database migrations on application startup.


License
-------

This project is licensed under the MIT License. See the LICENSE
