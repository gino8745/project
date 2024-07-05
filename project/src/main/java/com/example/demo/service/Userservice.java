package com.example.demo.service;

import org.aspectj.apache.bcel.generic.RET;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dao.UserDao;
import com.example.demo.dao.UserDaoImpl;
import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.passwordsalt;

@Service
public class Userservice {
    
	@Autowired
     private UserDaoImpl userDaoImpl;
	
	public boolean addUser(User user) {
		
		return userDaoImpl.addUser(user);
	}
	public boolean addLike(Integer userId, String cId, String newsId,char gender) {
		
		return userDaoImpl.addLike(userId, cId, newsId, gender);
	}
	public boolean getLike(Integer userId,String cId,String newsId) {
		return userDaoImpl.getLike(userId, cId, newsId);
	}
	public String findAllLike(Integer id) {
		return userDaoImpl.findAllLike(id);
	}
	
	public boolean addRecord(String userid,String cid,String id,String date) {
		return userDaoImpl.addRecord(userid,cid,id, date);
	}
	
	public String findAllRecord(Integer id) {
		
		return userDaoImpl.findAllRecord(id);
	}	
	
	public int removeRecord(Integer id,String name) {
		return userDaoImpl.removeRecord(id,name);
	}
	public int updateUser(String password,String salt,String email) {
		
		return userDaoImpl.updateUser(password,salt,email);
	}
	public boolean follow(Integer userid, String category) {
		return userDaoImpl.follow(userid, category);
	}
	public boolean getfollow(String category,String id) {
		return userDaoImpl.getfollow(category,id);
	}
	
	
	public backuser login(passwordsalt password) {
		
		return userDaoImpl.login( password);
	}
	public boolean checkUseremail(String email) {
		return userDaoImpl.checkUseremail(email);
	}

	
}
