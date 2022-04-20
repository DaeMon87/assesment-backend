package com.experian.assesmentbackend.model;

import java.util.Date;

public class StudentDTO {
    
    int studentNumber;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String cellNumber;
    String emailAddress;
    int currentScore;
    double averageScore;

    public StudentDTO() {

        this.studentNumber = -1;
        firstName = "";
        lastName = "";
        this.dateOfBirth = new Date(0);
        this.cellNumber = "";
        this.emailAddress = "";
        this.currentScore = 0;
        this.averageScore = 0.0;
    }

    public StudentDTO(  int studentNumber, 
                        String firstName, 
                        String lastName, 
                        Date dateOfBirth, 
                        String cellNumber,
                        String emailAddress, 
                        int currentScore, 
                        double averageScore) {

        this.studentNumber = studentNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cellNumber = cellNumber;
        this.emailAddress = emailAddress;
        this.currentScore = currentScore;
        this.averageScore = averageScore;
    }

    public StudentDTO(Student baseObject) {

        this();

        if(baseObject != null)
        {
            this.studentNumber = baseObject.getStudentNumber();
            this.firstName = baseObject.getFirstName();
            this.lastName = baseObject.getLastName();
            this.dateOfBirth = baseObject.getDateOfBirth();
            this.cellNumber = baseObject.getCellNumber();
            this.emailAddress = baseObject.getEmailAddress();
            this.currentScore = baseObject.getCurrentScore();
            this.averageScore = baseObject.getAverageScore();
        }
    }

    public int getStudentNumber() {

        return studentNumber;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public Date getDateOfBirth() {

        return dateOfBirth;
    }

    public String getCellNumber() {

        return cellNumber;
    }

    public String getEmailAddress() {

        return emailAddress;
    }

    public int getCurrentScore() {

        return currentScore;
    }

    public double getAverageScore() {

        return averageScore;
    }
}
