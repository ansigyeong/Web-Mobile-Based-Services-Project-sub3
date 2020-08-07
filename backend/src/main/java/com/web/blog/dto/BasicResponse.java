package com.web.blog.dto;

import com.mysql.cj.protocol.Message;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

public class BasicResponse {
    @ApiModelProperty(value = "status", position = 1)
    public boolean status;
    @ApiModelProperty(value = "data", position = 2)
    public Object data;
    @ApiModelProperty(value = "object", position = 3)
    public Object object;
    @ApiModelProperty(value = "header" ,position = 4)
    public Object headers;

}
