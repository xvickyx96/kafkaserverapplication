package vikram.kafka.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vikram.kafka.payload.Student;

@Service
public class JsonKafkaConsumerStudent {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumerStudent.class);

    @KafkaListener(topics = "studentTopicNew", groupId = "studentGroupNew")
    public void consume(Student student) {
        LOGGER.info(String.format("Student message received -> %s", student.toString()));
        // Här kan du lägga till din logik för att bearbeta studentdata, t.ex., spara i databasen.
    }
}
