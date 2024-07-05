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

import com.example.demo.dao.UserDaoImpl;
import com.example.demo.model.ApiResponse;
import com.example.demo.model.po.Likenews;
import com.example.demo.model.po.Record;
import com.example.demo.model.po.User;
import com.example.demo.model.po.backuser;
import com.example.demo.model.po.passwordsalt;
import com.example.demo.model.po.postList;
import com.example.demo.security.HashCode;
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
@RequestMapping("/user")
public class UserController {

	@Autowired
	private Userservice userservice;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	private PostListService postListService;
    
	@Autowired
	private EmailService emailService;
	 
	@Autowired
	private CaptchaService captchaService;

	private HashCode hashCode;

	@PostMapping
	public ResponseEntity<ApiResponse<User>> addUser(@RequestBody User userdata) throws Exception {

		boolean response = userservice.checkUseremail(userdata.getUserEmail());
        
		if (!response) {
			String hashResult = hashCode.hashcode(userdata.getPassword());
			String[] hr = hashResult.split(" ");
			userdata.setPassword(hr[0]);
			userdata.setSalt(hr[1]);
			boolean user = userservice.addUser(userdata);
			if (user == true) {
				ApiResponse apiResponse = new ApiResponse(response, "success", user);
				return ResponseEntity.ok(apiResponse);
			}
		}

		ApiResponse apiResponse = new ApiResponse(response, "fail", "false");
		return ResponseEntity.ok(apiResponse);
	}

	@PutMapping("/updata")
	public ResponseEntity<ApiResponse<String>> updataUser(@RequestBody String password, HttpSession session) throws Exception {
		String email = (String) session.getAttribute("email");;
		if(email==null) {
			ApiResponse apiResponse = new ApiResponse<>(false, "qurey", email);

			return ResponseEntity.ok(apiResponse);
		}
		String hashResult=hashCode.hashcode(password.replace("\"",""));
	
		String[] hr = hashResult.split(" ");
		int respones = userservice.updateUser(hr[0],hr[1],email);

		ApiResponse apiResponse = new ApiResponse<>(true, "qurey", email);
        
		return ResponseEntity.ok(apiResponse);
	};

	@PutMapping("/like/{gender}")
	public ResponseEntity<ApiResponse<Likenews>> updateLike(@RequestBody Likenews user , @PathVariable("gender") char gender) {
		boolean update = userservice.addLike(user.getUserId(), user.getCategoryId(), user.getNewsId(), gender);
		ApiResponse apiResponse = new ApiResponse(true, "qurey", update);
		return ResponseEntity.ok(apiResponse);
	}
	
	@PostMapping("/likebut")
	public ResponseEntity<ApiResponse<Likenews>> getLike(@RequestBody Likenews user) {
		boolean update = userservice.getLike(user.getUserId(), user.getCategoryId(), user.getNewsId());
		ApiResponse apiResponse = new ApiResponse(true, "qurey", update);
		return ResponseEntity.ok(apiResponse);
	}
	
	
	

	@GetMapping("/like/{id}")
	public ResponseEntity<ApiResponse<List<postList>>> findAllLike(@PathVariable("id") Integer id) {
		String response = userservice.findAllLike(id);
		if (response.length() >= 1) {
			String[] a = response.split(",");
			List<postList> postLists = new ArrayList<>();
			for (int i = a.length - 1; i >= 0; i--) {
				String[] Split = a[i].split("-");
				postLists.add(postListService.getPostList(newsName(Split[0]), Split[1]));

			}
			ApiResponse apiResponse = new ApiResponse<>(true, "qurey", postLists);

			return ResponseEntity.ok(apiResponse);
		}
		ApiResponse apiResponse = new ApiResponse<>(true, "false", "false");

		return ResponseEntity.ok(apiResponse);
	}

	@PutMapping("/record")
	public ResponseEntity<ApiResponse<Likenews>> updaterecord(@RequestBody Record user) {

		boolean update = userservice.addRecord(user.getUserId(), user.getCategoryId(), user.getNewsId(),
				user.getDate());

		ApiResponse apiResponse = new ApiResponse(true, "qurey", update);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/record/{id}")
	public ResponseEntity<ApiResponse<List<postList>>> findAllRecord(@PathVariable("id") Integer id) {
		String response = userservice.findAllRecord(id);
		
		String[] day = response.split("@");

		List<postList> list = new ArrayList<>();

		for (int i = day.length - 1; i >= 1; i--) {
			String[] Split = day[i].split(",");
			for (int j = Split.length - 1; j >= 1; j--) {
				String[] data = Split[j].split("-");
				if(data[0].equals("null")||data[1].equals("null")) {
					userservice.removeRecord(id, Split[j]+",");
				}else {
					postList postList = postListService.getPostList(newsName(data[0]), data[1]);
					postList.setRecord(Split[0]);
					list.add(postList);
				}
			}
		}
		;
		ApiResponse apiResponse = new ApiResponse<>(true, "qurey", list);

		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/pass")
	public ResponseEntity<ApiResponse<backuser>> hashcode(@RequestBody passwordsalt password, HttpSession session) {

		backuser login = userservice.login(password);
		if (login != null) {
			ApiResponse apiResponse = new ApiResponse(true, "query", login);
			return ResponseEntity.ok(apiResponse);
		} else {
			ApiResponse apiResponse = new ApiResponse(false, "fill", null);
			return ResponseEntity.ok(apiResponse);
		}
	}
    @GetMapping("/captcha")
	public void getcaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	captchaService.generateCaptcha(request, response);
    };
    @PostMapping("/validateCaptcha")
    public boolean validateCaptcha(@RequestBody String captcha, HttpServletRequest request) {
        return captchaService.validateCaptcha(captcha, request);
    };
	@PutMapping("/follow")
	public boolean putfollow(@RequestBody Likenews data) {
		
		boolean respones=userservice.follow( data.getUserId(),data.getCategoryId());
		return respones;
	}
	@GetMapping("/follow/{category}/{id}")
	public boolean getfollow(@PathVariable("category") String category,@PathVariable("id") String id) {
		boolean respones=userservice.getfollow(category,id);
		
		return respones;
	};
    
    
	
	@GetMapping("/forget/{email}")
	public ResponseEntity<ApiResponse<String>> forgetpassword(@PathVariable("email") String email, HttpSession session)
			throws MessagingException {

		boolean response = userservice.checkUseremail(email);
		if (!response) {
			ApiResponse apiResponse = new ApiResponse<>(false, "query", response);
			return ResponseEntity.ok(apiResponse);
		}
		emailService.sendEmail(email, "忘記密碼", "http://localhost:8082/user/updatauser.html");
		session.setAttribute("email", email);
		ApiResponse apiResponse = new ApiResponse<>(true, "query", email);

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/email/{email}")
	private ResponseEntity<ApiResponse<String>> email(@PathVariable("email") String Email) throws MessagingException {
		String text = "";
		Random random = new Random();
		for (int i = 0; i < 4; i++) {

			int a = random.nextInt(10);
			text = text + a;
		}
		emailService.sendEmail(Email, "註冊測試", text);
		ApiResponse apiResponse = new ApiResponse<>(true, "pass", text);
		return ResponseEntity.ok(apiResponse);
	}

	public String newsName(String id) {

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
