package com.example.demo3.controllers;

import com.example.demo3.entities.Student;
import com.example.demo3.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }
    //Create new student
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addStudent(@RequestBody Student student)
            throws ExecutionException, InterruptedException {
//        studentService.addStudent(student);
//        return new ResponseEntity<>("Student added successfully", HttpStatus.CREATED);
        try {
            studentService.addStudent(student);
            return ResponseEntity.status(HttpStatus.CREATED).body("Student added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //List all students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() throws ExecutionException,InterruptedException{
        try {
            List<Student> students = studentService.getAllStudents();
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable String id)
            throws ExecutionException, InterruptedException{
//        Student student = studentService.getStudentById(id);
//        return new ResponseEntity<>(student, HttpStatus.OK);
        try {
            Student student = studentService.getStudentById(id);
            if (student != null) {
                return ResponseEntity.ok(student);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Update using ID
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<Student> updateStudent(@PathVariable String id, @RequestBody Student student)
            throws ExecutionException, InterruptedException{
//        studentService.updateStudent(id, student);
//        return new ResponseEntity<>("Student updated successfully", HttpStatus.OK);
        try {
            Student updatedStudent = studentService.updateStudent(id, student);
            if (updatedStudent != null) {
                return ResponseEntity.ok(updatedStudent);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //Delete using ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(@PathVariable String id)
            throws ExecutionException, InterruptedException{
        studentService.deleteStudent(id);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.OK);

    }
    //Search by name
    @GetMapping("/search")
    public ResponseEntity<List<Student>> searchStudentByName(@RequestParam String name)
            throws ExecutionException, InterruptedException{
//        List<Student> students = studentService.searchStudentByName(name);
//        return new ResponseEntity<>(students, HttpStatus.OK);
        try {
            List<Student> students = studentService.searchStudentByName(name);
            return ResponseEntity.ok(students);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
