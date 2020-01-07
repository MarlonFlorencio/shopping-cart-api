# Shopping-cart-api

Shopping Cart API is an backend application for Shopping Cart

## Artifacts 

  - **Java** as programming language
  - **Spring Boot**
  - **Spring Hateoas**
  - **JWT** as authentication method
  - **MongoDB** as database
  - **Spring JPA** as persistence layer
  - **Flapdoodle-Mongo** as embedded database test
  - **lombok** as library
  - **Maven** as build tool

## Execution

##### Run tests

```bash
$ mvn test
```

Code coverage can be verified in the following file:
```bash
target/site/jacoco/index.html
```

##### Start Server 

To start the app, let's use docker-compose 

```bash
$ mvn clean package
$ docker-compose build
$ docker-compose up 
```

##### Testing using postman

To help to test the API, I created one postman's collection with all requests prepared the execute. 
Is necessary to import the following files:
```bash
postman/SHOPPING_CART_COLLECTION.postman_collection.json
postman/SHOPPING_CART.postman_environment.json
```

Any doubt about import collection and environment postman files, [check here](https://learning.getpostman.com/docs/postman/collection-runs/working-with-data-files/) 
