package com.kh.myproduct.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j //log.으로 서버에 출력 가능함
@Repository //어노테션들은 스프링 프레임 워크가 구동될 때 자동으로 객체를 만들어준다.
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

  private final NamedParameterJdbcTemplate template;
  //NamedParameterJdbcTemplate 으로 데이터베이스를 연동할 수 있다.

  /**
   * 상품 등록
   *
   * @param product 상품(상품아이디, 상품명, 수량, 가격)
   * @return 상품아이디
   */
  @Override
  public Long save(Product product) {
    StringBuffer sb = new StringBuffer(); //
    sb.append("insert into product(product_id, pname, quantity, price) ");
    sb.append(" values(product_product_id_seq.nextval, :pname, :quantity, :price) "); //문자열 마지막 부분은 항상 한칸 띄어쓰기, 이유는 sql에 넘어가서 붙여 입력되기 때문에 문법오류가 발생한다.

    SqlParameterSource param = new BeanPropertySqlParameterSource(product);
    //product에 있는 값을 getter 메소드로 읽는다.
    //Bean이라는 명은 스프링 프레임워크에서 관리하는 자바객체이다.
    KeyHolder keyHolder = new GeneratedKeyHolder();
    template.update(sb.toString(), param, keyHolder, new String[]{"product_id"});
//    template.update(sb.toString(), param, keyHolder, new String[]{"product_id", pname});
    //sb.toString() -> sb를 문자열로 변경해줌.
    long productId = keyHolder.getKey().longValue(); //상품아이디

    return productId;
  }

  /**
   * 상품 조회
   *
   * @param productId 상품아이디
   * @return 상품
   */
  @Override
  public Optional<Product> findById(Long productId) {
    //Optional<타입명>로 감싸면 있을 수 있고 없을 수도 있고
    StringBuffer sb = new StringBuffer();
    sb.append("select product_id, pname, quantity, price ");
    sb.append(" from product ");
    sb.append(" where product_id = :id ");

    try {
      Map<String, Long> param = Map.of("id", productId);
      Product product = template.queryForObject(sb.toString(), param, productRowMapper());

      return Optional.of(product);

      //queryForObject -> 레코드가 하나일 때 사용한다. 즉, Map.of("id", productId)와 같이 하나일 때 사용함.
    } catch (EmptyResultDataAccessException e) {
      //조회 결과가 없는 경우
      return Optional.empty();
    }
  }

  /**
   * 상품 수정
   *
   * @param productId 상품아이디
   * @param product   수정할 상품
   * @return 수정된 레코드 수
   */
  @Override
  public int update(Long productId, Product product) {
    StringBuffer sb = new StringBuffer();

    sb.append("update product ");
    sb.append(" set pname = :pname, ");
    sb.append(" quantity = :quantity, ");
    sb.append(" price = :price ");
    sb.append(" where product_id = :id ");

    SqlParameterSource param = new MapSqlParameterSource()
        .addValue("pname", product.getPname())
        .addValue("quantity", product.getQuantity())
        .addValue("price", product.getPrice())
        .addValue("id", productId);

    return template.update(sb.toString(),param);
  }

  /**
   * 상품 삭제
   *
   * @param productId 상품아이디
   * @return 삭제된 레코드 수
   */
  @Override
  public int delete(Long productId) {
    String sql = "delete from product where product_id = :id ";


    return template.update(sql, Map.of("id", productId));
  }

  /**
   * 상품 목록
   *
   * @return 상품목록
   */
  @Override
  public List<Product> findAll() {
    StringBuffer sb = new StringBuffer();
    sb.append("select product_id, pname, quantity, price ");
    sb.append(" from product ");

    List<Product> list = template.query(
        sb.toString(),
        BeanPropertyRowMapper.newInstance(Product.class));
      //자바객체로 맵핑을 해준다.
      //레코드 컬럼과 자바객체 멤버필드가 동일한 이름일 경우 맵핑을 해준다. 캐멀케이스 지원
    return list;
  }

//  RowMapper<Product> rowMapper = new RowMapper<Product>() {
//    @Override
//    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setQuantity(rs.getLong("quantity"));
//      product.setPrice(rs.getLong("price"));
//      return product;
//    }
//  };
//
//  //람다식
//  RowMapper<Product> rowMapper2 = (rs, rowNum) -> {
//      Product product = new Product();
//      product.setProductId(rs.getLong("product_id"));
//      product.setPname(rs.getString("pname"));
//      product.setQuantity(rs.getLong("quantity"));
//      product.setPrice(rs.getLong("price"));
//      return product;
//  };


  //수동 매핑
  private RowMapper<Product> productRowMapper() {
    return (rs, rowNum) -> {
      Product product = new Product();
      product.setProductId(rs.getLong("product_id"));
      product.setPname(rs.getString("pname"));
      product.setQuantity(rs.getLong("quantity"));
      product.setPrice(rs.getLong("price"));
      return product;
    };
  }

  //자동매핑 : 테이블의 컬럼명과 자바객체 타입의 맴버필드가 같아야 한다.
  // BeanPropertyRowMapper.newInstance(자바 객체 타입)
}
