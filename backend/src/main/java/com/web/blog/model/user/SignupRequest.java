package com.web.blog.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.security.core.userdetails.UserDetails;

@Valid //유효성체크
@ToString
@Getter
@Setter
@NoArgsConstructor
public class SignupRequest {

    @ApiModelProperty(required = true)
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다. ex) abc@abc.com")
    private String email;
   
    @ApiModelProperty(required = true)
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d$@$!%*#?&]{8,}$",
                        message = "영문/숫자/특수기호 포함 8자 이상이여야 합니다.")  //최소8자리에 최소하나의 문자 및 하나의 숫자 및 하나의 특수문자 
    private String pw;
    
    private int userNo;
    private String name;
    private String lang;
    private int grade;
    private boolean enable;
    private String role;
    private String authKey;
    private int authStatus;
    private Date createDate;
     
}
