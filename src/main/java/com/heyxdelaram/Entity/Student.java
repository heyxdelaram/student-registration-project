package com.heyxdelaram.Entity;

import java.time.LocalDate;
import java.util.Random;

public class Student {
    private String firstName;
    private String lastName;
    private float avgScore;
    private int studentNum;
    private String address;
    private String nCode;
    private boolean isDeleted;
    private Integer year;

    public int getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getnCode() {
        return nCode;
    }

    public void setnCode(String nCode) {
        this.nCode = nCode;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Student{" +
                " firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avgScore=" + avgScore +
                ", studentNum=" + studentNum +
                ", address='" + address + '\'' +
                ", nCode='" + nCode + '\'' +
                ", isDeleted=" + isDeleted +
                ", year='" + year + '\'' +
                '}';
    }
}
