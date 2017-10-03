package com.studentreport.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Subject {

    private String name;
    private int marks;
    private int minMarks;
    private int maxMarks;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getMarks() {
        return marks;
    }
    public void setMarks(int marks) {
        this.marks = marks;
    }
    public int getMinMarks() {
        return minMarks;
    }
    public void setMinMarks(int minMarks) {
        this.minMarks = minMarks;
    }
    public int getMaxMarks() {
        return maxMarks;
    }
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", marks=" + marks +
                ", minMarks=" + minMarks +
                ", maxMarks=" + maxMarks +
                '}';
    }
}
