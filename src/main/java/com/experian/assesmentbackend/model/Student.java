package com.experian.assesmentbackend.model;

import java.util.Date;
import java.util.Vector;

import com.experian.assesmentbackend.dao.StudentDAO;

public class Student {
    
    int studentNumber;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String cellNumber;
    String emailAddress;

    Vector<Integer> scoreList;

    public Student() {

        this.studentNumber = -1;
        this.firstName = "";
        this.lastName = "";
        this.dateOfBirth = new Date(0);
        this.cellNumber = "";
        this.emailAddress = "";

        scoreList = new Vector<Integer>();
    }

    public Student( int studentNumber, 
                    String firstName, 
                    String lastName, 
                    Date dateOfBirth, 
                    String cellNumber,
                    String emailAddress) {

        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cellNumber = cellNumber;
        this.emailAddress = emailAddress;

        scoreList = new Vector<Integer>();
    }

    public Student(StudentDAO sourceObject) {

        this();
        
        if(sourceObject != null) {

            this.studentNumber = sourceObject.getStudentNumber();
            this.firstName = sourceObject.getFirstName();
            this.lastName = sourceObject.getLastName();
            this.dateOfBirth = sourceObject.getDateOfBirth();
            this.cellNumber = sourceObject.getCellNumber();
            this.emailAddress = sourceObject.getEmailAddress();
        }
    }

    public Student(StudentDTO sourceObject) {

        this();
        
        if(sourceObject != null) {

            this.studentNumber = sourceObject.getStudentNumber();
            this.firstName = sourceObject.getFirstName();
            this.lastName = sourceObject.getLastName();
            this.dateOfBirth = sourceObject.getDateOfBirth();
            this.cellNumber = sourceObject.getCellNumber();
            this.emailAddress = sourceObject.getEmailAddress();
        }
    }

    public int getStudentNumber() {

        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {

        this.studentNumber = studentNumber;
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

    public Date getDateOfBirth() {

        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {

        this.dateOfBirth = dateOfBirth;
    }

    public String getCellNumber() {

        return cellNumber;
    }

    public void setCellNumber(String cellNumber) {

        this.cellNumber = cellNumber;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {

        this.emailAddress = emailAddress;
    }

    public Vector<Integer> getScoreList() {

        return scoreList;
    }

    public void setScoreList(Vector<Integer> scoreList) {

        this.scoreList = scoreList;
    }
    
    public int getCurrentScore() {

        if(scoreList != null && scoreList.size() > 0)
        {
            return scoreList.get(scoreList.size() - 1);
        }
        return 0;
    }

    public double getAverageScore() {

        double returnValue = 0.0;
        if(scoreList.size() > 0)
        {
            int iTotal = 0;
            for (Integer integer : scoreList) {
                
                iTotal += integer;
            }

            returnValue = ((double)iTotal) / ((double)scoreList.size());
        }
        return returnValue;
    }

    public void addScore(int newScore) {

        if(scoreList == null) {

            scoreList = new Vector<Integer>();
        }

        scoreList.add(newScore);
    }
}
