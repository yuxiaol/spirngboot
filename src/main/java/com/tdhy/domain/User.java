package com.tdhy.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name = "b_user")
public class User implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO() 
	*/ 
	private static final long serialVersionUID = 1L;
	
		@Id
	    @GeneratedValue
	    private Long id;
	    @Column(name = "name")
	    private String name;
	    private String password;
//	    @OneToMany(mappedBy = "user")
//	    private List<Role> roles;

}
