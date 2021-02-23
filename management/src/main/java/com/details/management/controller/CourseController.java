package com.details.management.controller;

import com.details.management.dto.MessageResponse;
import com.details.management.exception.DataResourceNotFoundException;
import com.details.management.model.Course;
import com.details.management.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/academy/courses")
public class CourseController {
    CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Method to add course
     * @return
     **/
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Course> addCourse(@RequestBody Course course) throws Exception {
        return new ResponseEntity<>(courseService.addCourse(course), HttpStatus.OK);
    }

    /**
     * Method to update Course by courseId
     * @param courseId
     * @return
     **/
    @PutMapping(value = "/{courseId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Course> updateCourse(@PathVariable("courseId") int courseId,
                                     @RequestBody @Validated Course course) throws Exception {
        return new ResponseEntity<>(courseService.updateCourse(courseId, course), HttpStatus.OK);
    }

    /**
     * Method to delete Course by courseId
     * @param courseId
     * @return
     **/
    @DeleteMapping(value = "/{courseId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MessageResponse> deleteCourse(@PathVariable int courseId) {
            courseService.deleteCourseRecordById(courseId);
            return new ResponseEntity<>(new MessageResponse(courseId + " - Course deleted successfully"), HttpStatus.OK);
    }

    /**
     * Method to get Course for student
     * @return
     **/
    @GetMapping(value = "/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCoursesForStudent(@PathVariable int courseId) throws Exception {
            return new ResponseEntity<>(courseService.getCoursesByCourseId(courseId), HttpStatus.OK);
    }

    /**
     * Method to  get All Course
     * @return
     **/
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourse() {
        List<Course> response = courseService.getAllCourseRecords();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
}
