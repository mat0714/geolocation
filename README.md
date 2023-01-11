# Geolocation project

## What is this?
This is small project created for training purposes. The task was to create REST service which will be able to save and get information about geolocation of mobile devices like phones and/or GPS locators. Input from these devices is in JSON format: 
```
{
"deviceId":100,
"latitude":-45.2222,
"longitude":"170.7580"
}
```
Tasks for this project:
- create README.MD
- validate input data
- implement basic authorization
- preapre unit and integration tests

## How to run?
You should have installed Java 17 and Maven 3 or later. 
Type in console in project directory: 
```
mvn spring-boot:run
```
## How to use?
When application is running you can send request on: http://localhost:8080/api/location .

There are two possible types of requests:
- POST (save location using format mentioned in section "What is this")
- GET (get all saved locations)


## Technologies
Java17, Maven, Spring: Boot, Data, Security, Lombok, JUnit, Mockito
