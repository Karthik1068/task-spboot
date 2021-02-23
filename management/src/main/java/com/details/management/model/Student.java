package com.details.management.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentID;
    private String firstName;
    private String lastName;
    private long phone;

    @JsonIgnore
    @ManyToMany
    Set<Course> courses;
}
