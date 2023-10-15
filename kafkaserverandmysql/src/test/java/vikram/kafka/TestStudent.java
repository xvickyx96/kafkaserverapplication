package vikram.kafka;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vikram.kafka.Repository.StudentRepository;
import vikram.kafka.payload.Student;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestStudent {

    @Autowired
    StudentRepository studentRepository;



    static Student testStudent;



    @BeforeEach
    void setUp(){
        System.out.println("Before test");
    }

    @AfterEach
    void tearDown(){
        System.out.println("After test");
    }

    @Test
    @Order(1)
    void createStudent() {

        // Skapa ett objekt av User med specifik data
        Student student = new Student();
        student.setStudentFirstName("A");
        student.setStudentLastName("B");


        // Spara till db
        testStudent = studentRepository.save(student);

        assertNotNull(studentRepository.findById(testStudent.getId()).get().getStudentFirstName());
    }

    @Test
    @Order(2)
    void updateStudent() {
        // Hämta user med id 1
        Student fetchedStudent = studentRepository.findById(testStudent.getId()).get();
        assertNotNull(fetchedStudent);

        //Uppdatera värdet i fetchetUser
        fetchedStudent.setStudentFirstName("Niklas");
        studentRepository.save(fetchedStudent);
        assertEquals("Niklas", studentRepository.findById(testStudent.getId()).get().getStudentFirstName());
    }

    @Test
    @Order(3)
    void removeStudent() {
        //Kontrollera att user med ID 1 finns
        assertNotNull(studentRepository.findById(testStudent.getId()).get());

        // Ta bort user med ID och kontrollera att user är borta
        studentRepository.deleteById(testStudent.getId());
        assertTrue(
                studentRepository.findById(testStudent.getId()).isEmpty());


    }
}