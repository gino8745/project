package com.example.demo.dao;

import org.springframework.cglib.core.TinyBitSet;

import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.passwordsalt;
import com.example.demo.model.po.responseuser;



public interface UserDao {
	boolean addUser(User user);
    boolean addLike(Integer userId,String cId,String newsId,char gender);
    boolean getLike(Integer userId,String cId,String newsId);
    String findAllLike(Integer id);
    boolean addRecord(String userid,String cid,String id,String date);
    String findAllRecord(Integer id);
    int removeRecord(Integer id,String name);
	int updateUser(String password,String salt,String email);
	boolean follow(Integer userid,String category);
	public boolean getfollow(String category,String id);
	backuser login(passwordsalt password);
	boolean checkUseremail(String email);
}
