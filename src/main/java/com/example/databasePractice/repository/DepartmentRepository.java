package com.example.databasePractice.repository;

import com.example.databasePractice.model.Department;

public interface DepartmentRepository {
	public Integer insertAndGetKey(String other);
	public String insertDepartment(Department dept);
	Boolean getDepartmentByName(String other);
}
