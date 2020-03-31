package com.github.wephotos.bughub.utils;

import lombok.Getter;

@Getter
public enum Errors {
    // 修改密码相关 1000开始
    OLD_PASSWORD_ERROR(1000,"原始密码不正确"),
    CONFIRM_PASSWORD_ERROR(1001,"两次密码输入不一致"),
    PASSWORD_ERROR(1002,"新密码不能与近期用过密码相同");


    private Integer code;
    private String message;

    Errors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
