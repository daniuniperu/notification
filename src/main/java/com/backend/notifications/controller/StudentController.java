package com.backend.notifications.controller;

import com.backend.notifications.model.Student;
import com.backend.notifications.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class StudentController {

    private Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // POST - Create a new student
    @PostMapping(path = "/add")
    public @ResponseBody String addStudent(@RequestBody Student student,
                                           HttpServletResponse response) throws JsonProcessingException, JSONException {
        logger.info("POST /api/add - adding student: {}", student.getName());
        return studentService.save(student, response);
    }

    // GET - Retrieve all students
    @GetMapping(path = "/findAllStudents", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getAllStudents(HttpServletResponse response) {
        logger.info("GET /api/findAllStudents - fetching all students");
        return studentService.getAllStudents();
    }

    // PUT - Update an existing student by ID
    @PutMapping(path = "/students/{id}")
    public String updateStudent(@PathVariable("id") ObjectId id,
                                @RequestBody Student student,
                                HttpServletResponse response) throws JsonProcessingException, JSONException {
        logger.info("PUT /api/students/{} - updating student", id);
        return studentService.updateStudent(id, student, response);
    }

    // DELETE - Delete a student by ID
    @DeleteMapping(path = "/delete/{id}")
    public String deleteStudent(@PathVariable ObjectId id,
                                HttpServletResponse response) throws JSONException {
        logger.info("DELETE /api/delete/{} - deleting student", id);
        return studentService.deleteById(id, response);
    }
}
