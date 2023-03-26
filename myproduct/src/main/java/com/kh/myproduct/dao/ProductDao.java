package com.kh.myproduct.dao;

import java.util.List;
import java.util.Optional;
//DAO의 껍데기 -> product 클래스를 타입을 활용하여 입력받을 수 있는 매개값을 제한
//예: save(Product product) => product: 프로덕트의 내용을 다 받는 다는 것
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
   * 전체 삭제
   * @return 삭제된 레코드 수
   * */
  int deleteAll();
  /**
   * 상품 목록
   * @return 상품목록
   * */
  List<Product> findAll();

  /**
   * 상품존재유무
   */
  boolean isExist(Long productId);

  /**
   * 등록된 상품 수
   */
  int countOfRecord();
}

