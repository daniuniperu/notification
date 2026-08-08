package com.backend.notifications.service;

import com.backend.notifications.model.Student;
import com.backend.notifications.repository.StudentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private Student student;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // CREATE - C in CRUD
    public String save(Student student, HttpServletResponse response) throws JsonProcessingException, JSONException {
        JSONObject responseJson = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        if (student.getName() == null || "".equals(student.getName())) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return responseJson.toString();
        }

        this.student = studentRepository.save(student);
        JSONObject studentJSON = new JSONObject(mapper.writeValueAsString(this.student));
        studentJSON.put("id", this.student.getId().toHexString());
        responseJson.put("saveStudentResponse", studentJSON);

        return responseJson.toString();
    }

    // READ - R in CRUD
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // UPDATE - U in CRUD
    @Transactional
    public String updateStudent(ObjectId id, Student student, HttpServletResponse response) throws JsonProcessingException, JSONException {
        JSONObject responseJson = new JSONObject();
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        Student employee1 = studentRepository.findById(id).orElse(null);
        boolean exists = studentRepository.existsById(id);

        if (!exists) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return responseJson.toString();
        }

        if (student.getName() != null) {
            employee1.setName(student.getName());
            employee1.setDob(student.getDob());
            studentRepository.save(employee1);

            JSONObject studentJSON = new JSONObject(mapper.writeValueAsString(employee1));
            studentJSON.put("id", employee1.getId().toHexString());
            responseJson.put("getUpdatedStudentResponse", studentJSON);
        }

        return responseJson.toString();
    }

    // DELETE - D in CRUD
    public String deleteById(ObjectId id, HttpServletResponse response) throws JSONException {
        JSONObject responseJson = new JSONObject();

        boolean exists = studentRepository.existsById(id);

        if (!exists) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } else {
            responseJson.put("deleted student with id ", id);
            studentRepository.deleteById(id);
        }

        return responseJson.toString();
    }
}
