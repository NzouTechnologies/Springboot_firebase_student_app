package com.example.demo3;

import com.example.demo3.controllers.StudentController;
import com.example.demo3.entities.Student;
import com.example.demo3.services.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StudentControllerTest {
    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;
    
    //Get all test
    @Test
    public void getAllStudents_shouldReturnListOfStudents() throws ExecutionException, InterruptedException{
        List<Student> students = new ArrayList<>();

        // Create mock student objects
        Student student1 = mock(Student.class);
        when(student1.getStudentName()).thenReturn("Hun");
        when(student1.getId()).thenReturn("1");
        when(student1.getAge()).thenReturn(12);
        when(student1.getStudentEmail()).thenReturn("hun@gmail.com");
        students.add(student1);

        Student student2 = mock(Student.class);
        when(student2.getStudentName()).thenReturn("Julio");
        when(student2.getId()).thenReturn("2");
        when(student2.getAge()).thenReturn(30);
        when(student2.getStudentEmail()).thenReturn("julio@gmail.com");
        students.add(student2);

        when(studentService.getAllStudents()).thenReturn(students);

        List<Student> result = studentController.getAllStudents().getBody();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hun", result.get(0).getStudentName());
        assertEquals("Julio", result.get(1).getStudentName());

        verify(studentService, times(1)).getAllStudents();
    }
    //Get student by ID
    @Test
    public void getStudentById_shouldReturnStudent() throws ExecutionException, InterruptedException {
        String studentId = "1";
        // Create mock student objects
        Student student1 = mock(Student.class);
        when(student1.getStudentName()).thenReturn("Hun");
        when(student1.getId()).thenReturn("1");
        when(student1.getAge()).thenReturn(12);
        when(student1.getStudentEmail()).thenReturn("hun@gmail.com");


        when(studentService.getStudentById(studentId)).thenReturn(student1);

        ResponseEntity<Student> response = studentController.getStudentById(studentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(student1, response.getBody());

        verify(studentService, times(1)).getStudentById(studentId);
    }
    //Search by name
    @Test
    public void searchByName_shouldReturnListOfStudents() throws ExecutionException, InterruptedException {
        String name = "John";
        List<Student> students = new ArrayList<>();

        // Create mock student objects
        Student student1 = mock(Student.class);
        when(student1.getStudentName()).thenReturn("Hun");
        when(student1.getId()).thenReturn("1");
        when(student1.getAge()).thenReturn(12);
        when(student1.getStudentEmail()).thenReturn("hun@gmail.com");
        students.add(student1);

        Student student2 = mock(Student.class);
        when(student2.getStudentName()).thenReturn("Julio");
        when(student2.getId()).thenReturn("2");
        when(student2.getAge()).thenReturn(30);
        when(student2.getStudentEmail()).thenReturn("julio@gmail.com");
        students.add(student2);

        when(studentService.searchStudentByName(name)).thenReturn(students);

        List<Student> result = studentController.searchStudentByName(name).getBody();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Hun", result.get(0).getStudentName());
        assertEquals("Julio", result.get(1).getStudentName());

        verify(studentService, times(1)).searchStudentByName(name);
    }
    //Update students data
    @Test
    public void updateStudent_shouldReturnUpdatedStudent() throws ExecutionException, InterruptedException {
        String studentId = "1";
        // Create mock student objects
        Student student1 = mock(Student.class);
        when(student1.getStudentName()).thenReturn("Hun");
        when(student1.getId()).thenReturn("1");
        when(student1.getAge()).thenReturn(12);
        when(student1.getStudentEmail()).thenReturn("hun@gmail.com");

        Student updatedStudent = mock(Student.class);
        // Set the behavior of the updateStudent() method
        when(studentService.updateStudent(eq(studentId), any(Student.class))).thenReturn(updatedStudent);

        ResponseEntity<Student> response = studentController.updateStudent(studentId, student1);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedStudent, response.getBody());

        verify(studentService, times(1)).updateStudent(eq(studentId), any(Student.class));
    }
    //Create a new student test
    @Test
    public void addStudent_shouldReturnCreatedStudent() throws ExecutionException, InterruptedException{
        Student student2 = mock(Student.class);
        when(student2.getStudentName()).thenReturn("Julio");
        when(student2.getId()).thenReturn("2");
        when(student2.getAge()).thenReturn(30);
        when(student2.getStudentEmail()).thenReturn("julio@gmail.com");

        // Mock the void method behavior using doNothing
        doNothing().when(studentService).addStudent(any(Student.class));

        // Verify the mock behavior for the void method
        doAnswer(invocation -> invocation.getArgument(0)).when(studentService).addStudent(any(Student.class));


        ResponseEntity<String> response = studentController.addStudent(student2);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Student added successfully", "Student added successfully");

        verify(studentService, times(1)).addStudent(any(Student.class));
    }
    //delete a student test
    @Test
    public void deleteStudent_shouldReturnNoContent() throws ExecutionException, InterruptedException {
        String studentId = "1";

        ResponseEntity<String> response = studentController.deleteStudent(studentId);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(studentService, times(1)).deleteStudent(studentId);
    }

}
