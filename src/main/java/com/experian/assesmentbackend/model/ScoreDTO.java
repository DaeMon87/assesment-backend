package com.experian.assesmentbackend.model;

public class ScoreDTO {
    
    int studentNumber;
    int score;

    public ScoreDTO() {

        studentNumber = -1;
        score = 0;
    }

    public ScoreDTO(int studentNumber, int score) {

        this.studentNumber = studentNumber;
        this.score = score;
    }

    public int getStudentNumber() {

        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {

        this.studentNumber = studentNumber;
    }

    public int getScore() {

        return score;
    }

    public void setScore(int score) {

        this.score = score;
    }
}
