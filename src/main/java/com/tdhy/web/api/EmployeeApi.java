package com.tdhy.web.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tdhy.domain.Employee;
import com.tdhy.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "api/employee")
@Api("Employee 相关的api")
public class EmployeeApi {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("list")
	@ApiOperation(value = "查询学生的信息,根据学生查询")
	@ApiImplicitParams({@ApiImplicitParam(name="pageNo" ,value = "页码" ,paramType = "path" , required = false,dataType = "Integer")
	,@ApiImplicitParam(name="pageSize" ,value = "偏移量" ,paramType = "path" , required = false,dataType = "Integer")})
	public List<Employee> list(Integer pageNo,Integer pageSize){
		pageNo = pageNo==null?0:pageNo;
		pageSize = pageSize==null?10:pageSize;
		return employeeService.findAll(pageNo,pageSize);
	}
	
	@GetMapping("{id}/findOne")
	public Employee getOne(@PathVariable Long id){
		return employeeService.findOne(id);
	}
	
	@GetMapping("{id}/modifyOne")
	public Employee modifyOne(@PathVariable Long id){
		Employee employee = employeeService.findOne(id);
		if(null == employee) {
			return null;
		}
		employee.setName("modifyName");
		employeeService.save(employee);
		return employee;
	}
	
	@GetMapping("{id}/remvoe")
	public String  remvoe(@PathVariable Long id){
		try {
			employeeService.remove(id);
		} catch (Exception e) {
			// TODO: handle exception
			return "error";
		}
		return "success";
	}
	
	
	@GetMapping("testcache")
	public String testcache() {
		return employeeService.testCache();
	}
	
}
