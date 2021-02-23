package com.details.management.controller;

import com.details.management.dto.MessageResponse;
import com.details.management.model.Instructor;
import com.details.management.model.Student;
import com.details.management.service.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/academy/instructors")

public class InstructorController {
    InstructorService instructorService;

    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }


    /**
     * Method to add Instructor
     * @return
     **/
    @PostMapping(value = "", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Instructor> addInstructor(@RequestBody Instructor instructor) {
        return new ResponseEntity<>(instructorService.addInstructor(instructor), HttpStatus.OK);
    }


    /**
     * Method to update Instructor
     * @param instructorId
     * @return
     **/
    @PutMapping(value = "/{instructorId}", produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Instructor> updateInstructor(@PathVariable("instructorId") int instructorId,
                                             @RequestBody @Validated Instructor instructor) {
        return new ResponseEntity<>(instructorService.updateInstructor(instructorId, instructor), HttpStatus.OK);
    }

    /**
     * Method to Delete/Remove Instructor by Id
     * @param instructorId
     * @return
     **/
    @DeleteMapping(value = "/{instructorId}")
    public ResponseEntity<MessageResponse> deleteInstructorById(@PathVariable int instructorId) {
            instructorService.deleteInstructorRecordById(instructorId);
            return new ResponseEntity<>(new MessageResponse("Deleted instructor successfully"), HttpStatus.OK);
    }

    /**
     * Method to get Instructor by id
     * @param instructorId
     * @return
     **/
    @GetMapping(value = "/{instructorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Instructor> getInstructorById(@PathVariable int instructorId) throws Exception {
        return new ResponseEntity<>(instructorService.getInstructorByInstructorId(instructorId), HttpStatus.OK);
    }

    /**
     * Method to get All Instructor Records
     * @return
     **/
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Instructor>> getAllInstructors() {
        return new ResponseEntity<>(instructorService.getAllInstructor(), HttpStatus.OK);
    }


    /**
     * Method to get number student list using InstructorId
     * @param instructorId
     * @return
     **/
    @GetMapping(value = "/{instructorId}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudentsByInstructorId(@PathVariable("instructorId") int instructorId) {
        return new ResponseEntity<>(instructorService.getStudentsForInstructor(instructorId), HttpStatus.OK);
    }
}