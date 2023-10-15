import org.example.Main;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import vikram.kafka.payload.Student;

import java.util.ArrayList;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class
KafkaTests {

    private static Student student;
    private static JSONObject myObj;

    @BeforeAll
    static void beforeAll() {
        student = new Student();
        student.setStudentFirstName("NewestTestFirst");
        student.setStudentLastName("NewestTestLast");
        student.setId(250L);

        myObj = new JSONObject();
        myObj.put("id", student.getId());
        myObj.put("studentFirstName", student.getStudentFirstName());
        myObj.put("studentLastName", student.getStudentLastName());
    }

    @Test
    @Order(1)
    public void sendToWebAPITest() {
        //Anropa metod för att skicka den
        String resp = Main.sendToWebAPI(myObj);

        //Jämföra response-värden
        assertEquals(resp, "Student Message sent to the Topic");
    }

    @Test
    @Order(2)
    public void getDataFromKafkaTest() {
        //Anropa metod för att hämta Students
        ArrayList<Student> students = Main.getDataFromKafka("studentTopicNew");
        Student testStudent = students.get(students.size() - 1);

        assertEquals( testStudent.getStudentFirstName() , student.getStudentFirstName());
        assertEquals( testStudent.getStudentLastName() , student.getStudentLastName());
        assertEquals( testStudent.getId() , student.getId());
    }
    }



