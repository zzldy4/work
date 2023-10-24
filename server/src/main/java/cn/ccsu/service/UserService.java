package cn.ccsu.service;

import cn.ccsu.dto.UserDTO;
import cn.ccsu.dto.UserLoginDTO;
import cn.ccsu.dto.UserUpdateDTO;
import cn.ccsu.entity.SmsCode;
import cn.ccsu.entity.User;
import cn.ccsu.result.Result;
import cn.ccsu.vo.UserUpdateVO;
import cn.ccsu.vo.UserVO;
import org.springframework.stereotype.Service;

public interface UserService {

    User login(UserLoginDTO userLoginDTO);

    UserVO save(UserDTO userDTO);

    User checkCode(SmsCode smsCode);

    void update(UserUpdateDTO userUpdateDTO);

}
