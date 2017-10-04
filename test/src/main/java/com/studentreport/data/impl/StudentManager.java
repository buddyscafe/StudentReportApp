package com.studentreport.data.impl;

import com.studentreport.data.PersistenceManager;
import com.studentreport.data.StudentRepository;
import com.studentreport.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentManager implements PersistenceManager<Student> {

    @Autowired
    public StudentRepository studentRepository;

    @Override
    public List<Student> getRecords() {
        return studentRepository.findAll();
    }

    @Override
    public void createRecord(Student student) {
        studentRepository.save(student);
    }

    @Override
    public void removeAll() {
        studentRepository.deleteAll();
    }
}
