package cn.ccsu.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户登录返回的数据格式")
public class UserLoginVO {

    private Integer id;

    private String userName;

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

    private String token;//jwt令牌

}
