package com.msc.restdemo.rest;

import com.msc.restdemo.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
