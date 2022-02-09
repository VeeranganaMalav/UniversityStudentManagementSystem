package com.example.universitystudent.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    @Column(name = "student_id")
    private long studentId;

    @Column(name = "student_name")
    private String studentName;

    @Column(name = "student_phone_number")
    private String studentPhoneNumber;

    @Column(name = "student_email")
    private String studentEmailId;

    private String gender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "university_name", referencedColumnName = "university_name")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "students"})
    private University university;

}
