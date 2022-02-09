package com.example.universitystudent.repository;

import com.example.universitystudent.entity.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<University, String> {

}
