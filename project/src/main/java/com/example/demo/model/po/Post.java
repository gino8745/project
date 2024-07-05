package com.example.demo.model.po;

import java.util.Date;

import javax.swing.text.View;

import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
   //category_id, image_1, title, post_1, image_2, post_2, image_3, post_3, image_4
   private Integer categoryId;
   private String writer;
   private String writerId;
   private Integer newsId;
   private String title;
   private String image_1;
   private String post_1;
   private String image_2;
   private String post_2;
   private String image_3;
   private String post_3;
   private Date create_time;
   private Integer view;

}
