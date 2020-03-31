package com.github.wephotos.bughub.entity.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 类描述
 *
 * @author xcloading
 */
@Getter
@Setter
@ToString
public class ModifyPasswordDto {
    /**
     * 原始密码
     */
    private String oldPassword;
    /**
     * 新密码
     */
    private String password;
    /**
     * 确认密码
     */
    private String confirmPwd;
}
