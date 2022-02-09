package com.example.universitystudent.controller;

import com.example.universitystudent.entity.Student;
import com.example.universitystudent.entity.University;
import com.example.universitystudent.exception.ResourceNotFoundException;
import com.example.universitystudent.repository.StudentRepository;
import com.example.universitystudent.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/students")
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable(value = "studentId") long id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student by id " + id + " doesn't exist"));
        return ResponseEntity.ok(student);
    }

    @PutMapping("/universities/{universityName}/students/{studentId}")
    public Student updateStudent(@PathVariable(value = "universityName") String universityName,@PathVariable(value = "studentId") long id,@RequestBody Student studentDetails){

        if (!universityRepository.existsById(universityName)){
            throw new ResourceNotFoundException("University by name " + universityName + " doesn't exist");
        }

        return studentRepository.findById(id).map(student -> {
            student.setStudentName(studentDetails.getStudentName());
            student.setStudentEmailId(studentDetails.getStudentEmailId());
            student.setStudentPhoneNumber(studentDetails.getStudentPhoneNumber());
            student.setGender(studentDetails.getGender());
            return studentRepository.save(student);
        }).orElseThrow(() -> new ResourceNotFoundException("Student by id " + id + " doesn't exist"));
    }


    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "studentId") long id) {
        return studentRepository.findById(id).map(student -> {
            studentRepository.delete(student);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Student by id " + id + " not found"));
    }


    @GetMapping("/universities/{universityName}/students")
    public List<Student> getStudentsByUniversity(@PathVariable(value = "universityName") String universityName){

        List<Student> students = universityRepository.getById(universityName).getStudents();
        return students;
    }

    @GetMapping("/universities/{universityName}/students/{studentId}")
    public Student getStudentByUniversityAndId(@PathVariable(value = "universityName") String universityName, @PathVariable(value = "studentId") long studentId){

        Student student = universityRepository.getById(universityName).getStudents().get(Long.valueOf(studentId).intValue());
        return student;
    }

    @PostMapping("/universities/{universityName}/student")
    public Student createStudent(@PathVariable(value = "universityName") String universityName, @RequestBody Student student){

        try{
            University university = universityRepository.getById(universityName);
            student.setUniversity(university);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return studentRepository.save(student);
    }

}