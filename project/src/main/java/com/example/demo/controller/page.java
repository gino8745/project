package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.Record;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.passwordsalt;
import com.example.demo.model.po.postList;
import com.example.demo.security.HashCode;
import com.example.demo.service.BaseDateService;
import com.example.demo.service.CaptchaService;
import com.example.demo.service.EmailService;
import com.example.demo.service.PostListService;
import com.example.demo.service.Userservice;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/page")
public class page {
	@Autowired
	private BaseDateService baseDateService;
	

	@GetMapping("/testpage")
	public ResponseEntity<ApiResponse<Post>> page(HttpSession session) {
	
		String cid=(String) session.getAttribute("categoryId");
		Integer id=(Integer) session.getAttribute("newsId");
		
		
		Post news=new Post();
		switch (cid) {
		case "1":
			news = baseDateService.getWorldNews(id, '男');
			break;
		case "2":
			news = baseDateService.getPoliticsNews(id, '男');
			break;
		case "3":
			news = baseDateService.getBusinessNews(id, '男');
			break;
		case "4":
			news = baseDateService.getHealthNews(id, '男');
			break;
		case "5":
			news = baseDateService.getEntertainmentNews(id, '男');
			break;
		case "6":
			news = baseDateService.getTravelNews(id, '男');
			break;
		case "7":
			news = baseDateService.getSportNews(id, '男');
			break;
		case "8":
			news = baseDateService.getWeatherNews(id, '男');
			break;
		};
		
		
		
		
		ApiResponse apiResponse = new ApiResponse<>(true, "query",news );
		return ResponseEntity.ok(apiResponse);
	}
	
	
}
