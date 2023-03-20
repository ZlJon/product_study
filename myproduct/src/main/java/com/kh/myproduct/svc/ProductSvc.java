package com.kh.myproduct.svc;

import com.kh.myproduct.dao.Product;

import java.util.List;
import java.util.Optional;

public interface ProductSvc { //type으로써 역활을 하는 곳; 껍데기와 같은 곳
  //상품 등록
  Long save(Product product);

  //상품 조회
  Optional<Product> findById(Long productId);

  //상품 수정
  int update(Long productId, Product product);

  //상품 삭제
  int delete(Long productId);
  //상품 목록
  List<Product> findAll();
}

