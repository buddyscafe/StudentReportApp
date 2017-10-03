package com.studentreport.controller;

import com.studentreport.data.StudentFileRepo;
import com.studentreport.model.Student;
import com.studentreport.model.StudentReport;
import com.studentreport.processor.ResultProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.studentreport.data.StudentRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/students")
public class StudentController {

    private static final int POOL_SIZE_FOR_RECORD_PROCESSOR = 5;

    @Autowired
    StudentRepository studentRepository;

    @RequestMapping(method = RequestMethod.POST)
    public List<Student> create(@RequestBody List<Student> studentList){
        System.out.println(studentList);

        if(!studentList.isEmpty()) {
            studentRepository.deleteAll();
            for (Student student : studentList) {
                studentRepository.save(student);
            }
        }
        return studentList;
    }

    @RequestMapping(method = RequestMethod.GET, value="")
    public List<Student> getAll(){
        return studentRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.GET, value="/report/")
    public List<StudentReport> getAllStudentsReport(){
        StudentFileRepo studentFileRepo = new StudentFileRepo();
        return studentFileRepo.readFromFile("");
    }

    @RequestMapping(method = RequestMethod.POST, value="/report/")
    public void createStudentsReport(@RequestBody String pathname){
        List<Student> studentList = studentRepository.findAll();
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
