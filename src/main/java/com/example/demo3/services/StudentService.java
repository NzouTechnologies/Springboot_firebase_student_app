package com.example.demo3.services;

import com.example.demo3.entities.Student;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class StudentService {
    //Define the table name
    private static final String COLLECTION_NAME = "studentTable";


    //Create a new student
    public void addStudent(Student student) throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentRef = firestore.collection(COLLECTION_NAME).document();
        student.setId(documentRef.getId());
        documentRef.set(student).get();
    }
    //Get all students
    public List<Student> getAllStudents() throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        Query query = collectionReference.orderBy("studentName");

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Student> students = new ArrayList<>();
        for(QueryDocumentSnapshot document : documents){
            students.add(document.toObject(Student.class));
        }
        return students;
    }
    //Get by ID
    public Student getStudentById(String id) throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);

        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();
        DocumentSnapshot document = documentSnapshot.get();

        if(document.exists()){
            return document.toObject(Student.class);
        }else {
            throw new IllegalArgumentException("Student not found with ID: " + id);
        }
    }
    //Update student by ID
    public Student updateStudent(String id, Student student) throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);

        student.setId(id);
        documentReference.set(student).get();
        return student;
    }
    //Delete student by ID
    public void deleteStudent(String id) throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = firestore.collection(COLLECTION_NAME).document(id);

        documentReference.delete().get();
    }
    //Search by Name
    public List<Student> searchStudentByName(String name) throws ExecutionException, InterruptedException{
        Firestore firestore = FirestoreClient.getFirestore();
        CollectionReference collectionReference = firestore.collection(COLLECTION_NAME);
        Query query = collectionReference.whereEqualTo("studentName", name);

        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();

        List<Student> students = new ArrayList<>();
        for(QueryDocumentSnapshot document : documents){
            students.add(document.toObject(Student.class));
        }
        return students;
    }
}
