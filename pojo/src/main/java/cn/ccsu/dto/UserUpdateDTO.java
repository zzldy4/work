package cn.ccsu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserUpdateDTO implements Serializable {
    private Integer id;

    private String name;

    private String userId;//用户唯一标识

    private String phone;

    private String sex;//性别

    private String signature;//签名

    private String province;//省份

    private String birthdate;//生日

}
