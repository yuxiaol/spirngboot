package com.tdhy.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "t_employee")
@AllArgsConstructor
@NoArgsConstructor
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	
	private Integer sex;
	
	private Integer age;
	
	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;
	
	
	
}
