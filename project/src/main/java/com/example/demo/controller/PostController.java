package com.example.demo.controller;

import java.awt.Image;
import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.example.demo.model.ApiResponse;
import com.example.demo.model.po.Post;
import com.example.demo.service.PostService;
import com.example.demo.util.ImageUtils;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/news/post")
public class PostController {
     @Autowired
     private PostService postService;
     
     
     @PostMapping
     public ResponseEntity<ApiResponse<Post>> addPost(@RequestBody Post post ) throws IOException{
 
    	 Integer postid=postService.addPost(post);
    	 if(postid != null) {
    		 ApiResponse apiResponse=new ApiResponse(true,"add succes",post);
    		 return ResponseEntity.ok(apiResponse);
    	 }
    	 ApiResponse apiResponse=new ApiResponse(false,"add fail",post);
		 return ResponseEntity.ok(apiResponse); 	 
     }
   
     
     @PutMapping("/update/{id}")
     public ResponseEntity<ApiResponse<Post>> updatePost(@PathVariable("id") Integer id,@RequestBody Post post){

    	 Integer update=postService.updatePost(id, post);
         
    	 ApiResponse apiResponse=new ApiResponse(true,"qurey",update);
    	 return ResponseEntity.ok(apiResponse);
    	 
     }
     
     @DeleteMapping("/delete/{id}/{newsid}")
     public ResponseEntity<ApiResponse<Post>> deletePost(@PathVariable("id") Integer id,@PathVariable("newsid") Integer newsid){
    	 Integer delete =postService.deletePost(id,newsid);
    	 ApiResponse apiResponse=new ApiResponse<>(true,"qurey",delete);
    	 return ResponseEntity.ok(apiResponse);
     }
     
     public byte[] getbyte(byte[] bs) throws IOException {
    	 return ImageUtils.compressImage(bs);
     }
     
     
     
     
     
}
