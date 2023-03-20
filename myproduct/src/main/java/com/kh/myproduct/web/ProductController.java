package com.kh.myproduct.web;

import com.kh.myproduct.dao.Product;
import com.kh.myproduct.svc.ProductSvc;
import com.kh.myproduct.web.form.DetailForm;
import com.kh.myproduct.web.form.SaveForm;
import com.kh.myproduct.web.form.UpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor //맴버필드 중에 파이널 키워드가 붙은 필드값의 생성자를 만들어주는 어노테이션, 즉 final 멤버 필드를 매개값으로 하는 생성자를 자동 생성
public class ProductController {

  private final ProductSvc productSvc;

  //원래는 이런식으로 생성자를 만들어야 한다. 그러나 @RequiredArgsConstructor 를 사용하면 아래와 같은 생성자를 만들어준다.
//  public ProductController(ProductSvc productSvc) {
//    this.productSvc = productSvc;
//  }

  //등록양식
  @GetMapping("/add")
  public String saveForm() {

    return "product/saveForm";
    //랜더링할 뷰
  }

  //등록처리
  @PostMapping("/add")
  public String save(
//      @Param("pname") String pname,
//      @Param("quantity")Long quantity,
//      @Param("price") Long price
//      위와 같이도 작성이 가능하나 아래와 같이 간결하게 작성가능함.
      @ModelAttribute SaveForm saveForm,
      RedirectAttributes redirectAttributes
      ) {
//    log.info("pname={}, quantity={}, price={}", pname, quantity, price);
      log.info("saveForm={}",saveForm);
    //데이터 검증
    //등록
    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setQuantity(saveForm.getQuantity());
    product.setPrice(saveForm.getPrice());

    Long savedProductId = productSvc.save(product);
    redirectAttributes.addAttribute("id", savedProductId);
    return "redirect:/products/{id}/detail";
    //redirect: 경로 => 서버에 경로에 있는 정보를 다시 요청하는 것 따라서 두 번 요청하는 것과 같다. POST/Redirect/GET 패턴
  }

  //상품조회
  @GetMapping("/{id}/detail")
  public String findById(
      @PathVariable("id") Long id,
      Model model
  ) {
    Optional<Product> findedProduct = productSvc.findById(id);
    Product product = findedProduct.orElseThrow();

    DetailForm detailForm = new DetailForm();
    detailForm.setProductId(product.getProductId());
    detailForm.setPname(product.getPname());
    detailForm.setQuantity(product.getQuantity());
    detailForm.setPrice(product.getPrice());

    model.addAttribute("form", detailForm); //데이터를 전달할 때 model 객체에 담아서 전달한다는 의미
    return "product/detailForm";
  }

  //상품수정 양식
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") long id,
      Model model
  ) {
    Optional<Product> findedProduct = productSvc.findById(id);
    Product product = findedProduct.orElseThrow();

    UpdateForm updateForm = new UpdateForm();
    updateForm.setProductId(product.getProductId());
    updateForm.setPname(product.getPname());
    updateForm.setQuantity(product.getQuantity());
    updateForm.setPrice(product.getPrice());

    model.addAttribute("form", updateForm);
    return "product/updateForm";
  }

  //상품수정 처리
  @PostMapping("/{id}/edit")
  public String update(
      @PathVariable("id") Long productId,
      @ModelAttribute("form") UpdateForm updateForm,
      RedirectAttributes redirectAttributes
  ) {
    //데이터 검증
    Product product = new Product();
    product.setProductId(productId);
    product.setPname(updateForm.getPname());
    product.setQuantity(updateForm.getQuantity());
    product.setPrice(updateForm.getPrice());

    productSvc.update(productId, product);

    redirectAttributes.addAttribute("id", productId);
    return "redirect:/products/{id}/detail";
  }

  //상품 삭제
  @GetMapping("/{id}/del")
  public String deleteById(@PathVariable("id") Long productId) {

    productSvc.delete(productId);

    return "redirect:/products";
  }

  //상품 목록
  @GetMapping
  //RequestMapping 이 프로덕트로 되어 있기 때문에 비워두어도 됨.
  public String findAll(Model model) {

    List<Product> products = productSvc.findAll();
    model.addAttribute("products", products);

    return "product/all";
  }
}
