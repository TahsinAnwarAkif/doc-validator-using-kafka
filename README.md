# IBAN Document Validator

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Functionalities](#functionalities)
* [IBAN Validating API Provider](#iban-validating-api-provider)
* [Instructions on Using Application](#instructions-on-using-application)
* [Functionality Documentation](#functionality-documentation)
* [Future Enhancements](#future-enhancements)

## General info
A simple IBAN document validator app where user can send a .doc/.docx/.pdf file to the server and
validate whether it contains any blacklisted/invalid IBAN or not.

## Technologies
* Java 1.8
* Spring Boot 2.6.4
* Apache Kafka
* Apache Zookeeper
* MySQL

## Functionalities

| Case                                             | User Story                                                                                                                                                                                                                                                                                 |
|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Login                                            | As an API consumer, user/admin can login using username/password, which will give a JWT token if the user is valid.                                                                                                                                                                        |
| Sending File                                     | As an API consumer, user/admin can send a file local/remote URL and file type, which will save the fetched file in the server-local storage. It would also send the whole file as a message through Kafka.                                                                                 |
| Validating File                                  | The sent file is asynchronously validated through the server's Kafka Listener. **openiban** has been used to validate the IBAN number inside the file. After sending the result JSON with proper validations, the locally kept file in the server is deleted using another Kafka Listener. |


## IBAN Validating API Provider

https://openiban.com/
> Example:
> 
> https://openiban.com/validate/DE15%203006%200601%200505%207807%2080?getBIC=true&validateBankCode=true
>
> Where **IBAN** is DE15300606010505780780

```
Result: 
{
"valid": true,
"messages": [
"Bank code valid: 30060601"
],
"iban": "DE15 3006 0601 0505 7807 80",
"bankData": {
"bankCode": "30060601",
"name": "apoBank",
"zip": "40001",
"city": "Düsseldorf",
"bic": "DAAEDEDDXXX"
},
"checkResults": {
"bankCode": true
}
}
```

## Instructions on Using Application
1. Run the queries in pre-ddl.sql, dml.sql in this mentioned order in MySQL. Follow DB credentials specified in application.properties.
2. Run Zookeeper, Kafka in this respective order. Also we need to create 2 topics. Perform these following commands (for Windows):
```
cd <kafka_directory>
bin/windows/zookeeper-server-start.bat config/zookeeper.properties
bin/windows/kafka-server-start.bat config/server.properties
bin/windows/kafka-console-consumer.bat  --bootstrap-server localhost:9092 --topic EVENT_SEND_RCV_TOPIC --from-beginning
bin/windows/kafka-console-consumer.bat  --bootstrap-server localhost:9092 --topic RESULT_EVENT_SEND_RCV_TOPIC --from-beginning
```
3. Run the Spring Boot App.
4. Authenticate initially using this <username, password>: <_akif_, _pass_>. Use the resulted JWT token everytime for other requests.

## Functionality Documentation
Please follow http://localhost:8080/swagger-ui.html to get all the API details.

## Future Enhancements
1. Will add sending/validating bulk files using ThreadLocal/ExecutorService. 
2. Will save files in memory to reduce local-storage overhead.
