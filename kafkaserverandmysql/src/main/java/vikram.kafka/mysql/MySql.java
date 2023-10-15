package vikram.kafka.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vikram.kafka.Repository.StudentRepository;
import vikram.kafka.payload.Student;


import java.util.List;

@Service
public class MySql {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        Student existingStudent = studentRepository.findById(id).orElse(null);
        if (existingStudent != null) {
            existingStudent.setStudentFirstName(updatedStudent.getStudentFirstName());
            existingStudent.setStudentLastName(updatedStudent.getStudentLastName());
            return studentRepository.save(existingStudent);
        }
        return null; // Om studenten inte hittas
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
