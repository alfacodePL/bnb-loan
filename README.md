# Introduction 
This repository contains Assignment from D&B project.

# Contributing

This project follows GitFlow git branching approach.

# Build and Test
Describes and shows how to build code and run the tests. 

### Build process

```
mvn clean install
```
executed from the root project directory

### Test process
```
mvn test
```

###Configuration
All validation rules can be set within the boot\src\main\resources\validation.properties file.

#Running the application
```
cd boot
mvn spring-boot:run
```
Application Swagger UI will be available at
[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

###Logs
Use the boot\src\main\resources\log4j2.properties file to setup the logger. The requestLogger will log all your http request to separate file. 

