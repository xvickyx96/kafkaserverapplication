package vikram.kafka.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vikram.kafka.Repository.StudentRepository;
import vikram.kafka.payload.Student;

@Service
public class JsonKafkaConsumerStudentDb {

    @Autowired
    private StudentRepository studentRepository;

        @KafkaListener(topics = "studentTopicNew", groupId = "studentGroupNew2")
        public void writeToDB(Student student) {

            // Skicka data till DB
            studentRepository.save(student);
        }


}
