package com.kh.myproduct.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//해당 클래스는 데이터베이스의 테이블 안의 변수를 의미
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
// 엔터티 클래스 -> 자바와 데이터베이스가 매칭되어 있는 경우
@AllArgsConstructor //모든 멤버필드를 매개변수로 하는 생성자 생성
@NoArgsConstructor //디폴트 생성자
public class Product {
  //엔터티 테이블 -> sql 테이블과 매칭될 수 있게 정의
  private Long productId; //PRODUCT_ID(10,0)
  private String pname; //PNAME	VARCHAR2(30 BYTE)
  private Long quantity; //QUANTITY	NUMBER(10,0)
  private Long price; //PRICE	NUMBER(10,0)
}
