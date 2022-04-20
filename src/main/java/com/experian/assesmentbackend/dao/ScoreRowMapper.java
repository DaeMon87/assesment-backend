package com.experian.assesmentbackend.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ScoreRowMapper implements RowMapper<ScoreDAO> {

    @Override
    public ScoreDAO mapRow(ResultSet rs, int rowNum) throws SQLException {

        ScoreDAO score = new ScoreDAO();
        score.setId(rs.getInt("scoreid"));
        score.setStudentNumber(rs.getInt("studentnumber"));
        score.setScore(rs.getInt("score"));
        score.setScoreTimestamp(new Date(rs.getTimestamp("scoretimestamp").getTime()));

        return score;
    }
}