



# Application Structure

### Model

Domain model is organized under the **model** package and it consists of entity classes. Entities use various annotations that describe the
relationships between each other. All these annotations are used by JPA in order to map entities to database tables.

### Repository

Repositories are interfaces that are responsible for data persistence and retrieval. The repository layer is an abstraction that provides all
CRUD functionality and keeps hidden the data related information (e.g. specific database implmentation) from the other layers. This layer
should always persist entities.

### Service

Service layer depends on the repository layer and provides separation of concern, encapsulating all the business logic implementation. It is
there to apply business rules on data sent to and from the repository layer. Service layer does not care about the specific database implementation
and provides loose coupling. This technique makes the application super flexible in a possible data source replacement. This layer should
always receive and return DTOs.

### Controller

Controller layer depends on the service layer and is responsible for the incoming requests and the outgoing responses. A controller determines all the
available endpoints that client side (or other api) is able to call. This layer should not apply logic on the receiving or returning data.



# Quick Start

## Prerequisites

#### 1. Create a MySQL database

```
CREATE DATABASE new_universal_health_services;
```
In case you want to use a different database name, follow the next steps:
 -  ```
    CREATE DATABASE DB_NAME;
    ```
 - Open ```src/main/resources/application.properties``` file
 - Change ```db.name``` property to match your preferred database name DB_NAME

#### 2. Modify MySQL username and password

- Open ```src/main/resources/application.properties``` file
- Change ```spring.datasource.username``` and ```spring.datasource.password``` properties to match your MySQL connection

## Build project

Build the application using the following **maven wrapper** command:
```
./mvnw clean package
```
In case you want to use this project as a dependency in other
projects locally, use the following command:
```
./mvnw clean install
```
Each of these commands will create an executable ```.jar``` file at ```target``` directory.

## Run project

After packaging the application into an executable ```.jar``` file, you can start the server running the following command using any terminal in the project directory:
```
java -jar target/warehouse-0.0.1.jar
```
Alternatively, you can start the server without packaging, by running the following command:
```
./mvnw spring-boot:run
```
The server will start running at http://localhost:8095.







# After running

Every time the server is running, it triggers the flyway migration tool to look for possible schema changes. The first time, flyway will create a table called
```schema_version```, in order to write down all the scripts have already run, in a versioning way. Additionally, it will run all the available scripts at
```src/main/resources/db/mysql/``` directory.  

After the first run, flyway will detect and run only the new scripts.



# API Documentation

When server is up and running, you can use swagger to explore the available endpoints and try them out. Find it at:
http://localhost:8095/swagger-ui/
