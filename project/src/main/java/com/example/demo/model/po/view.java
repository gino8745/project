package com.example.demo.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class view {
  Integer category_id;
  Integer LikeMale;
  Integer LikeFemale;
  Integer totalLike;
  Integer ViewMale;
  Integer ViewFemale;
  Integer totalView;
}
