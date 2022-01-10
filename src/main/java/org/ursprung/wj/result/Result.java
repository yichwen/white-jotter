package org.ursprung.wj.result;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {

    //响应码
    private int code;
    private String message;
    private Object result;

}
