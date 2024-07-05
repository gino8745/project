package com.example.demo.model.po;

import java.util.Date;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class postList {
	 private Integer category_id;
     private Integer news_id;
	 private Date create_time;
     private String image_1;
     private String title;
     private String post_1;
     private String record;
     private Integer view;
}
