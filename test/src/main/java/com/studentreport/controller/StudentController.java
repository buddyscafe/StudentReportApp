package com.studentreport.controller;

import com.studentreport.model.Student;
import com.studentreport.model.StudentReport;
import com.studentreport.services.StudentService;
import com.studentreport.util.APIConstants;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @RequestMapping(method = RequestMethod.POST)
    public String uploadStudents(@RequestBody List<Student> studentList){
        System.out.println(studentList);
        if(!studentList.isEmpty()) {
            final StudentService service = new StudentService();
            try {
                service.removeAll();
                System.out.println("DEBUG : "+"Existing students record removed");
                for (Student student : studentList) {
                    service.createStudent(student);
                }
                return APIConstants.UPLOAD_SUCCESSFUL;
            } catch (Exception e) {
                System.out.println("ERROR : "+"Student records insertion failed");
                e.printStackTrace();
                return APIConstants.UPLOAD_FAIL;
            }
        }
        return APIConstants.UPLOAD_DATA;
    }

    @RequestMapping(method = RequestMethod.GET, value="")
    public List<Student> getAllStudents(){
        final StudentService service = new StudentService();
        try {
            return service.getAllStudentRecords();
        } catch (Exception e) {
            System.out.println("ERROR : "+"Unable to fetch Student records ");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value="/report/")
    public List<StudentReport> getAllStudentsReport(){
        final StudentService service = new StudentService();
        try {
            return service.getAllStudentsReport();
        } catch (Exception e) {
            System.out.println("ERROR : "+"Unable to fetch Students report ");
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value="/report/")
    public String createStudentsReport(@RequestBody String pathname){
        final StudentService service = new StudentService();
        try {
            service.createAllStudentsReport(pathname);
            return APIConstants.CREATE_SUCCESSFUL;
        } catch (Exception e) {
            System.out.println("ERROR : "+"Unable to create all students report ");
            e.printStackTrace();
            return APIConstants.CREATE_FAIL;
        }
    }
}
