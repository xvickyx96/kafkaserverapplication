# Apache Kafka
## TopicConfig
The TopicConfig class has a Bean method that uses TopicBuilder to create a topic with the name "studentTopicNew". This topic is then used for sending and receiving messages.
## JsonKafkaProducer
The JsonKafkaProducer class implements the Kafka producer to send messages to the topic "studentTopicNew". To send a message, a KafkaTemplate<String, Object> is injected from this Service class. The sendStudent method creates a message that is sent to the topic using kafkaTemplate.send(message). The class also has a logger that informs the user when the message has been sent.
## JsonKafkaConsumer
The JsonKafkaConsumer class implements the Kafka consumer that receives messages from the topic "studentTopicNew". The class uses the KafkaListener annotation to listen for messages from the topic. The consume method is used to log received messages.
## JsonKafkaConsumerDb
The JsonKafkaConsumerDb class is used to send data to the database. The class interacts with the database after injecting the StudentRepository, which is used in the writeToDB method to save student data in the database when messages are received from Kafka. The class also uses the KafkaListener annotation to listen for messages from the topic "studentTopicNew".
