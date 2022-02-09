package com.example.universitystudent.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "university")
public class University {

    @Id
    @Column(name = "university_name")
    private String universityName;

    @Column(name = "university_location")
    private String universityLocation;

    @OneToMany(mappedBy = "university", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "university"})
    private List<Student> students;
}
