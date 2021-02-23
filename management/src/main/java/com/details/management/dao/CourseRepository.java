package com.details.management.dao;

import com.details.management.model.Course;
import org.springframework.data.repository.CrudRepository;


public interface CourseRepository extends CrudRepository<Course, Integer> {

}