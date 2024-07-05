package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scripting.bsh.BshScriptUtils.BshExecutionException;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BaseDataDao;
import com.example.demo.model.po.BaseData;
import com.example.demo.model.po.Post;


@Service
public class BaseDateService {
    @Autowired
    private BaseDataDao baseDataDao;
	
	

	public Post getWorldNews(Integer newId,char gender) {
		
		return baseDataDao.getWorldNews(newId,gender);
	}

	

	
	public Post getPoliticsNews(Integer newId,char gender) {
		return baseDataDao.getPoliticsNews(newId,gender);
	}

	

	
	public Post getBusinessNews(Integer newId,char gender) {
		
		return baseDataDao.getBusinessNews(newId,gender);
	}



	public Post getHealthNews(Integer newId,char gender) {
		
		return baseDataDao.getHealthNews(newId,gender);
	}



	
	public Post getEntertainmentNews(Integer newId,char gender) {
	
		return baseDataDao.getEntertainmentNews(newId,gender);
	}

	

	
	public Post getTravelNews(Integer newId,char gender) {
	
		return baseDataDao.getTravelNews(newId,gender);
	}


	
	public Post getSportNews(Integer newId,char gender) {
		// TODO Auto-generated method stub
		return baseDataDao.getSportNews(newId,gender);
	}



	
	public Post getWeatherNews(Integer newId,char gender) {
		return baseDataDao.getWeatherNews(newId,gender);
	}

	public List<BaseData> findAllBaseDate() {
		return baseDataDao.findallbaseData();
	}
	
}
