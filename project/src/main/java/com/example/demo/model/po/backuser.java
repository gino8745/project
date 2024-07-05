package com.example.demo.model.po;

import javax.swing.text.StyledEditorKit.BoldAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class backuser {
    Integer userId;
	String userName;
    String userEmail;
    String gender;
	Integer manager;
	boolean follow;
}
