package cn.ccsu.controller.user;

import cn.ccsu.constant.JwtClaimsConstant;
import cn.ccsu.constant.MessageConstant;
import cn.ccsu.dto.UserDTO;
import cn.ccsu.dto.UserLoginDTO;
import cn.ccsu.dto.UserUpdateDTO;
import cn.ccsu.dto.UserUpdatePasswordDTO;
import cn.ccsu.entity.SmsCode;
import cn.ccsu.entity.User;
import cn.ccsu.exception.AccountNotFoundException;
import cn.ccsu.mapper.UserMapper;
import cn.ccsu.properties.JwtProperties;
import cn.ccsu.result.Result;
import cn.ccsu.service.SmsCodeService;
import cn.ccsu.service.UserService;
import cn.ccsu.utils.JwtUtil;
import cn.ccsu.vo.UserLoginVO;
import cn.ccsu.vo.UserUpdateVO;
import cn.ccsu.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    @ApiOperation("用户登录")
    Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
        String userId = userLoginDTO.getUserId();
        String password = userLoginDTO.getPassword();
        User user = userMapper.getByUserId(userId);
        //找不到账号报账号异常
        if(user == null){
            return Result.error("账号不存在");
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());//md5加密
        //校验密码是否正确
        if(!password.equals(user.getPassword())){
            log.info(password);
            return Result.error("密码错误");
        }
        //检测是否被封号
        if(user.getStatus() == 0){
            Result.error("账号被锁定");//账号被锁定
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .image(user.getImage())
                .signature(user.getSignature())
                .token(token)
                .build();
        return Result.success(userLoginVO);
    }

    @PostMapping("/save")
    @ApiOperation("账号注册")
    Result<UserVO> save(@RequestBody UserDTO userDTO) {
        UserVO userVO = userService.save(userDTO);
        return Result.success(userVO);
    }
    @PostMapping("/checkCode")
    @ApiOperation("手机验证码登录")
    Result<UserLoginVO> checkCode(@RequestBody SmsCode smsCode){
        log.info(smsCode.getCode());

        if(!smsCodeService.checkCode(smsCode)){
            return Result.error("手机号或验证码错误");
        }
        else {
            User user = userService.checkCode(smsCode);
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.EMP_ID, user.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getUserSecretKey(),
                    jwtProperties.getUserTtl(),
                    claims);
            UserLoginVO userLoginVO = UserLoginVO.builder()
                    .id(user.getId())
                    .userId(user.getUserId())
                    .userName(user.getName())
                    .image(user.getImage())
                    .phone(user.getPhone())
                    .birthdate(user.getBirthdate())
                    .sex(user.getSex())
                    .signature(user.getSignature())
                    .province(user.getProvince())
                    .token(token)
                    .build();
            return Result.success(userLoginVO);
        }
    }





}
