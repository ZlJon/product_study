package com.kh.myproduct.web.form;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class SaveForm {
  @NotBlank //null, 빈문자열("")을 허용 안 함, 문자열 타입에만 사용
  @Size(min = 2, max = 10)
  private String pname;
  @NotNull //모든 타입에 대해 null 허용 안 함
  @Positive //양수면 통과
  @Max(1000) //최대값, 최대 1000개를 넘길 수 없다.
  private Long quantity;
  @NotNull //모든 타입에 대해 null 허용 안 함
  @Positive //양수면 통과
  @Min(1000) //최소값, 최소 1000개 미만이 될 수 없다.
  private Long price;

//  @NotEmpty =>  null, 빈문자열(""), 공백문자("") 허용 안 함,
//                컬랙션인 경우 요소가 1개이상 존재 문자열, 컬랙션타입에 사용
}
