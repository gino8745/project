package com.example.demo.model.po;

import org.aspectj.internal.lang.annotation.ajcPrivileged;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class passwordsalt {
	String userEmail;
	String password;
	String salt;
	Integer userId;
	String userName;
	Integer manager;
}
