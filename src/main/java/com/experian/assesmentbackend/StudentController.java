package com.experian.assesmentbackend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.experian.assesmentbackend.model.ScoreDTO;
import com.experian.assesmentbackend.model.Student;
import com.experian.assesmentbackend.model.StudentDTO;
import com.experian.assesmentbackend.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController {

	@Autowired
	StudentService studentService;

	@GetMapping("/student")
	public List<StudentDTO> GetStudentList() {

		List<StudentDTO> studentList = new ArrayList<StudentDTO>();
		studentService.getAllStudents().forEach(student -> studentList.add(new StudentDTO(student)));
        return studentList;
	}

	@GetMapping("/student/{id}")
	public StudentDTO StudentDetail(@PathVariable(value = "id") int id) {

		Student student = studentService.getStudentById(id);

		if (student == null) {

			throw new StudentNotFoundException("Student does not exist with id: '" + id + "'");
		}

		return new StudentDTO(student);
	}

	@PostMapping(	path = "/student",
					consumes = MediaType.APPLICATION_JSON_VALUE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public StudentDTO RegisterNewStudent(@RequestBody StudentDTO newStudent) {

		Student savedStudent = studentService.registerNewStudent(newStudent);
		if (savedStudent == null || savedStudent.getStudentNumber() < 1) {

			throw new ActionFailedException("Failed to create new student");
		} 

		return new StudentDTO(savedStudent);
	}

	@PutMapping("/student/{id}")
	public StudentDTO UpdateStudent(@PathVariable(value = "id") int id, @RequestBody StudentDTO studentDetails) {

		if(id != studentDetails.getStudentNumber()) {

			throw new StudentNotFoundException("Data mismatch");
		}

		Student savedStudent = studentService.updateStudent(id, studentDetails);
		if(savedStudent == null || savedStudent.getStudentNumber() == id && StudentService.updateCheck(studentDetails, new StudentDTO(savedStudent))) {

			throw new ActionFailedException("Failed to update student");
		} 

		return new StudentDTO(savedStudent);
	}

	@DeleteMapping("/student/{id}")
	public StudentDTO DeleteStudent(@PathVariable(value = "id") int id) {

		Student student = studentService.getStudentById(id);
		if(student == null) {

			throw new StudentNotFoundException("Student does not exist with id: '" + id + "'");
		}

		Student deletedStudent = studentService.deleteStudent(id);
		if(deletedStudent == null ) {

			throw new ActionFailedException("Failed to delete student");
		} 

		return new StudentDTO(deletedStudent);
	}

	@PostMapping("/student/{id}/score")
	public StudentDTO AddStudentScore(@PathVariable(value = "id") int id, @RequestBody ScoreDTO newScore) {

		Student student = studentService.getStudentById(id);
		if(student == null) {

			throw new StudentNotFoundException("Student does not exist with id: '" + id + "'");
		}

		boolean sucess = studentService.addScoreToStudent(id, newScore.getScore());
		if(!sucess) {

			throw new ActionFailedException("Failed to delete student");
		} 

		return new StudentDTO(studentService.getStudentById(id));
	}

	@GetMapping("/studentsearch")
	public List<StudentDTO> StudentSearch(	@RequestParam(value = "studentnumber", 	required = false) Integer studentnumber,
											@RequestParam(value = "firstname", 		required = false) String firstname,
											@RequestParam(value = "lastname", 		required = false) String lastname,
											@RequestParam(value = "email", 			required = false) String email) {

		HashMap<String, Object> searchmap = new HashMap<String, Object>();

		if(studentnumber != null) {

			searchmap.put("studentnumber", studentnumber);
		}

		if(firstname != null) {

			searchmap.put("firstname", firstname);
		}

		if(lastname != null) {

			searchmap.put("lastname", lastname);
		}

		if(email != null) {

			searchmap.put("email", email);
		}

		List<StudentDTO> studentList = new ArrayList<StudentDTO>();
		if (searchmap.size() > 0) {

			studentService.getStudentsBySearch(searchmap).forEach(student -> studentList.add(new StudentDTO(student)));
		}

        return studentList;
	}
}
