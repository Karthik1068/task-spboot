package com.details.management.dao;

import com.details.management.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;


public interface StudentRepository extends CrudRepository<Student, Integer> {

    Set<Student> findByCourses_Instructor_InstructorID(int instructorId);

}