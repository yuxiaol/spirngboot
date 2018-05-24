package com.tdhy.util;

/** 
 * BaseSystemConstants
 *  常量数据类
 * @author CaoZhongyu
 * @date 2016年6月4日
 */
public final class BaseSystemConstants {
	/** 升序 */
	public final static String ASC = "asc";

	/** 降序 */
	public final static String DESC = "desc";

	/** 初始页号 */
	public final static int DEFAULT_PAGE_NOM = 1;

	/** 初始页记录数 */
	public final static int DEFAULT_PAGE_SIZE = 10;

	/** 默认排序字段 */
	public final static String DEFAULT_ORDER_BY = "id";

	/** 默认系统单位 */
	public final static String COMPANY = "";

	/** 默认添加的数据一律为非删除状态 0 */
	public static final Integer DEFAULT_ISNOTREMOVED = 0;

	/** 删除时为删除状态 1 */
	public static final Integer DEFAULT_ISREMOVED = 1;

	/** 管理员添加用户时默认的密码 123456 */
	public final static String DEFAULT_USER_PASSWORD = "123456";

	/** 系统默认的级别根目录的编码统一跟数据库对应,支持动态改编码以及位数 00000 */
	public final static String PARENT_CODE = "00000";

	/** 系统默认的叶子节点标记 1 */
	public final static Integer ISLEAF = 1;

	/** 系统默认的目录节点标记 0 */
	public final static Integer ISNOTLEAF = 0;

	/** 系统数据激活状态 **/
	public static final Integer DEFAULT_ACTIVE = 1;

	/** 系统数据非激活状态 **/
	public static final Integer ISNOT_ACTIVE = 0;

	/** 设置多大内存为系统里最低内存 */
	public final static Integer LOW_MEMORY_COUNT = 512;

	/** 系统默认超级管理员名 admin */
	public final static String DEFAULT_ADMIN_NAME = "admin";
	/**  管理员默认name名  */
	public final static String ADMIN_NAME="系统管理员";
	/**
	 * 原图存放路径
	 */
	public final static String IMG_PATH = "c://file/dbzq_related/";
	/**
	 * 缩略图存放路径
	 */
	public final static String IMG_PATH_MIN = IMG_PATH+"/min/";
	
	/**
	 * server.xml 配置图片域名
	 */
	public final static String SERVER_IMG_PATH = "/upload";
	/**
	 * 缩略图路径
	 */
	public final static String SERVER_IMG_PATH_MIN = SERVER_IMG_PATH+"/min/";
	
	/**
	 * 修改批次产量差量
	 */
	public final static int MIN_MODIFY_ARGS = 20;
	/**
	 * 后台SESSION
	 */
	public final static String MANAGE_SESSION_KEY = "manageSessionKey";
	/**
	 * 客户SESSION
	 */
	public final static String CLIENT_SESSION_KEY = "clientSessionKey";
	/**
	 * openid SESSION
	 */
	public final static String OPENID_SESSION_KEY = "OPENID";
	
	public final static String SUCCESS= "SUCCESS";
	

}
