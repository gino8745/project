package com.example.demo.service;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PostDaoImpl;
import com.example.demo.dao.postListDao;
import com.example.demo.dao.postListDaoImpl;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.postList;
import com.example.demo.model.po.view;

@Service
public class PostListService{

	@Autowired
     private postListDaoImpl postListDaoImpl;

	
	public List<postList> findAllPostList(String Id) {	
		return postListDaoImpl.findAllPostList(Id);
	}
	public postList getPostList(String cid,String Id) {	
		return postListDaoImpl.getPostList(cid,Id);
	}
	public List<postList> getrandomPost(String cId, String Id) {
		return postListDaoImpl.getrandomPost(cId, Id);
	}
	public List<postList> findAllwriternews(Integer name) {
		return postListDaoImpl.findAllwriternews(name);
	}
	public List<postList> findMostview(String name) {
		
		return postListDaoImpl.findMostview(name);
	}
	public List<Post> findbreakingnews() {
		
		return postListDaoImpl.findbreakingnews();
	}
	
	public List<view> findAllPostView(Integer id) {
		return postListDaoImpl.findAllPostView(id);
	}
}
