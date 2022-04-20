package com.experian.assesmentbackend.dao;

import java.util.Date;

import com.experian.assesmentbackend.model.Student;

public class StudentDAO {
    
    public int studentNumber;
    public String FirstName;
    public String LastName;
    public Date dateOfBirth;
    public String cellNumber;
    public String emailAddress;

    public StudentDAO() {

        this.studentNumber = -1;
        FirstName = "";
        LastName = "";
        this.dateOfBirth = new Date(0);
        this.cellNumber = "";
        this.emailAddress = "";
    }

    public StudentDAO(  int studentNumber, 
                        String firstName, 
                        String lastName, 
                        Date dateOfBirth, 
                        String cellNumber,
                        String emailAddress) {

        this.studentNumber = studentNumber;
        FirstName = firstName;
        LastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.cellNumber = cellNumber;
        this.emailAddress = emailAddress;
    }

    public StudentDAO(Student baseObject) {

        this();

        if(baseObject != null)
        {
            this.studentNumber = baseObject.getStudentNumber();
            this.FirstName = baseObject.getFirstName();
            this.LastName = baseObject.getLastName();
            this.dateOfBirth = baseObject.getDateOfBirth();
            this.cellNumber = baseObject.getCellNumber();
            this.emailAddress = baseObject.getEmailAddress();
        }
    }

    public int getStudentNumber() {

        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {

        this.studentNumber = studentNumber;
    }

    public String getFirstName() {

        return FirstName;
    }

    public void setFirstName(String firstName) {

        FirstName = firstName;
    }

    public String getLastName() {

        return LastName;
    }

    public void setLastName(String lastName) {

        LastName = lastName;
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
}
