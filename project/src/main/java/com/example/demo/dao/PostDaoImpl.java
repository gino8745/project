package com.example.demo.dao;

import java.io.Console;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.text.StyledEditorKit.BoldAction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.responseuser;
import com.example.demo.service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;

@Repository
public class PostDaoImpl implements PostDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EmailService emailService;

	@Override
	public int addPost(Post post) {
		// category_id, news_id, image_1, title, post_1, image_2, post_2, image_3,
		// post_3, image_4, create_time
		PostDaoImpl postDaoImpl = new PostDaoImpl();
		String tableName = postDaoImpl.Category(post.getCategoryId());
		String sql = "insert into " + tableName
				+ "(title,writer,writerId,image_1,post_1,image_2,post_2,image_3,post_3,create_time) values (?,?,?,?,?,?,?,?,?,?)";
		int a = jdbcTemplate.update(sql, post.getTitle(), post.getWriter(), post.getWriterId(), post.getImage_1(),
				post.getPost_1(), post.getImage_2(), post.getPost_2(), post.getImage_3(), post.getPost_3(),
				postDaoImpl.getDate());
		String like = "SELECT category_id,news_id,(writerId) as userId FROM " + tableName
				+ " order by news_id DESC limit 1";
		Likenews getid = jdbcTemplate.queryForObject(like, new BeanPropertyRowMapper<>(Likenews.class));

		String likesql = "insert into likedata (category_id, news_id,user_id,creattime) value (?,?,?,?)";
		jdbcTemplate.update(likesql, getid.getCategoryId(), getid.getNewsId(), getid.getUserId(),
				postDaoImpl.getDate());

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(true);
		session.setAttribute("categoryId", getid.getCategoryId());
		session.setAttribute("newsId", Integer.parseInt(getid.getNewsId()));
		String cfsql = "SELECT userId,userEmail FROM user where follow LIKE '%"+post.getWriter()+"%' or follow LIKE'%"+tableName+"%'";

		List<backuser> n = jdbcTemplate.query(cfsql, new BeanPropertyRowMapper(backuser.class));
		for(backuser i:n) {
			
		    String checksql="SELECT CASE WHEN follow LIKE '%"+post.getWriter()+"%' and follow LIKE'%"+tableName+"%' "
		    		         + "THEN TRUE ELSE FALSE END AS follow FROM user where userId="+i.getUserId();
		    
		    boolean b=jdbcTemplate.queryForObject(checksql, boolean.class);
			if(b) {
			    try {
					emailService.sendNewstoMail(i.getUserEmail(), post.getTitle(), post.getPost_1());
				} catch (MessagingException e) {
					
					e.printStackTrace();
				}
			}else {
			    try {
					emailService.sendNewstoMail(i.getUserEmail(), post.getTitle(), post.getPost_1());
				} catch (MessagingException e) {
					
					e.printStackTrace();
				}
			}    
		}
		return a;
	}

	@Override
	public int updatePost(Integer Id, Post post) {
		PostDaoImpl postDaoImpl = new PostDaoImpl();
		String tableName = postDaoImpl.Category(post.getCategoryId());
		String sql = "update " + tableName
				+ " set title=?,writer=? ,image_1=?,post_1=?,image_2=?,post_2=?,image_3=?,post_3=? where news_id=?";

		return jdbcTemplate.update(sql, post.getTitle(), post.getWriter(), post.getImage_1(), post.getPost_1(),
				post.getImage_2(), post.getPost_2(), post.getImage_3(), post.getPost_3(), Id);
	}

	@Override
	public int deletePost(Integer Id, Integer newsId) {
		PostDaoImpl postDaoImpl = new PostDaoImpl();
		String tableName = postDaoImpl.Category(Id);
		String sql = "delete from " + tableName + " where news_id=?";

		String deletesql = "DELETE FROM likedata WHERE category_id = ? AND news_id = ? LIMIT 1";
		jdbcTemplate.update(deletesql, Id, newsId);
		return jdbcTemplate.update(sql, newsId);
	}

	@Override
	public int updateview(Integer name, Integer newsId, char gender) {

		if (gender == '男') {
			String sql = "UPDATE likedata SET viewmale = viewmale + 1 WHERE news_id = ? AND category_id = ? LIMIT 1";
			return jdbcTemplate.update(sql, newsId, name);
		} else if (gender == '女') {
			String sql = "UPDATE likedata SET viewfemale = viewfemale + 1 WHERE news_id = ? AND category_id = ? LIMIT 1";
			return jdbcTemplate.update(sql, newsId, name);
		}
		String sql = "UPDATE likedata SET viewmale = viewmale + 1 WHERE news_id = ? AND category_id = ? LIMIT 1";
		return jdbcTemplate.update(sql, newsId);
	}

	private String Category(Integer id) {
		String tableName = null;
		switch (id) {
		case 1:
			tableName = "world_news";
			break;
		case 2:
			tableName = "politics_news";
			break;
		case 3:
			tableName = "business_news";
			break;
		case 4:
			tableName = "health_news";
			break;
		case 5:
			tableName = "entertainment_news";
			break;
		case 6:
			tableName = "travel_news";
			break;
		case 7:
			tableName = "sport_news";
			break;
		case 8:
			tableName = "weather_news";
			break;
		}
		return tableName;
	}

	private Date getDate() {
		Date date = new Date();
		return date;
	}

	public static byte[] decode(String base64String) {
		return Base64.getDecoder().decode(base64String);
	}

}
