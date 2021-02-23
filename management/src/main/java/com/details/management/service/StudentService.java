package com.details.management.service;

import com.details.management.dao.CourseRepository;
import com.details.management.exception.DataResourceNotFoundException;
import com.details.management.model.Course;
import com.details.management.model.Student;
import com.details.management.dao.StudentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseService courseService;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Transactional
    public Student updateStudent(int studentId, Student student) {
        if(student.getStudentID() == 0) {
            student.setStudentID(studentId);
        }
        return studentRepository.save(student);
    }

    public Student getStudentById(int studentId) throws DataResourceNotFoundException {
        return studentRepository.findById(studentId).orElseThrow(() -> new DataResourceNotFoundException("Student not found"));
    }

    @Transactional
    public void deleteStudentById(int studentId) {
        studentRepository.deleteById(studentId);
    }

    public List<Student> getAllStudents() {
        return (List<Student>) studentRepository.findAll();
    }

    public List<Student> getAllStudentsByInstructorId(int instructorId) {
        return new ArrayList<>(studentRepository.findByCourses_Instructor_InstructorID(instructorId));
    }

    public List<Course> getCourseForStudent(int studentId) throws DataResourceNotFoundException {
            return new ArrayList<>(getStudentById(studentId).getCourses());
    }

    public long getTotalCourseDurationForStudent(int studentId) throws DataResourceNotFoundException {
        return  getStudentById(studentId).getCourses().stream().mapToLong(Course::getDuration).sum();
    }

    public Student addCourse(int studentId, int courseId) throws DataResourceNotFoundException {
        Student student = getStudentById(studentId);
        Course course = courseService.getCoursesByCourseId(courseId);

        student.getCourses().add(course);

        return studentRepository.save(student);
    }

    public Student removeCourse(int studentId, int courseId) throws DataResourceNotFoundException {
        Student student = getStudentById(studentId);
        Course course = courseService.getCoursesByCourseId(courseId);

        student.getCourses().remove(course);

        return studentRepository.save(student);
    }
}