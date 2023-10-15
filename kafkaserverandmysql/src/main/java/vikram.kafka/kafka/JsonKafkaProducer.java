package vikram.kafka.kafka;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import vikram.kafka.payload.Student;


@Service
public class JsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);
    private KafkaTemplate<String, Object> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void sendStudent(Student student) {
        LOGGER.info(String.format("Student message sent -> %s", student.toString()));
        Message<Student> message = MessageBuilder.withPayload(student).setHeader(KafkaHeaders.TOPIC, "studentTopicNew").build();
        kafkaTemplate.send(message);
    }

}
