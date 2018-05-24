package com.tdhy.service;

import java.util.List;

import com.tdhy.domain.Employee;

public interface EmployeeService {
	
	List<Employee> findAll(Integer pageNo,Integer pageSize);
	
	Employee findOne(Long id);
	
	void save(Employee Employee);
	
	void remove(Long id);
	
	
	void test01();
	
	
	String testCache();
}
