package com.example.demo.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.lang.annotation.Repeatable;
import java.lang.module.ResolutionException;
import java.nio.channels.Pipe;
import java.security.DrbgParameters.Reseed;
import java.sql.PseudoColumnUsage;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.po.BaseData;
import com.example.demo.model.po.Post;
import com.example.demo.model.po.Record;
import com.example.demo.model.po.postList;
import com.example.demo.model.po.view;
import com.example.demo.service.BaseDateService;
import com.example.demo.service.PostListService;
import com.example.demo.service.PostService;
import com.example.demo.util.ImageUtils;

import jakarta.servlet.http.HttpSession;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/news")
public class BaseDateController {

	@Autowired
	private BaseDateService baseDateService;
	@Autowired
	private PostListService postListService;
	private ImageUtils imageUtils;

	@GetMapping("/world")
	public ResponseEntity<ApiResponse<List<postList>>> findAllWorld() throws IOException {
		List<postList> postLists = postListService.findAllPostList("world");
		ApiResponse apiResponse = new ApiResponse<>(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/view/{name}")
	public ResponseEntity<ApiResponse<List<postList>>> findmostview(@PathVariable("name") String name)
			throws IOException {
		List<postList> postLists = postListService.findMostview(name);
		ApiResponse apiResponse = new ApiResponse<>(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/postview/{id}")
	public ResponseEntity<ApiResponse<List<view>>> findAllPostView(@PathVariable("id") Integer id) throws IOException {
		List<view> postLists = postListService.findAllPostView(id);
		ApiResponse apiResponse = new ApiResponse<>(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/world/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getWorld(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender, HttpSession session) {
		try {
			Post news = baseDateService.getWorldNews(id, gender);
			ApiResponse apiResponse = new ApiResponse(true, "123", news);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse<>(false, e.getMessage(), null);
			return ResponseEntity.ok(apiResponse);
		}
	}

	@GetMapping("/politics")
	public ResponseEntity<ApiResponse<List<postList>>> findAllPolitics() {
		List<postList> postLists = postListService.findAllPostList("politics");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/politics/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getpolitics(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getPoliticsNews(id, gender);
			ApiResponse apiResponse = new ApiResponse<>(true, "query", post);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);
		}
	}

	@GetMapping("/business")
	public ResponseEntity<ApiResponse<List<postList>>> findAllBusiness() {
		List<postList> postLists = postListService.findAllPostList("business");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/business/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getBusiness(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getBusinessNews(id, gender);
			ApiResponse apiResponse = new ApiResponse(true, "query", post);

			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);
		}
	}

	@GetMapping("/health")
	public ResponseEntity<ApiResponse<List<postList>>> findAllheath() {
		List<postList> postLists = postListService.findAllPostList("health");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);

		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/health/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> gethealth(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getHealthNews(id, gender);
			ApiResponse apiResponse = new ApiResponse<>(true, "query", post);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse<>(false, "error", e);
			return ResponseEntity.ok(apiResponse);
		}
	}

	@GetMapping("/entertainment")
	public ResponseEntity<ApiResponse<List<postList>>> findAllEntertainment() {
		List<postList> postLists = postListService.findAllPostList("entertainment");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);

	}

	@GetMapping("/entertainment/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getEntertainment(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getEntertainmentNews(id, gender);
			ApiResponse apiResponse = new ApiResponse(true, "query", post);
			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);

		}
	}

	@GetMapping("/travel")
	public ResponseEntity<ApiResponse<List<postList>>> findAllTravel() {
		List<postList> postLists = postListService.findAllPostList("travel");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/travel/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getTravel(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getTravelNews(id, gender);
			ApiResponse apiResponse = new ApiResponse<>(true, "query", post);
			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);

		}
	}

	@GetMapping("/sport")
	public ResponseEntity<ApiResponse<List<postList>>> findAllSport() {
		List<postList> postLists = postListService.findAllPostList("sport");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/sport/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getSport(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getSportNews(id, gender);
			ApiResponse apiResponse = new ApiResponse<>(true, "query", post);
			return ResponseEntity.ok(apiResponse);

		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);
		}
	}

	@GetMapping("/weather")
	public ResponseEntity<ApiResponse<List<postList>>> findAllWeather() {
		List<postList> postLists = postListService.findAllPostList("weather");
		ApiResponse apiResponse = new ApiResponse(true, "query", postLists);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/weather/{id}/{gender}")
	public ResponseEntity<ApiResponse<Post>> getWeather(@PathVariable("id") Integer id,
			@PathVariable("gender") char gender) {
		try {
			Post post = baseDateService.getWeatherNews(id, gender);
			ApiResponse apiResponse = new ApiResponse<>(true, "query", post);
			return ResponseEntity.ok(apiResponse);
		} catch (Exception e) {
			e.printStackTrace();
			ApiResponse apiResponse = new ApiResponse(false, "error", e);
			return ResponseEntity.ok(apiResponse);
		}

	}

	@GetMapping("/writer/{name}")
	public ResponseEntity<ApiResponse<List<Post>>> FindAllwriter(@PathVariable("name") Integer name) {
		List<postList> postList = postListService.findAllwriternews(name);
		ApiResponse apiResponse = new ApiResponse<>(true, "query", postList);

		return ResponseEntity.ok(apiResponse);
	};

	@PostMapping("/random")
	public ResponseEntity<ApiResponse<List<postList>>> randomnews(@RequestBody Record data) {
		List<postList> respones = postListService.getrandomPost(data.getCategoryId(), data.getNewsId());
		ApiResponse apiResponse = new ApiResponse<>(true, "query", respones);
		return ResponseEntity.ok(apiResponse);
	};

	@GetMapping("/basedata")
	public ResponseEntity<ApiResponse<List<BaseData>>> findAllBasedata() {
		List<BaseData> baseDatas = baseDateService.findAllBaseDate();
		ApiResponse apiResponse = new ApiResponse<>(true, "query", baseDatas);
		return ResponseEntity.ok(apiResponse);
	}

	@GetMapping("/breakingnews")
	public ResponseEntity<ApiResponse<List<Post>>> findbreakingnews() {
		List<Post> postList = postListService.findbreakingnews();

		ApiResponse apiResponse = new ApiResponse<>(true, "query", postList);
		return ResponseEntity.ok(apiResponse);
	}


}
