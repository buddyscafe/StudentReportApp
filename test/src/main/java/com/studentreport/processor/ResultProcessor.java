package com.studentreport.processor;

import com.studentreport.data.StudentFileRepo;
import com.studentreport.model.Student;
import com.studentreport.model.StudentReport;
import com.studentreport.model.StudentResult;
import com.studentreport.model.Subject;

public class ResultProcessor implements Runnable {

    private Student student;
    private StudentFileRepo studentFileRepo;

    public ResultProcessor (Student student, StudentFileRepo studentFileRepo) {
        this.student = student;
        this.studentFileRepo = studentFileRepo;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        processResult();
    }

    private void processResult() {
        int totalMarks = 0;
        StudentReport studentReport = new StudentReport(student,StudentResult.PASS,0,0);
        if(!student.getSubjects().isEmpty()) {
            for (Subject subject : student.getSubjects()) {
                if(subject.getMarks() < subject.getMinMarks()) {
                    studentReport.setResult(StudentResult.FAIL);
                }
                totalMarks = totalMarks + subject.getMarks();
            }
            studentReport.setTotalMarks(totalMarks);
        }
        studentFileRepo.addToQueue(studentReport);
    }
}