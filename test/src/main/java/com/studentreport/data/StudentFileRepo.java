package com.studentreport.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studentreport.model.StudentReport;
import com.studentreport.model.StudentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

public class StudentFileRepo {

    //Shared queue to store Student records after processing
    private ConcurrentLinkedQueue<StudentReport> queue = new ConcurrentLinkedQueue<StudentReport>();

    //Default pathname for storing StudentReport
    private static final String DEFAULT_PATH_NAME = "D:"+ File.separator+"StudentReports.json";

    //Sleep time in millis for each iteration when queue is empty
    private static final int EMPTY_QUEUE_SLEEP_TIME = 1000;

    //Consecutive retry counter to check if the Queue is Empty or not
    private static final int RETRY_COUNTER_TO_CHECK_EMPTY_QUEUE = 3;

    public StudentFileRepo() {
    }

    public StudentFileRepo(String pathname) {
//        writeToFile(pathname);
    }

    public void addToQueue(StudentReport studentReport) {
        queue.add(studentReport);
    }

    public ConcurrentLinkedQueue<StudentReport> getStudentReportsQueue() {
        return queue;
    }

    public void writeToFile(String pathname) {
        try {
            File file = createFile(pathname);
            if (file.exists()){
                System.out.println("File Exists");
                file.delete();
            }
            file.createNewFile();
            System.out.println("File is created!");
            final FileOutputStream out = new FileOutputStream(file, true);
            final ObjectMapper mapper = new ObjectMapper();
            out.write("[".getBytes("UTF-8"));
            Thread writerThread = new Thread(() -> {
                System.out.println("Thread called");
                int emptyQueueCounter = 0;
                    while(emptyQueueCounter < RETRY_COUNTER_TO_CHECK_EMPTY_QUEUE) {
                            if(queue.isEmpty()) {
                                System.out.println("Empty Queue");
                                try {
                                    emptyQueueCounter++;
                                    Thread.sleep(EMPTY_QUEUE_SLEEP_TIME);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                continue;
                            }
                            try {
                                System.out.println("Empty Queue");
                                //Object to JSON in file
                                out.write(mapper.writeValueAsString(queue.poll()).getBytes("UTF-8"));
                                out.write(",".getBytes("UTF-8"));
                                emptyQueueCounter = 0;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    }
            });
            writerThread.start();
            try {
                writerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            out.write("{}]".getBytes("UTF-8"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File createFile(String pathname) {
        pathname = (pathname == null || pathname.isEmpty() ? DEFAULT_PATH_NAME : pathname);
        System.out.println("pathname : " + pathname);
        File file = new File(pathname);
        return file;
    }

    public List<StudentReport> readFromFile(String pathname) {
        try {
            File file = createFile(pathname);
            if(!file.exists()) {
                return Collections.emptyList();
            }
            final ObjectMapper mapper = new ObjectMapper();
            //JSON from file to Object
            List<StudentReport> studentsReport = mapper.readValue(file, new TypeReference<List<StudentReport>>(){});
            studentsReport = studentsReport.stream()
                    .filter(studentReport -> {
                        if(studentReport == null || studentReport.getResult() == null
                                || studentReport.getResult().equals(StudentResult.FAIL))  {
                            return false;
                        }
                        return true;
                    })
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            int rank = 0;
            for(StudentReport studentReport : studentsReport ){
                studentReport.setRank(++rank);
            }
//          TreeSet studentReportSet = new TreeSet(studentsReport);
            return studentsReport;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}