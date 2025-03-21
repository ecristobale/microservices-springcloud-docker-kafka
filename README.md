# Microservices with Spring Cloud, Docker, Kafka, Swagger (OpenAPI), PostgreSQL, Spring Boot 3

## DEVELOPER
 * Eduardo Cristóbal
 * Linkedin: [https://www.linkedin.com/in/ecristobale](https://www.linkedin.com/in/ecristobale)
 * GitHub: [https://github.com/ecristobale](https://github.com/ecristobale)
 * Email: [Eduardo Cristóbal - email](mailto:edu_ce_1988@hotmail.com)

## DESCRIPTION
The **goal** of this project is to create a **microservices** system using **Spring Boot 3** and **Java 17** . The following technologies and tools are used:
 * Spring Cloud
 * Swagger (OpenAPI)
 * Spring Cloud Streams with Kafka
 * Docker: dockerized each microservice and resource (Kafka, Mongo DB, Postgre DB)
 * Microservices: Registry Service (Eureka), Config Service, Feign, Load Balancer, Spring Cloud Gateway, Circuit Breaker pattern
 * Spring Security Oauth2 + JWT
 * DBs: PostgreSQL and MongoDB
 * Postman
 * DBeaver and Studio 3t
 * IntelliJ
 * Visual Studio Code
 * Sourcetree
 * Docker Desktop
  
### Microservices system arquitecture:
 
![Alt text](readme-screenshots/01-microservices-arquitecture.png?raw=true "Microservices system arquitecture")

### Screenshots:

* Eureka Server registered some instances of companies microservice:
![Alt text](readme-screenshots/02-microservices-eureka.png?raw=true "Eureka Server")

* Swagger (OpenAPI) for companies microservice:
![Alt text](readme-screenshots/03-microservices-swagger-openapi.png?raw=true "Swagger (OpenAPI)")

* GitHub project with .yml files (profiles: default, prod and qa) for config-server:
![Alt text](readme-screenshots/04-microservices-config-server-github-yml.png?raw=true "GitHub .yml files for Config Server")

* Feign with Load Balancer to communicate between microservices:
![Alt text](readme-screenshots/05-microservices-feign-with-load-balancer.png?raw=true "Feign with Load Balancer")

* Spring Cloud Streams with Kafka: consuming Kafka message and saving it to MongoDB
![Alt text](readme-screenshots/06-microservices-kafka-mongo.png?raw=true "Consuming Kafka message and saving it to MongoDB")

* Dockerized each ms and resource from docker-compose:
![Alt text](readme-screenshots/07-docker-view-compose.png?raw=true "Dockerized each ms and resource")

* Dockerized each ms and resource from Docker desktop:
![Alt text](readme-screenshots/08-docker-view-desktop.png?raw=true "Dockerized each ms and resource")
