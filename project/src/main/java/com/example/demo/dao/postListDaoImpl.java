package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.postList;
import com.example.demo.model.po.view;
import com.example.demo.util.ImageUtils;

@Repository
public class postListDaoImpl implements postListDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private ImageUtils imageUtils;

	private String findview = "SELECT news_id, CAST(image_1 AS CHAR) AS image_1, title,create_time FROM ";
	private String orderview = " ORDER BY view DESC LIMIT 4;";
	private String sql = " SELECT category_id, news_id, image_1, title,post_1, create_time FROM ("
			+ " SELECT category_id, news_id, image_1, title, create_time,post_1 FROM world_news" + " UNION ALL"
			+ "	SELECT category_id, news_id, image_1, title, create_time,post_1 FROM politics_news" + " UNION ALL"
			+ " SELECT category_id, news_id, image_1, title, create_time,post_1 FROM business_news" + " UNION ALL"
			+ " SELECT category_id, news_id, image_1, title, create_time,post_1 FROM health_news" + " UNION ALL"
			+ " SELECT category_id, news_id, image_1, title, create_time,post_1 FROM entertainment_news" + " UNION ALL"
			+ " SELECT category_id, news_id, image_1, title, create_time,post_1 FROM travel_news" + " UNION ALL"
			+ "	SELECT category_id, news_id, image_1, title, create_time,post_1 FROM sport_news" + " UNION ALL"
			+ "	SELECT category_id, news_id, image_1, title, create_time,post_1 FROM weather_news" + ") AS combined_news";

	@Override
	public List<view> findAllPostView(Integer id) {
		String sql = "SELECT category_id,SUM(likemale) AS LikeMale,"
				+ "SUM(likefemale) AS LikeFemale,"
				+ "SUM(likemale + likefemale) AS totalLike,"
				+ "SUM(viewmale) AS ViewMale,"
				+ "SUM(viewfemale) AS ViewFemale,"
				+ "SUM(viewmale + viewfemale) AS totalView "
				+ "FROM likedata WHERE user_id = ? GROUP BY category_id";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(view.class), id);
	}

	@Override
	public postList getPostList(String cId, String Id) {

		String sql = "select category_id,news_id,create_time ,image_1,post_1,title from " + cId + " where news_id=" + Id;
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(postList.class));
	}

	@Override
	public List<postList> getrandomPost(String cId, String Id) {
		String randemsql = "SELECT category_id,news_id,image_1,title FROM "+cId+" where news_id not in ("+Id+") ORDER BY RAND() LIMIT 4";

		return jdbcTemplate.query(randemsql, new BeanPropertyRowMapper<>(postList.class));
	}
	
	
	
	@Override
	public List<postList> findAllPostList(String name) {
		postListDaoImpl PLDI = new postListDaoImpl();
		String tablename = PLDI.CategoryString(name);
		String sql = "select news_id,create_time ,image_1,title,post_1 from " + tablename + " order by create_time DESC";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(postList.class));
	}

	@Override
	public List<postList> findMostview(String name) {
		postListDaoImpl PLDI = new postListDaoImpl();
		String liketable = PLDI.CategoryInt(name);
		String tablename = PLDI.CategoryString(name);
		String likesql = "SELECT news_id,(viewmale+viewfemale)as total FROM likedata where category_id=" + liketable
				+ "  order by total DESC limit 4";
		List<Likenews> like = jdbcTemplate.query(likesql, new BeanPropertyRowMapper(Likenews.class));
		List<postList> postLists = new ArrayList();
		for (Likenews i : like) {
			postLists.add(getPostList(tablename, i.getNewsId()));
		}

		return postLists;
	}

	@Override
	public List<postList> findAllwriternews(Integer name) {
		String sql = "select category_id,news_id  from likedata where user_id=? ORDER BY creattime DESC";
		try {
			List<Likenews> like = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Likenews.class), name);

			List<postList> postLists = new ArrayList();
			for (Likenews i : like) {
				postLists.add(getPostList(CategoryInttoString(i.getCategoryId()), i.getNewsId()));
			}

			return postLists;
		} catch (Exception e) {
			return null;
		}

	};

	@Override
	public List<Post> findbreakingnews() {
		String bnsql = sql + " ORDER BY create_time DESC LIMIT 5";
		return jdbcTemplate.query(bnsql, new BeanPropertyRowMapper(Post.class));
	}

	@Override
	public List<postList> findUserLike(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	private String CategoryString(String id) {
		String tableName = null;
		switch (id) {
		case "world":
			tableName = "world_news";
			break;
		case "politics":
			tableName = "politics_news";
			break;
		case "business":
			tableName = "business_news";
			break;
		case "health":
			tableName = "health_news";
			break;
		case "entertainment":
			tableName = "entertainment_news";
			break;
		case "travel":
			tableName = "travel_news";
			break;
		case "sport":
			tableName = "sport_news";
			break;
		case "weather":
			tableName = "weather_news";
			break;
		}
		return tableName;
	}

	private String CategoryInt(String id) {
		String tableName = null;
		switch (id) {
		case "world":
			tableName = "1";
			break;
		case "politics":
			tableName = "2";
			break;
		case "business":
			tableName = "3";
			break;
		case "health":
			tableName = "4";
			break;
		case "entertainment":
			tableName = "5";
			break;
		case "travel":
			tableName = "6";
			break;
		case "sport":
			tableName = "7";
			break;
		case "weather":
			tableName = "8";
			break;
		}
		return tableName;
	}

	private String CategoryInttoString(String id) {
		String tableName = null;
		switch (id) {
		case "1":
			tableName = "world_news";
			break;
		case "2":
			tableName = "politics_news";
			break;
		case "3":
			tableName = "business_news";
			break;
		case "4":
			tableName = "health_news";
			break;
		case "5":
			tableName = "entertainment_news";
			break;
		case "6":
			tableName = "travel_news";
			break;
		case "7":
			tableName = "sport_news";
			break;
		case "8":
			tableName = "weather_news";
			break;
		}
		return tableName;
	}

}
