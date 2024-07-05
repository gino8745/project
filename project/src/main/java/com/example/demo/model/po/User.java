package com.example.demo.model.po;

import java.util.Collection;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
  //user_id, user_name, password, gender, user_email, manager

  String username;
  String password;
  String gender;
  String salt;
  String likeNews;
  String userEmail;

}
