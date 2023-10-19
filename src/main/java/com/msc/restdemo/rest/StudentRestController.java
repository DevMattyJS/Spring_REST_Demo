package com.msc.restdemo.rest;

import com.msc.restdemo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    private List<Student> students;

    @PostConstruct
    private void loadData() {

        students = new ArrayList<>();

        // data hardcoded for now,they will be loaded from DB later
        students.add(new Student("Denka", "Jamka"));
        students.add(new Student("Matko", "Slizik"));
        students.add(new Student("Mary", "Smith"));
    }

    // define endpoint for "/students" - return list of all students
    @GetMapping("/students")
    public List<Student> getStudents() {

        // Jackson Project will automatically convert this list to JSON (behind the scenes)
        return students;
    }

    // define endpoint for "/students/{studentId} - return student at index
    // {studentId} => path variable
    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {

        // check studentId against the list size
        if ((studentId >= students.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }

        // just index into the list to keep it simple for now, will be refactored later
        return students.get(studentId);
    }

    // exception handler for StudentNotFoundException
    // <StudentErrorResponse> - type of the response body
    // StudentNotFoundException - exception type to handle / catch
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (StudentNotFoundException e) {

        // create StudentErrorResponse
        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        // return new ResponseEntity(errorBody, status)
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // exception handler for generic exceptions (catch all)
    @ExceptionHandler
    public ResponseEntity<StudentErrorResponse> handleException (Exception e) {

        StudentErrorResponse error = new StudentErrorResponse();

        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}
