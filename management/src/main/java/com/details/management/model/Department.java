package com.details.management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "department")
public class Department {
    @Id
    private String name;
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Course> courses;

}