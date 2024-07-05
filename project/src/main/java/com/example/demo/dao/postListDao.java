package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.po.Post;
import com.example.demo.model.po.postList;
import com.example.demo.model.po.view;

public interface postListDao {
    List<postList> findAllPostList(String name);
    postList getPostList(String cId,String Id);
    List<postList> getrandomPost(String cId,String Id);
    List<postList> findAllwriternews(Integer name);
	List<Post> findbreakingnews();
    List<postList> findMostview(String name);
    List<postList> findUserLike(Integer userId);
    List<view> findAllPostView(Integer id);
}
