# kafkaServerwithMySQL
## An application that is connected to kafka server and mySQL

## Description
This is an application that lets the user/admin from a school save information about students. The information that the application will save about the student is their student id, firstname and lastname. The application lets the user add new students and see all students that are saved.

What motivated me to do this project is to learn how to work with mysql database and Apache Kafka.


## Installation
- Install IntelliJ IDEA
- Install Apache Kafka
- Install MySQL workbench
- Clone the project on GitHub and open it in IntelliJ
- open the server file from config file that is located in your Apache Kafka
- Find this inside the server file and change “log.dirs=/tmp/kafka-logs” to
log.dirs="the address to kafka map"/kafka-logs>
- Open the zookeeper file from config file that is located in your Apache Kafka file
- Find this inside the zookeeper file and change “dataDir=/tmp/zookeeper” to dataDir="the address to kafka map"/tmp/zookeeper
- You then need to open two different command prompts and write the address to kafka map in both command prompts.
- In the first prompt write: .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
- In the second prompt write:  .\bin\windows\kafka-server-start.bat .\config\server.properties
- Now you need to start KafkaaSpingBootExempleApplication class and then main class


## Dependencies
Spring Boot Starter Web
MySQL Connector/J
JUnit Jupiter API
Apache Http Client5
JSON.simple
Spring Kafka
Spring Boot Starter Test
Spring Boot Kafka Test

## License
MIT License

## Tests
The tests are found in the classes named DatabaseTest and KafkaTests that test the connection with database and kafka.
