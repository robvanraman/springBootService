package com.raman.demo.service;

import com.raman.demo.model.Student;
import com.raman.demo.model.Tutorial;
import com.raman.demo.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        return students;
    }

    public Student findStudentbyId(String id) {
        Student student = studentRepository.findById("Eng2015001").get();
        return student;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }


}


