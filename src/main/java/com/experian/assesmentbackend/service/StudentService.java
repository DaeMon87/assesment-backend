package com.experian.assesmentbackend.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.experian.assesmentbackend.dao.ScoreDAO;
import com.experian.assesmentbackend.dao.StudentDAO;
import com.experian.assesmentbackend.model.Student;
import com.experian.assesmentbackend.model.StudentDTO;
import com.experian.assesmentbackend.repository.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    @Autowired
    Repository repository;

    Logger logger = LoggerFactory.getLogger(StudentService.class);


    public StudentService() {
    }

    // getting all student records
    public List<Student> getAllStudents() {

        List<Student> studentList = new ArrayList<Student>();
        repository.getStudentList(null, null).forEach(student -> studentList.add(new Student(student)));

        for(int i = 0; i < studentList.size(); i++) {

            populateStudentScores(studentList.get(i));
        }

        return studentList;
    }

    // getting a specific record
    public Student getStudentById(Integer id) {

        Student requestedStudent = null;

        StudentDAO studentDAO = repository.getStudentById(id);
        if (studentDAO != null && studentDAO.getStudentNumber() != 0) {

            requestedStudent = new Student(studentDAO);
            populateStudentScores(requestedStudent);
        }

        return requestedStudent;
    }

    public void populateStudentScores(Student student) {

        List<ScoreDAO> scores = repository.getStudentScores(student.getStudentNumber());
        for (ScoreDAO scoreDAO : scores) {
            
            student.addScore(scoreDAO.getScore());
        }
    }

    public Student registerNewStudent(StudentDTO newStudent) {
        
        Student incomingStudent = null;
        if (newStudent != null) {

            incomingStudent = new Student(newStudent);
        }
        
        // Enforce firstname and lastname as mandatory
        if (incomingStudent != null && incomingStudent.getFirstName() != null && incomingStudent.getLastName() != null) {

            try {
                
                List<StudentDAO> nameList = repository.findStudentByName(incomingStudent.getFirstName(), incomingStudent.getLastName());
                if (nameList.isEmpty()) {

                    StudentDAO studentDAO = repository.addStudent(incomingStudent);
                    return new Student(studentDAO);
                }

            } catch (Exception ex) {

                logger.error("Exception occured creating new student", ex);
            } 
        }
        return null;
    }

    public Student updateStudent(int studentnumber, StudentDTO studentDetails) {

        Student targetStudent = getStudentById(studentnumber);
        if (targetStudent != null) {

            HashMap<String, Object> updateMap = new HashMap<String, Object>();
            // Check for updatable fields

            if(studentDetails.getFirstName() != targetStudent.getFirstName()) {

                updateMap.put("firstname", studentDetails.getFirstName());
            }

            if(studentDetails.getLastName() != targetStudent.getLastName()) {

                updateMap.put("lastname", studentDetails.getLastName());
            }

            if(studentDetails.getDateOfBirth() != targetStudent.getDateOfBirth()) {

                updateMap.put("dateofbirth", studentDetails.getDateOfBirth());
            }

            if(studentDetails.getCellNumber() != targetStudent.getCellNumber()) {

                updateMap.put("cellnumber", studentDetails.getCellNumber());
            }

            if(studentDetails.getEmailAddress() != targetStudent.getEmailAddress()) {

                updateMap.put("emailaddress", studentDetails.getEmailAddress());
            }

            StudentDAO updatedStudent = repository.updateStudent(studentnumber, updateMap);
            if (updatedStudent != null) {

                return new Student(updatedStudent);
            }
        }
        return null;
    }

    public static boolean updateCheck(StudentDTO studentDetails, StudentDTO savedStudent) {

        return false;
    }

    public Student deleteStudent(int id) {

        Student targetStudent = getStudentById(id);

        int result = repository.deleteStudent(id);
        if(result > 0) {

            return targetStudent;
        }

        return null;
    }

    public boolean addScoreToStudent(int id, int score) {

        // range limit for new scores
        if(score < 0 || score > 100) {

            return false;
        }

        int result = repository.addScore(id, score);

        return result > 0;
    }

    public List<Student> getStudentsBySearch(HashMap<String, Object> searchmap) {
        
        List<Student> studentList = new ArrayList<Student>();
        repository.getStudentsBySearch(searchmap).forEach(student -> studentList.add(new Student(student)));
        return studentList;
    }
}