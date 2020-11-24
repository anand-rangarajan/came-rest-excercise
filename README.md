# Network API(Frontend service) and Devices Repo API (Backend service)
Network API is front end api. It exposes following rest apis on 8080 port:-
* /network-api/getDevices
* /network-api/getDevice/100
* /network-api/addDevice
* /network-api/removeDevice

Devices-Repo-api is backend api. It exposes rest apis for CRUD on 8081 port:-
* /device-api/getDevices
* /device-api/addDevice
* /device-api/removeDevice
* /device-api/getDevice?deviceId=100

This excercise does the following :
* Validates incoming JSON request against JSON Schema (network-api-schema.json)
* Transforms front end JSON request to backend rest api JSON request ( network-api-to-device)
* Validates backend JSON request against JSON Schema (device-service-schema.json)
* Forwards the request to backend service device-api(A rest service with in-memory H2 db)
* Validates backend JSON response against JSON Schema (device-service-schema.json)
* Transforms backend device-api response to front end network-api response

## Technology ##
1. Spring Boot: v2.2.5
2. Apache Camel: v3.0.0
3. Java: v1.8
4. Wiremock
5. Json
6. Docker
7. Git

## How to Build and Run ##
Both spring boot applications need to be built and run. Starting with backend service "device-api" first.
Clone the repository
git clone https://github.com/anand-rangarajan/came-rest-excercise.git

### network-api (front end rest api) ###

1. cd <repo-path>/came-rest-excercise/network-api
2. mvn clean install
3. cd target
4. java -jar network-api.jar

___API URL /network-api/addDevice___ http://http://localhost:8080/network-api/addDevice

* HTTP POST
* ____JSON REQUEST-Happy Scenario___

{
        "deviceId": 1,
        "deviceName": "Device01",
        "type": "Switch"
    }
    
* ____JSON RESPONSE-Happy Scenario___

{
    "status": "SUCCESS",
    "message": "Added Device01 successfully."
}


* ___JSON REQUEST-Error Scenario___
{
        "deviceId": 2,
        "type": "Switch"
    }
    
* ___JSON RESPONSE-Error Scenario___

{
    "timestamp": "2020-11-24T08:31:53.517+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "deviceName: null found, string expected",
    "path": "/network-api/addDevice"
}

___API URL /network-api/removeDevice/{deviceId}___ http://http://localhost:8080/network-api/removeDevice/1

* HTTP GET

* ____JSON RESPONSE- Happy Scenario___

{
    "status": "SUCCESS",
    "message": "Device removed successfully"
}

### device-api (backend rest api) ###

1. cd <repo-path>/rest-api/device-api
2. mvn clean install
3. cd target
4. java -jar device-api.jar
  
___API URL /device-api/addDevice___ http://localhost:8085/device-api/addDevice

* HTTP POST
* ___JSON REQUEST-Happy Scenario___

{
    "id": 1,
    "name": "Device01",
     "type" : "Switch"
}


* ____JSON RESPONSE___ Added Device01 successfully.

___API URL /device-api/getDevices___ http://localhost:8081/device-api/getDevices

* HTTP GET

* ___JSON RESPONSE___
[
    {
        "id": 1,
        "name": "Device01",
        "type": "Router"
    },
    {
        "id": 2,
        "name": "Device02",
        "type": "Switch"
    }
]

___API URL /device-api/getDevice/{id}___ http://localhost:8085/device-api/getdevice/1

* HTTP GET
* ___JSON RESPONSE___

{
    "id": 1,
    "name": "Device01",
    "type": "Switch"
}

___API URL /device-api/removeDevice?{id}___ http://localhost:8085/device-api/removeDevice?deviceId=1

* HTTP GET
* ____JSON RESPONSE___ Device is removed successfully

## Docker ##
1. docker build -t network-api.jar .
2. docker run -p 8080:8080 network-api.jar