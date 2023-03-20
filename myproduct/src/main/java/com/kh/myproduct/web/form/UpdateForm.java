package com.kh.myproduct.web.form;

import lombok.Data;

@Data
public class UpdateForm {
  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;
}
//확장성을 고려해서 똑같지만 별도로 만들어둔다.
//화면 또한 다르기 때문에 생성한다.
