package com.tdhy.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public class PersistentObject implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 创建时间
	 */
	@Column(name = "create_date")
	private Date createDate = new Date();
	/**
	 * 状态 0：冻结 1：激活
	 */
	@Column(name = "remove_status")
	private Integer removeStatus ;



}
