package com.kh.myproduct.web;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
//제너릭 타입; 사용하는 것에 따라 타입이 바뀐다. <>안에는 대문자 한 글자 넣는다(관례상 T = 타입, V = 변수)
public class RestResponse<T> {
  private Header header;
  private T data;

  @Data
  @AllArgsConstructor
  public static class Header {
    private String rtcd;
    private String rtmsg;
  }

  //  제너릭 메소드 =>리턴 타입 아무 종류 가능함.
  public static <T> RestResponse<T> createRestResponse(String rtcd, String rtmsg, T data) {
    return new RestResponse<>(new Header(rtcd, rtmsg), data);
  }
}
