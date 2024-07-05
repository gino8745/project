package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.PostDao;
import com.example.demo.model.po.Post;

@Service
public class PostService {
    @Autowired
    private PostDao postDao;
	
	

	public int addPost(Post post) {
		
		return postDao.addPost(post);
	}


	public int updatePost(Integer Id, Post post) {
		
		return postDao.updatePost(Id, post);
	}


	public int deletePost(Integer Id ,Integer newsId) {
	 
		return postDao.deletePost(Id,newsId);
	}
   
}
