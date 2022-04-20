package com.experian.assesmentbackend.repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import com.experian.assesmentbackend.dao.ScoreDAO;
import com.experian.assesmentbackend.dao.ScoreRowMapper;
import com.experian.assesmentbackend.dao.StudentDAO;
import com.experian.assesmentbackend.dao.StudentRowMapper;
import com.experian.assesmentbackend.model.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component("Repository")
public class Repository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentRowMapper studentRowMapper;
    private ScoreRowMapper scoreRowMapper;

    Logger logger = LoggerFactory.getLogger(Repository.class);

    public Repository() {

        studentRowMapper = new StudentRowMapper();
        scoreRowMapper = new ScoreRowMapper();
    }

    public List<StudentDAO> getStudentList(String key, String searchparam) {
        
        String sql = "SELECT * FROM assesmentbackend.student ";
        List<StudentDAO> studentList;

        if(key != null && !key.isEmpty() && searchparam != null && !searchparam.isEmpty()) { 

            sql += key + " = ?";

            studentList = jdbcTemplate.query(sql, studentRowMapper, searchparam);

        } else {

            studentList = jdbcTemplate.query(sql, studentRowMapper);
        }

        return studentList;
    }

    public StudentDAO getStudentById(int id) {
        
        if(id <= 0) {
            
            return null;
        }
        
        String sql = "SELECT * FROM assesmentbackend.student WHERE studentnumber = ?";

        StudentDAO student = null;
        try {

            student = jdbcTemplate.queryForObject(sql, studentRowMapper, id);
        }
        catch(Exception ex) {

            logger.error("Failed to find Student Object with id: " + id, ex);
        }
		
        return student;
    }

    public List<ScoreDAO> getStudentScores(int studentid) {

        String sql = "SELECT * FROM assesmentbackend.score WHERE studentnumber = ?";

		List<ScoreDAO> scorelist = jdbcTemplate.query(sql, scoreRowMapper, studentid);

        return scorelist;
    }

    public int addScore(int studentNumber, int newScore) {

        String sqlScore = "INSERT INTO assesmentbackend.score (studentnumber, scoretimestamp, score) VALUES (?, CURRENT_TIMESTAMP, ?)";
        int result = jdbcTemplate.update(sqlScore, studentNumber, newScore);
        return result;
    }

    public StudentDAO addStudent(Student newStudent) throws IllegalArgumentException, IllegalAccessException {

        StudentDAO studentDAO = new StudentDAO(newStudent);

        String sqlStart = "INSERT INTO assesmentbackend.student (";
        String sqlMid = ") VALUES (";
        String sqlEnd = ")";
        String columnList = "";
        String valueList = "";
        ArrayList<Object> values = new ArrayList<Object>();
        ArrayList<Integer> valueTypes = new ArrayList<Integer>();     

        Field[] fields = studentDAO.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {

            if (fields[i].getName().toLowerCase().equals("studentnumber")) {
                
                continue;
            }

            Object value = fields[i].get(studentDAO);
            if (value != null) {

                columnList = addItemToStringList(columnList, fields[i].getName());
                valueList = addItemToStringList(valueList, "?");
                values.add(value);
                valueTypes.add(getSQLType(value));
            }
        }

        if(columnList.length() > 0) {

            String finalQuery = sqlStart + columnList + sqlMid + valueList + sqlEnd;
            
            int[] sqlTypes = new int[valueTypes.size()];
            for (int i = 0; i < valueTypes.size(); i++) {
                sqlTypes[i] = (int)valueTypes.get(i);
            }
            int result = jdbcTemplate.update(finalQuery, values.toArray(), sqlTypes);

            if(result > 0) {

                List<StudentDAO> nameList = findStudentByName(studentDAO.getFirstName(), studentDAO.getLastName());
                if(!nameList.isEmpty()) {

                    return nameList.get(0);
                }
            }
        }

        return null;
    }

    protected String addItemToStringList(String sourceString, String newValue) {

        String returnValue = sourceString;
        if(sourceString != null && sourceString.length() > 0) {
            
            returnValue += ", ";
        }
        returnValue += newValue;

        return returnValue;
    }

    protected int getSQLType(Object objCheck) {

        if(objCheck instanceof String) {

            return java.sql.Types.VARCHAR;

        } else if(objCheck instanceof Integer) {

            return java.sql.Types.INTEGER;

        }else if(objCheck instanceof Date || objCheck instanceof java.sql.Date) {

            return java.sql.Types.DATE;
        }

        return java.sql.Types.VARCHAR;
    }

    public int[] getAllSqlTypes(ArrayList<Object> values) {

        int listsize = values.size();
        int[] returnVal = new int[listsize];
        for (int i = 0; i < listsize; i++) {

            returnVal[i] = getSQLType(values.get(i));
        }

        return returnVal;
    }

    public List<StudentDAO> findStudentByName(String firstname, String lastname) {

        if (firstname == null && lastname == null) {
            return null;
        }

        String sql = "SELECT * FROM assesmentbackend.student WHERE ";
        String sqlCondition = "";
        List<StudentDAO> studentList;

        if (firstname != null && !firstname.isEmpty()) { 

            sqlCondition += "firstname = '" + firstname + "'";
        } 

        if (lastname != null && !lastname.isEmpty()) {

            if(!sqlCondition.isEmpty()) {

                sqlCondition += " AND ";
            }
            sqlCondition += "lastname = '" + lastname + "'";
        }

        studentList = jdbcTemplate.query(sql + " " + sqlCondition, studentRowMapper);

        return studentList;
    }

    public StudentDAO updateStudent(int studentnumber, HashMap<String, Object> updateMap) {

        if(updateMap.size() > 0) {

            String sqlUpdate = "UPDATE assesmentbackend.student SET ";
            String sqlCondition = " WHERE studentnumber = ?";
            String sqlValues = "";
            ArrayList<Object> values = new ArrayList<Object>();
            for (String updateKey : updateMap.keySet()) {
                
                if (!sqlValues.isEmpty()) {

                    sqlValues += " , ";
                }

                sqlValues += " " + updateKey + " = ?";
                
                if(updateMap.get(updateKey) instanceof java.util.Date) {

                    Object tmpObject = new java.sql.Date(((java.util.Date)updateMap.get(updateKey)).getTime());
                    values.add(tmpObject);
                } else {

                    values.add(updateMap.get(updateKey));
                }
               
            }

            // Add studentnnumber
            values.add(studentnumber);

            String sqlFinal = sqlUpdate + sqlValues + sqlCondition;


            int result = jdbcTemplate.update(sqlFinal, values.toArray(), getAllSqlTypes(values));
            if(result > 0) {

                StudentDAO updatedStudentDAO = getStudentById(studentnumber);
                if(updatedStudentDAO != null) {

                    return updatedStudentDAO;
                }
            }
        }
        return null;
    }

    public int deleteStudent(int id) {

        String sqlDelete = "DELETE FROM assesmentbackend.student WHERE studentnumber = ?";
        
        int result = jdbcTemplate.update(sqlDelete, id);
        
        return result;
    }

    public List<StudentDAO> getStudentsBySearch(HashMap<String, Object> searchmap) {
        
        List<StudentDAO> list = new ArrayList<StudentDAO>();
        
        String sqlSearch = "SELECT * FROM assesmentbackend.student WHERE ";
        String sqlCriteria = "";
        ArrayList<Object> values = new ArrayList<Object>();

        for (String searchKey : searchmap.keySet()) {

            if(sqlCriteria.length() > 0) {

                sqlCriteria += " AND ";
            }
            sqlCriteria += searchKey + " = ?";
            values.add(searchmap.get(searchKey));
         
            list = jdbcTemplate.query(sqlSearch + sqlCriteria, values.toArray(), getAllSqlTypes(values), studentRowMapper);
        }

        return list;
    }
}
