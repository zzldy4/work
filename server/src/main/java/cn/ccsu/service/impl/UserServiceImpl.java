package cn.ccsu.service.impl;

import cn.ccsu.constant.MessageConstant;
import cn.ccsu.dto.UserDTO;
import cn.ccsu.dto.UserLoginDTO;
import cn.ccsu.dto.UserUpdateDTO;
import cn.ccsu.entity.SmsCode;
import cn.ccsu.entity.User;
import cn.ccsu.exception.AccountNotFoundException;
import cn.ccsu.mapper.UserMapper;
import cn.ccsu.result.Result;
import cn.ccsu.service.UserService;
import cn.ccsu.utils.UUIDUtil;
import cn.ccsu.vo.UserUpdateVO;
import cn.ccsu.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    public User login(UserLoginDTO userLoginDTO) {
        String userId = userLoginDTO.getUserId();
        String password = userLoginDTO.getPassword();

        User user = userMapper.getByUserId(userId);
        //找不到账号报账号异常
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        password = DigestUtils.md5DigestAsHex(password.getBytes());//md5加密
        //校验密码是否正确
        if(!password.equals(user.getPassword())){
            log.info(password);
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }
        //检测是否被封号
        if(user.getStatus() == 0){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_LOCKED);//账号被锁定
        }
        return user;
    }

    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    public UserVO save(UserDTO userDTO) {
        String userId = UUIDUtil.getUUID();
        User user = User.builder()
                .userId(userId)
                .image("https://web-tlias2003.oss-cn-hangzhou.aliyuncs.com/009fd0f8-4c17-4e92-9f10-313928620ae7.png")
                .name(userDTO.getName())
                .status(1)
                .signature("这个人很懒，什么也没留下")
                .password(DigestUtils.md5DigestAsHex(userDTO.getPassword().getBytes()))
                .xp(0)
                .level(0)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
        userMapper.insert(user);
        UserVO userVO =
                UserVO.builder()
                        .userId(userId)
                        .build();
        return userVO;
    }

    /**
     * 手机验证码登录
     * @param smsCode
     * @return
     */
    public User checkCode(SmsCode smsCode) {
        String tele = smsCode.getTele();

        User user = userMapper.getByPhone(tele);
        //找不到账号报账号异常
        if(user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //检测是否被封号
        if(user.getStatus() == 0){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_LOCKED);//账号被锁定
        }

        return user;
    }

    public void update(UserUpdateDTO userUpdateDTO){
        User user = User.builder()
                .id(userUpdateDTO.getId())
                .phone(userUpdateDTO.getPhone())
                .birthdate(userUpdateDTO.getBirthdate())
                .province(userUpdateDTO.getProvince())
                .sex(userUpdateDTO.getSex())
                .userId(userUpdateDTO.getUserId())
                .name(userUpdateDTO.getName())
                .signature(userUpdateDTO.getSignature())
                .updateTime(LocalDateTime.now())
                .build();
        log.info(user.toString());
        userMapper.update(user);
    }

}
