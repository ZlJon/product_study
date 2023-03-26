package com.kh.myproduct.dao;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@SpringBootTest //테스트 환경에서 스프링부트가 실행됨.
public class ProductDaoImplTest {

  @Autowired //기존에 만들어진 객체를 인스턴스 생성할 필요없이 자동으로 넣어준다. 필드 값을 주입받는다.
  ProductDao productDao;

  //등록
  @Test
  //test 대상이 되게 만드는 어노테이션 또한, 어노테이션이 선언되면 각각 별도의 환경에서 실행된다.
  @DisplayName("상품등록")
  //테스트 이름 설정 가능함
  void save() {
    Product product = new Product();
    product.setPname("복사기");
    product.setQuantity(10L);
    product.setPrice(1000000L);

    Long productId = productDao.save(product);
    log.info("productId={}", productId); //log를 사용한다는 것은 눈으로 보고 한다는 것
    Assertions.assertThat(productId).isGreaterThan(0L); //정확하게 체크하려고 한다면 위와 같이 프로그램화해서 체크한다.
  }

  //조회
  @Test
  @DisplayName("상품조회")
  //테스트 이름 설정 가능함
  void findById() {
    Long productId = 65L;
    Optional<Product> product = productDao.findById(productId);
//    if (product.isPresent()) {
//      log.info("product={}", product.get());
//    } else {
//      log.info("조회한 결과 없음");
//    }
//    Assertions.assertThat(product.stream().count()).isEqualTo(1);
    Product findedProduct = product.orElseThrow(); //없으면 java.util.NoSuchElementException: No value present 있으면 아래 선언문이 출력됨
    Assertions.assertThat(findedProduct.getPname()).isEqualTo("복사기");
    Assertions.assertThat(findedProduct.getQuantity()).isEqualTo(10L);
    Assertions.assertThat(findedProduct.getPrice()).isEqualTo(1000000L);
  }

  //수정
  @Test
  @DisplayName("상품수정")
  //테스트 이름 설정 가능함
  void update() {
    Long productId = 64L;
    Product product = new Product();
    product.setPname("복사기_TX");
    product.setQuantity(20L);
    product.setPrice(2000000L);
    int updateRowCount = productDao.update(productId, product);
    Optional<Product> findedProduct = productDao.findById(productId);

    Assertions.assertThat(updateRowCount).isEqualTo(1);
    Assertions.assertThat(findedProduct.get().getPname()).isEqualTo(product.getPname());
    Assertions.assertThat(findedProduct.get().getQuantity()).isEqualTo(product.getQuantity());
    Assertions.assertThat(findedProduct.get().getPrice()).isEqualTo(product.getPrice());
  }

  //삭제
  @Test
  @DisplayName("상품삭제")
  //테스트 이름 설정 가능함
  void delete() {
    Long productId = 66L;
    int deletedRowCount = productDao.delete(productId);
    Optional<Product> findedProduct = productDao.findById(productId);
//    Product product = findedProduct.orElseThrow();
    // case 1)
//    Assertions.assertThat(findedProduct.ofNullable("없음").orElseThrow()).isNotEqualTo("없음");
    // case 2)
    Assertions.assertThatThrownBy(() -> findedProduct.orElseThrow()).isInstanceOf(NoSuchElementException.class); //NoSuchElementException.class 가 발생하지 않아야 ture 로 본다.
  }

  //목록
  @Test
  @DisplayName("상품목록")
  void findAll() {
    List<Product> list = productDao.findAll();
    //case 1)
//    for (Product product : list) {
//      log.info("product={}", product);
//    }

    //case 2)
//    list.stream().forEach(product -> log.info("product={}", product));

    Assertions.assertThat(list.size()).isGreaterThan(0);
  }

  @Test
  @DisplayName("상품존재 유")
  void isExist() {
    Long productId = 345L;
    boolean exist = productDao.isExist(productId);
    Assertions.assertThat(exist).isTrue();
  }

  @Test
  @DisplayName("상품존재 무")
  void isExist2() {
    Long productId = 0L;
    boolean exist = productDao.isExist(productId);
    Assertions.assertThat(exist).isTrue();
  }

  @Test
  @DisplayName("전체 삭제")
  void deleteAll() {
    int deletedRows = productDao.deleteAll();
    int countOfRecord = productDao.countOfRecord();
    Assertions.assertThat(countOfRecord).isEqualTo(0);
  }

  @Test
  @DisplayName("레코드 건 수")
  void countOfRecord() {
    int countOfRecord = productDao.countOfRecord();
    log.info("countOfRecord={}",countOfRecord);
  }

}
