package com.example.universitystudent.controller;

import com.example.universitystudent.entity.University;
import com.example.universitystudent.exception.ResourceNotFoundException;
import com.example.universitystudent.repository.StudentRepository;
import com.example.universitystudent.repository.UniversityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class UniversityController {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/universities")
    public List<University> getAllUniversities(){
        return universityRepository.findAll();
    }

    @GetMapping("/universities/{universityName}")
    public ResponseEntity<University> getUniversityByName(@PathVariable(value = "universityName") String universityName){
        University university = universityRepository.findById(universityName)
                .orElseThrow(() -> new ResourceNotFoundException("University by name " + universityName + " doesn't exist"));
        return ResponseEntity.ok(university);
    }

    @PostMapping("/universities")
    public University createUniversity(@RequestBody University university){
        return universityRepository.save(university);
    }

    @DeleteMapping("/universities/{universityName}")
    public ResponseEntity<?> deleteUniversity(@PathVariable String universityName) {
        return universityRepository.findById(universityName).map(university -> {
            universityRepository.delete(university);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("University by name " + universityName + " not found"));
    }

}
