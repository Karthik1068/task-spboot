package com.details.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int courseID;
    private int duration;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructorID", insertable = true, updatable = false)
    private Instructor instructor;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "departmentName", insertable = true, updatable = false)
    private Department department;

    @JsonIgnore
    @ManyToMany
    Set<Student> students;

}