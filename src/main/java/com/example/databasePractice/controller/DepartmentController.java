package com.example.databasePractice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.databasePractice.dao.DepartmentDao;
import com.example.databasePractice.model.Department;

@RestController
public class DepartmentController {
	//@Autowired
	private DepartmentDao departmentdao;
	@PostMapping("/savedept")
	public String saveEMployee(@RequestBody Department dept) {
		return departmentdao.insertDepartment(dept);
	}
	
	@GetMapping("/savedeptGetKey/{dept}")
	public Integer saveDeptGetKey(@PathVariable String dept) {
		return departmentdao.insertAndGetKey(dept);
	}

}
