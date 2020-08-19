package com.web.blog.config;

public enum SocailType {

    FACEBOOK("facebook"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String ROLE_PREFIX = "ROLE_";
    private String name;
     
    SocailType(String name){ 
        this.name = name; 
    }

    public String getRoleType() {
          return ROLE_PREFIX + name.toUpperCase(); 
    }
      
    public String getValue() {
         return name; 
    }


}