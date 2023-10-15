package vikram.kafka.controller;
import vikram.kafka.kafka.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vikram.kafka.payload.Student;

@RestController
@RequestMapping("/api/v1/kafka")
public class JsonMessageController {

    private JsonKafkaProducer kafkaProducer;

    public JsonMessageController(JsonKafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }


    @PostMapping("/publishStudent")
    public ResponseEntity<String> publishStudent(@RequestBody Student student) {
        kafkaProducer.sendStudent(student);
        return ResponseEntity.ok("Student Message sent to the Topic");
    }

}
