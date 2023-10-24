package cn.ccsu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class User implements Serializable {

    private Integer id;

    private String name;

    private String userId;//用户唯一标识

    private String image;//头像

    private String password;

    private String phone;

    private String signature;//签名

    private Integer status;

    private Integer xp;//经验

    private Integer level;//等级

    private String birthdate;//生日

    private String province;//省份

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private  LocalDateTime createTime;//创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间
    private String sex;//性别

}
