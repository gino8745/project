package com.example.demo.dao;

import java.util.List;

import com.example.demo.model.po.BaseData;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.postList;


public interface BaseDataDao {
 //category_id, news_id, image_1, title, post_1, image_2, post_2, image_3, post_3, image_4

    Post getWorldNews(Integer newId,char gender);
	
    
    

	Post getPoliticsNews(Integer newId,char gender);
	
	

	Post getBusinessNews(Integer newId,char gender);
	


	Post getHealthNews(Integer newId,char gender);


	Post getEntertainmentNews(Integer newId,char gender);
	
	
	Post getTravelNews(Integer newId,char gender);


	Post getSportNews(Integer newId,char gender);

	
	Post getWeatherNews(Integer newId,char gender);
	
	List<BaseData> findallbaseData();
	
	
	
}
