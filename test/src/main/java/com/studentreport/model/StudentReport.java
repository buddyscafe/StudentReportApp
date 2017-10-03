package com.studentreport.model;

import java.util.HashMap;
import java.util.Map;

public class StudentReport implements Comparable<StudentReport> {

    private static final int POSITIVE_INTEGER = 1;
    private static final int NEGATIVE_INTEGER = -1;
    private static final int ZERO = 0;

    private String id;
    private String name;
    private String className;
    private String result;
    private Map<String, Integer> subjectMarks;
    private int totalMarks;
    private int rank;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Map<String, Integer> getSubjectMarks() {
        return subjectMarks;
    }

    public void setSubjectMarks(Map<String, Integer> subjectMarks) {
        this.subjectMarks = subjectMarks;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    private StudentReport(){
    }

    public StudentReport(Student student, String result, int totalMarks, int rank) {
        this.id = student.getId();
        this.name = student.getName();
        this.className = student.getClassName();
        this.subjectMarks = new HashMap<>();
        if(!student.getSubjects().isEmpty()) {
            for (Subject subject : student.getSubjects()) {
                this.subjectMarks.put(subject.getName(), subject.getMarks());
            }
        }
        this.totalMarks = totalMarks;
        this.result = result;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "StudentReport{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", subjectMarks=" + subjectMarks +
                ", totalMarks='" + totalMarks + '\'' +
                ", result=" + result +
                ", rank=" + rank +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentReport that = (StudentReport) o;

        if (totalMarks != that.totalMarks) return false;
        if (rank != that.rank) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (result != null ? !result.equals(that.result) : that.result != null) return false;
        return subjectMarks != null ? subjectMarks.equals(that.subjectMarks) : that.subjectMarks == null;
    }

    @Override
    public int hashCode() {
        int result1 = id != null ? id.hashCode() : 0;
        result1 = 31 * result1 + (name != null ? name.hashCode() : 0);
        result1 = 31 * result1 + (className != null ? className.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (subjectMarks != null ? subjectMarks.hashCode() : 0);
        result1 = 31 * result1 + totalMarks;
        result1 = 31 * result1 + rank;
        return result1;
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(StudentReport that) {
        int result = ZERO;
        if(that != null) {
            if(this.totalMarks < that.getTotalMarks()) {
                result = NEGATIVE_INTEGER;
            } else if(this.totalMarks > that.getTotalMarks()) {
                result = POSITIVE_INTEGER;
            }
        }
        return result;
    }
}
