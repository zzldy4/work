package cn.ccsu.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class UserUpdateVO implements Serializable {
    private Integer id;

    private String name;

    private String userId;//用户唯一标识

    private String image;//头像

    private String phone;

    private String sex;//性别

    private String signature;//签名

    private Integer status;

    private Integer xp;//经验

    private Integer level;//等级

    private String province;//省份

    private String birthdate;//生日

}
