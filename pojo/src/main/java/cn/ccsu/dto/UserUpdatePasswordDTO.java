package cn.ccsu.dto;

import lombok.Data;

@Data
public class UserUpdatePasswordDTO {

    private int id;

    private String tele;

    private String code;

    private String password;

}
