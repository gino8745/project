package com.example.demo.dao;

import java.awt.Image;

import com.example.demo.model.po.Post;

import jakarta.servlet.http.HttpSession;

public interface PostDao {
   int addPost(Post post);
   int updatePost(Integer Id,Post post);
   int deletePost(Integer Id,Integer newsId); 
   int updateview(Integer name,Integer newsId,char gender);
}
