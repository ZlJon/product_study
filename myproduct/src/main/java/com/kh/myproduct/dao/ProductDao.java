package com.kh.myproduct.dao;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
  /**
   상품 등록
   * @param product 상품(상품아이디, 상품명, 수량, 가격)
   * @return 상품아이디
   */
  Long save(Product product);

  /**
   * 상품 조회
   *
   * @param productId 상품아이디
   * @return 상품
   */
  Optional<Product> findById(Long productId);

  /**
  상품 수정
   * @param productId 상품아이디
   * @param product 수정할 상품
   * @return 수정된 레코드 수
   */
  int update(Long productId, Product product);

  /**
  상품 삭제
   *@param productId 상품아이디
   *@return  삭제된 레코드 수
   */
  int delete(Long productId);
  /**
   * 상품 목록
   * @return 상품목록
   * */
  List<Product> findAll();
}
