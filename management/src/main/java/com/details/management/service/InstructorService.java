package com.details.management.service;


import com.details.management.exception.DataResourceNotFoundException;
import com.details.management.model.Instructor;
import com.details.management.model.Student;
import com.details.management.dao.InstructorRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InstructorService {
    @Autowired
    InstructorRepository instructorRepository;

    public InstructorService(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Transactional
    public Instructor addInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    @Transactional
    public Instructor updateInstructor(int instructorId, Instructor instructor) {
        if(instructor.getInstructorID() == 0) {
            instructor.setInstructorID(instructorId);
        }
        return instructorRepository.save(instructor);
    }

    public Instructor getInstructorByInstructorId(int instructorId) throws DataResourceNotFoundException {
        return instructorRepository.findById(instructorId).orElseThrow(() -> new DataResourceNotFoundException("Instructor not found"));
    }

    @Transactional
    public void deleteInstructorRecordById(int instructorId) {
        instructorRepository.deleteById(instructorId);
    }

    public List<Instructor> getAllInstructor() {
        return (List<Instructor>) instructorRepository.findAll();
    }

    public List<Student> getStudentsForInstructor(int instructorId) {
        return null;
    }
}