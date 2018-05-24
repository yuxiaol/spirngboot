package com.tdhy.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "b_role")
@Data
public class Role {

	 	@Id
	    @GeneratedValue
	    private Long id;
	    private String roleName;
	    @ManyToOne(fetch = FetchType.EAGER)
	    private User user;
//	    @OneToMany(mappedBy = "role")
//	    private List<Permission> permissions;
}
