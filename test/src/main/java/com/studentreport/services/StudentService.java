package com.studentreport.services;

import com.studentreport.data.StudentFileRepo;
import com.studentreport.data.impl.StudentManager;
import com.studentreport.model.Student;
import com.studentreport.model.StudentReport;
import com.studentreport.processor.ResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@EnableMongoRepositories(basePackages="com.studentreport.data")
@Service
public class StudentService {

    private static final int POOL_SIZE_FOR_RECORD_PROCESSOR = 5;

    @Autowired
    private StudentManager studentManager;

    public void removeAll() throws Exception{
        studentManager.removeAll();
    }


    public void createStudent(Student student) throws  Exception{
        studentManager.createRecord(student);
    }

    public List<Student> getAllStudentRecords() throws Exception{
        return studentManager.getRecords();
    }

    public List<StudentReport> getAllStudentsReport() {
        final StudentFileRepo studentFileRepo = new StudentFileRepo();
        return studentFileRepo.readFromFile("");
    }

    public void createAllStudentsReport(String pathname) {
        List<Student> studentList = studentManager.getRecords();
        if(studentList.isEmpty()) {
            return;
        }
        prepareStudentReport(studentList,pathname);
    }

    private void prepareStudentReport (List<Student> studentList, String pathname) {
        ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE_FOR_RECORD_PROCESSOR);
        StudentFileRepo studentFileRepo = new StudentFileRepo(pathname);
        for (Student student : studentList) {
            executorService.execute( new ResultProcessor(student,studentFileRepo));
        }
        studentFileRepo.writeToFile(pathname);
    }
}
