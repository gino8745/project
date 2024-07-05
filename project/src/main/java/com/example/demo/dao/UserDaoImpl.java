package com.example.demo.dao;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.passwordsalt;
import com.example.demo.security.HashCode;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	private HashCode hashCode;

	@Override
	public boolean addUser(User user) {
		// user_id, user_name, password, gender, user_email
		try {
			String sql = "insert into user(userName , password,salt ,gender, userEmail) value (?,? ,?,?,?) ";
			jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getSalt(), user.getGender(),
					user.getUserEmail());
			return true;
		} catch (Exception e) {
			System.err.print("已有同樣的信箱了");
			return false;
		}

	}

	@Override
	public boolean addLike(Integer userId, String cId, String newsId,char gender) {
		String like = cId + "-" + newsId + ",";
		String checksql = "SELECT CASE WHEN likeNews LIKE '%" + like
				+ "%' THEN TRUE ELSE FALSE END AS contains_likeNews FROM user WHERE userId = ?";
		boolean check = jdbcTemplate.queryForObject(checksql, boolean.class, userId);
		if (check) {
			String deletesql = "UPDATE user SET likeNews = REPLACE(likeNews, ?, '') WHERE userId = ?";
			if(gender=='男') {
				String newslikesql="UPDATE likedata SET likemale = likemale -1 WHERE news_id = ? AND category_id = ? limit 1";	
				jdbcTemplate.update(newslikesql,newsId,cId);
			}else if(gender == '女') {
				String newslikesql="UPDATE likedata SET likefemale = likefemale -1 WHERE news_id = ? AND category_id = ? limit 1";	
				jdbcTemplate.update(newslikesql,newsId,cId);
			}
			jdbcTemplate.update(deletesql, like, userId);
			return false;
		}

		String likesql = "UPDATE user SET likeNews = if(likeNews IS NOT NULL, CONCAT(likeNews,?),?) WHERE userId=?";
		if(gender=='男') {
			String newslikesql="UPDATE likedata SET likemale = likemale + 1 WHERE news_id = ? AND category_id = ? limit 1";	
		    jdbcTemplate.update(newslikesql,newsId,cId);
		}else if(gender == '女') {
			String newslikesql="UPDATE likedata SET likefemale = likefemale + 1 WHERE news_id = ? AND category_id = ? limit 1";	
			jdbcTemplate.update(newslikesql,newsId,cId);
		}
		int b=jdbcTemplate.update(likesql, like, like, userId);
		return true;
	}
    
	@Override
	public boolean getLike(Integer userId,String cId,String newsId) {
		String like = cId + "-" + newsId + ",";
		String checksql = "SELECT CASE WHEN likeNews LIKE '%" + like
				+ "%' THEN TRUE ELSE FALSE END AS contains_likeNews FROM user WHERE userId = ?";
		
		return jdbcTemplate.queryForObject(checksql,boolean.class,userId);
	}
	
	
	
	
	
	
	
	@Override
	public String findAllLike(Integer id) {
		String likesql = "SELECT CAST(likeNews AS CHAR) AS likeNews FROM user WHERE userId=? ;";
			String respones=jdbcTemplate.queryForObject(likesql, String.class, id);
			if(respones==null) {
				 return "false";
			}
			return respones;
	}

	@Override
	public boolean addRecord(String userid, String cid, String id, String date) {
		String like = cid + "-" + id + ",";
		String hash = date + like;
		String checkdatesql = "SELECT CASE WHEN record LIKE '%" + date	+ "%' THEN TRUE ELSE FALSE END AS contains_record FROM user WHERE userId = ?";
		String checksql = "SELECT CASE WHEN record LIKE '%" + like
				+ "%' THEN TRUE ELSE FALSE END AS contains_record FROM user WHERE userId = ?";
		String deletesql = "UPDATE user SET record = REPLACE(record, ?, '') WHERE userId = ?";
		boolean checkdate = jdbcTemplate.queryForObject(checkdatesql, boolean.class, userid);
		boolean check = jdbcTemplate.queryForObject(checksql, boolean.class, userid);
		
		if (checkdate) {
			if (check) {
				jdbcTemplate.update(deletesql, like,userid);
			}
			String recordsql = "UPDATE user SET record =  CONCAT(record,?) WHERE userId=?";
			jdbcTemplate.update(recordsql, like, userid);
			return false;
		} else {
			if (check) {
				jdbcTemplate.update(deletesql, like, userid);			
			}
			String recordsql = "UPDATE user SET record = if(record IS NOT NULL, CONCAT(record,?),?) WHERE userId=?";
			jdbcTemplate.update(recordsql, hash,hash, userid);  
		}
		return true;
	}
    
	@Override
	public String findAllRecord(Integer id) {
		String likesql = "SELECT CAST(record AS CHAR) AS record FROM user WHERE userId=? ";
		return jdbcTemplate.queryForObject(likesql, String.class, id);
	}
	@Override
	public int removeRecord(Integer id,String name) {
		
		String likesql = "update user SET record = REPLACE(record,?,'') WHERE userId=?";
		return jdbcTemplate.update(likesql,name ,id);
	}
	
	
	@Override
	public int updateUser(String password,String salt,String email) {
		String sql = "update user set  password=?,salt=? where userEmail=?";
		return jdbcTemplate.update(sql,password,salt,email);
	}
    
	@Override
	public boolean follow(Integer userid, String category) {
		String sql="SELECT CASE WHEN follow LIKE '%" + category+ "%' THEN TRUE ELSE FALSE END AS contains_follow FROM user WHERE userId = ?";
		boolean followcheck=jdbcTemplate.queryForObject(sql, boolean.class,userid);
        String categoryid=category+",";
		if(followcheck) {
			String delete="UPDATE user SET follow = REPLACE(follow, ?, '') WHERE userId = ?";
		    jdbcTemplate.update(delete,categoryid,userid);
		    return true;
		}
		
		
		String add="UPDATE user SET follow = if(follow IS NOT NULL, CONCAT(follow,?),?) WHERE userId=?";
		jdbcTemplate.update(add,categoryid,categoryid,userid);
		return false;
	}
	@Override
	public boolean getfollow(String category,String id) {
		String sql="SELECT CASE WHEN follow LIKE '%" + category+ "%' THEN TRUE ELSE FALSE END AS contains_follow FROM user WHERE userId = ?";
		return jdbcTemplate.queryForObject(sql,boolean.class ,id);
	};
	
	
	
	
	@Override
	public backuser login(passwordsalt password) {

		try {

			String sql = "select password,salt FROM user where userEmail=?";

			passwordsalt jdbc = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(passwordsalt.class),
					password.getUserEmail());
			String userpassword = jdbc.getPassword();
			String usersalt = jdbc.getSalt();
            
			boolean resp = decrypt(password.getPassword(), userpassword, usersalt);
			
			if (resp) {
				String resql = "select userId,userName,gender,userEmail,manager FROM user where userEmail=?";
				backuser respones = jdbcTemplate.queryForObject(resql, new BeanPropertyRowMapper<>(backuser.class),
						password.getUserEmail());
				return respones;
			}
		} catch (Exception e) {
			System.err.println("錯誤: " + e.toString());
		}
		return null;
	}

	@Override
	public boolean checkUseremail(String email) {
		String sql = "select case when exists(select 1 from user where userEmail= ?)then true else false end AS emailExists;";
		boolean response = jdbcTemplate.queryForObject(sql, boolean.class, email);
		if(response) {
			return true;
		}
        return false;
	}

    public static boolean decrypt(String password, String storedHash, String storedSalt) throws NoSuchAlgorithmException {
        byte[] salt = Base64.getDecoder().decode(storedSalt);
        HashCode hashCode = new HashCode();
        String hashedPassword = hashCode.hashPassword(password, salt);
        return hashedPassword.equals(storedHash);
    }
    private String Category(String id) {
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
