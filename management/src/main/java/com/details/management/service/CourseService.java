package com.details.management.service;

import com.details.management.exception.DataResourceNotFoundException;
import com.details.management.model.Course;
import com.details.management.dao.CourseRepository;
import com.details.management.model.Department;
import com.details.management.model.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    InstructorService instructorService;

    @Transactional
    public Course addCourse(Course course) throws DataResourceNotFoundException {
        Instructor instructor = instructorService.getInstructorByInstructorId(course.getInstructor().getInstructorID());
        course.setInstructor(instructor);

        Department department = departmentService.getDepartmentByDepartmentName(course.getDepartment().getName());
        course.setDepartment(department);

        return courseRepository.save(course);
    }

    @Transactional
    public Course updateCourse(int courseId, Course course) throws DataResourceNotFoundException {
        if(course.getCourseID() == 0 ) {
            course.setCourseID(courseId);
        }

        Instructor instructor = instructorService.getInstructorByInstructorId(course.getInstructor().getInstructorID());
        course.setInstructor(instructor);

        Department department = departmentService.getDepartmentByDepartmentName(course.getDepartment().getName());
        course.setDepartment(department);

        return courseRepository.save(course);
    }

    public Course getCoursesByCourseId(int courseId) throws DataResourceNotFoundException {
        return courseRepository.findById(courseId).orElseThrow(() -> new DataResourceNotFoundException("Requested course not found"));
    }

    @Transactional
    public void deleteCourseRecordById(int courseId) {
        courseRepository.deleteById(courseId);
    }

    public List<Course> getAllCourseRecords() {
        return (List<Course>) courseRepository.findAll();
    }
}