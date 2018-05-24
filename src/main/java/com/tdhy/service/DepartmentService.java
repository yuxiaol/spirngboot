package com.tdhy.service;

import java.util.List;

import com.tdhy.domain.Department;

public interface DepartmentService {
	
	List<Department> findAll();
	
	Department findOne(Long id);
	
	void save(Department department);
	
	void remove(Long id);
}
