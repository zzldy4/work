package com.example.userlogin.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户类
 */
@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class User implements Serializable {

	/**
	 * 用户id
	 */
	private Integer id;

	/**
	 * 用户名
	 */
	@NotEmpty(message = "用户名不能为空！")
	private String username;

	/**
	 * 密码
	 */
	@NotEmpty(message = "密码不能为空！")
	@Size(min = 8, message = "密码长度不能小于8！")
	private String password;

}