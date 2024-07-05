package com.example.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.po.BaseData;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.postList;
import com.example.demo.service.PostListService;

@Repository
public class BaseDataDaoImpl implements BaseDataDao{
    
	@Autowired
	 JdbcTemplate jdbcTemplate ;
	@Autowired
	  PostDaoImpl postDaoImpl;
	
	
//	private String findAllSqlTemplate="select category_id, news_id, image_1, title, post_1, image_2, post_2, image_3, post_3, image_4,create_time from ";
	private String findAllSqlTemplate="SELECT news_id, CAST(image_1 AS CHAR) AS image_1, title,create_time FROM";
	private String findview="SELECT news_id, CAST(image_1 AS CHAR) AS image_1, title,create_time FROM ";
	private String orderview=" ORDER BY view DESC LIMIT 4;";
	private String getsql="select category_id, news_id,writer, image_1, title, post_1, image_2, post_2, image_3, post_3,create_time from ";
	

	@Override
	public Post getWorldNews(Integer newId ,char gender) {
		String tableName = "world_news";
        String sql=getsql+tableName+" where news_id=?";
       
        postDaoImpl.updateview(1, newId,gender);
        
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);
	}



	@Override
	public Post getPoliticsNews( Integer newId,char gender) {
		String tableName = "Politics_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(2, newId,gender);
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);
	}



	@Override
	public Post getBusinessNews( Integer newId,char gender) {
		String tableName = "Business_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(3, newId,gender);
        
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);

	}



	@Override
	public Post getHealthNews( Integer newId,char gender) {
		String tableName = "Health_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(4, newId,gender);
        
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);

	}



	@Override
	public Post getEntertainmentNews(Integer newId,char gender) {
		String tableName = "Entertainment_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(5, newId,gender);
        
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);

	}



	@Override
	public Post getTravelNews(Integer newId,char gender) {
		String tableName = "Travel_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(6, newId,gender);
        
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);

	}



	@Override
	public Post getSportNews(Integer newId,char gender) {
		String tableName = "Sport_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(7, newId,gender);
        
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);

	}



	@Override
	public Post getWeatherNews(Integer newId,char gender) {
		String tableName = "Weather_news";
        String sql=getsql+tableName+" where news_id=?";
        postDaoImpl.updateview(8, newId,gender);
        
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Post.class),newId);
	}

	@Override
	public List<BaseData> findallbaseData() {
		//category_id, category_name
		String sql="select category_id, category_name from basedata order by category_id asc";
		return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(BaseData.class));
	}




}
