package com.experian.assesmentbackend.dao;

import java.util.Date;

public class ScoreDAO {
    
    int id;
    int studentNumber;
    int score;
    Date scoreTimestamp;

    public ScoreDAO() {

        this.studentNumber = -1;
        this.score = 0;
        this.scoreTimestamp = new Date(0);
    }

    public ScoreDAO(int studentNumber, int score, Date scoreTimestamp) {
        this.studentNumber = studentNumber;
        this.score = score;
        this.scoreTimestamp = scoreTimestamp;
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

    public Date getScoreTimestamp() {
        return scoreTimestamp;
    }

    public void setScoreTimestamp(Date scoreTimestamp) {
        this.scoreTimestamp = scoreTimestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
