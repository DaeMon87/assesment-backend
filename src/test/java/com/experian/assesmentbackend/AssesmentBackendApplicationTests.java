package com.experian.assesmentbackend;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyMapOf;
import static org.mockito.ArgumentMatchers.anyObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.experian.assesmentbackend.dao.ScoreDAO;
import com.experian.assesmentbackend.dao.StudentDAO;
import com.experian.assesmentbackend.model.ScoreDTO;
import com.experian.assesmentbackend.model.Student;
import com.experian.assesmentbackend.model.StudentDTO;
import com.experian.assesmentbackend.repository.Repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
class AssesmentBackendApplicationTests extends AbstractTester {

	@MockBean
	private Repository repository;

	@Override
    @BeforeEach 
    public void setUp() {
        super.setUp();

		ArrayList<Student> studentList = new ArrayList<Student>();
		studentList.add(getStudentObject());

		ArrayList<StudentDAO> studentDAOList = new ArrayList<StudentDAO>();
		studentDAOList.add(getStudentDAOObject());

		ArrayList<StudentDTO> studentDTOList = new ArrayList<StudentDTO>();
		studentDTOList.add(getStudentDTOObject());

		List<ScoreDAO> scoreDTOList = new ArrayList<ScoreDAO>();
		scoreDTOList.add(new ScoreDAO());

		Mockito.when(repository.getStudentList(null, null)).thenReturn(studentDAOList);
		Mockito.when(repository.getStudentById(1)).thenReturn(getStudentDAOObject());
		Mockito.when(repository.getStudentScores(1)).thenReturn(scoreDTOList);
		Mockito.when(repository.addScore(anyInt(), anyInt())).thenReturn(1);
		Mockito.when(repository.findStudentByName(any(), any())).thenReturn(studentDAOList);
		Mockito.when(repository.updateStudent(anyInt(), any())).thenReturn(getStudentDAOObject());
		Mockito.when(repository.deleteStudent(1)).thenReturn(1);
		Mockito.when(repository.getStudentsBySearch(any())).thenReturn(studentDAOList);

		try {
			Mockito.when(repository.addStudent(any(Student.class))).thenReturn(getStudentDAOObject());
		} catch (Exception e) {
		} 
		
    }

	@Test
	void contextLoads() {
	}

	@Test
    public void getStudentList() throws Exception {

        String uri = "/student";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
            .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        StudentDTO[] Studentlist = super.mapFromJson(content, StudentDTO[].class);
        assertTrue(Studentlist.length > 0);
   }

   @Test
   public void registerStudent() throws Exception {

		ArrayList<StudentDAO> studentDAOListFirst = new ArrayList<StudentDAO>();
		ArrayList<StudentDAO> studentDAOListSecond = new ArrayList<StudentDAO>();
		studentDAOListSecond.add(getStudentDAOObject());
		Mockito.when(repository.findStudentByName(any(), any())).thenReturn(studentDAOListFirst, studentDAOListSecond);
		

		String uri = "/student";

		String inputJson = super.mapToJson(getStudentObject());
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		// String content = mvcResult.getResponse().getContentAsString();
		// String resultJson = super.mapToJson(studentDTO);
		// assertEquals(content, resultJson);
   	}

	@Test
	public void updateStudent() throws Exception {

		String uri = "/student/1";
		Student Student = getStudentObject();
		
		String inputJson = super.mapToJson(Student);
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		// String content = mvcResult.getResponse().getContentAsString();
		// assertEquals(content, "Student is updated successsfully");
	}

	@Test
	public void deleteStudent() throws Exception {

		String uri = "/student/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		// String content = mvcResult.getResponse().getContentAsString();
		// assertEquals(content, "Student is deleted successsfully");
	}

   @Test
   public void searchStudent() throws Exception {

		String uri = "/studentsearch?firstname=Bingo";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);

		String content = mvcResult.getResponse().getContentAsString();
		StudentDTO[] Studentlist = super.mapFromJson(content, StudentDTO[].class);
		assertTrue(Studentlist.length > 0);
   }

	@Test
	public void addScore() throws Exception {

		String uri = "/student/1/score";

		String inputJson = super.mapToJson(new ScoreDTO(1, 90));
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
			.contentType(MediaType.APPLICATION_JSON_VALUE)
			.content(inputJson)).andReturn();
		
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	public Student getStudentObject() {

		Student student = new Student();
		student.setStudentNumber(1);
		student.setFirstName("Bingo");
		student.setLastName("Bongo"); 
		student.setEmailAddress("asd@asd.com"); 
		student.setCellNumber("9876543210"); 
		student.setDateOfBirth(new Date()); 

		student.addScore(80);
		student.addScore(90);

		return student;
	}

	public StudentDAO getStudentDAOObject() {

		StudentDAO student = new StudentDAO(getStudentObject());	
		return student;
	}

	public StudentDTO getStudentDTOObject() {

		StudentDTO student = new StudentDTO(getStudentObject());
		return student;
	}

}
