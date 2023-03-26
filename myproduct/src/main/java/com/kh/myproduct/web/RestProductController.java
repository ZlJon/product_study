package com.kh.myproduct.web;

import com.kh.myproduct.dao.Product;
import com.kh.myproduct.svc.ProductSvc;
import com.kh.myproduct.web.rest.SaveRest;
import com.kh.myproduct.web.rest.UpdateRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController //@Controller + @ResponseBody
//@Controller
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class RestProductController {

  private final ProductSvc productSvc;

  //상품등록
//  @ResponseBody
  @PostMapping
  public RestResponse<Object> save(@RequestBody SaveRest saveRest) {
    log.info("saveRest={}", saveRest);

    //데이터 검증

    //등록
    Product product = new Product();
    product.setPname(saveRest.getPname());
    product.setQuantity(saveRest.getQuantity());
    product.setPrice(saveRest.getPrice());

    Long productId = productSvc.save(product);
    product.setProductId(productId);
    RestResponse<Object> res = null;

    if (productId > 0) {
      res = RestResponse.createRestResponse("00", "성공", product);

    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

  //상품조회
//  @ResponseBody
  @GetMapping("/{id}")
  public RestResponse<Optional> findById(@PathVariable("id") Long productId) {
//    Optional<타입> 리스트와 같은 객체를 담을 수있는 ; 있거나 없거나
    RestResponse<Optional> res = null;

    //1)상품 존재 유무 판단
    if (!productSvc.isExist(productId)) {
      res = RestResponse.createRestResponse("01", "해당하는 상품이 없습니다.", null);
      return res;
    }

    //2)조회
    Optional<Product> findedProduct = productSvc.findById(productId);
    res = RestResponse.createRestResponse("00", "성공", findedProduct);
//    Product product = findedProduct.orElseThrow();
    //        new RestResponse<>(new RestResponse.Header("00","성공"),findedProduct);
//    restResponse.setHeader(new RestResponse.Header());

//    return RestResponse.createRestResponse("00", "성공", findedProduct);
    return res;
  }
  //상품수정
  @PatchMapping("/{id}")
  public RestResponse<Object> update(
      @PathVariable("id") Long productId,
      @RequestBody UpdateRest updateRest
      ) {
    RestResponse<Object> res = null;

    //검증


    //1)상품 존재 유무 판단
    if (!productSvc.isExist(productId)) {
      res = RestResponse.createRestResponse("01", "해당하는 상품이 없습니다.", null);
      return res;
    }


    //2)수정
    Product product = new Product();
    product.setPname(updateRest.getPname());
    product.setQuantity(updateRest.getQuantity());
    product.setPrice(updateRest.getPrice());

    int updatedRowCnt = productSvc.update(productId, product);
    updateRest.setProductId(productId);
    if (updatedRowCnt == 1) {
      res = RestResponse.createRestResponse("00", "성공", product);
    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

//
//  //상품삭제
  @DeleteMapping("/{id}")
  public RestResponse<Object> delete(@PathVariable("id") Long productId) {
    RestResponse<Object> res = null;

    //1)상품 존재 유무 판단
    if (!productSvc.isExist(productId)) {
      res = RestResponse.createRestResponse("01", "찾고자하는 상품이 없습니다.", null);
      return res;
    }

    //2)상품 삭제
    int deleteRowCnt = productSvc.delete(productId);
    if (deleteRowCnt == 1) {
      res = RestResponse.createRestResponse("00", "성공", null);
    } else {
      res = RestResponse.createRestResponse("99", "실패", "서버오류");
    }
    return res;
  }

//
  //상품목록
//  @ResponseBody
  @GetMapping
  public RestResponse<Object> findAll() {
//    List<Product> list = productSvc.findAll();
//Set => [{},{},{}]
//    Set<Product> set = new HashSet<>();
//    list.stream().forEach(product -> set.add(product));
//Map => {{},{},{}}
//    Map<Long, Product> map = new LinkedHashMap<>();
//    list.stream().forEach(product -> map.put(product.getProductId(),product));

    RestResponse<Object> res = null;
    List<Product> list = productSvc.findAll();
    if (list.size() > 0) {
      res = RestResponse.createRestResponse("00", "성공", list);

    } else {
      res = RestResponse.createRestResponse("01", "상품이 없습니다.", list);

    }
//    return RestResponse.createRestResponse("00", "성공", productSvc.findAll());
    return res;
  }

}
