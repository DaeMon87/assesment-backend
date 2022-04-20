package com.experian.assesmentbackend.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StudentRowMapper implements RowMapper<StudentDAO> {

    @Override
    public StudentDAO mapRow(ResultSet rs, int rowNum) throws SQLException {

        StudentDAO student = new StudentDAO();
        student.setStudentNumber(rs.getInt("studentnumber"));
        student.setFirstName(rs.getString("firstname"));
        student.setLastName(rs.getString("lastname"));
        student.setDateOfBirth(new Date(rs.getTimestamp("dateofbirth").getTime()));
        student.setCellNumber(rs.getString("cellnumber"));
        student.setEmailAddress(rs.getString("emailaddress"));

        return student;
    }
}