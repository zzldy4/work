package cn.ccsu.controller.common;

import cn.ccsu.constant.MessageConstant;
import cn.ccsu.dto.UserUpdateDTO;
import cn.ccsu.dto.UserUpdatePasswordDTO;
import cn.ccsu.entity.SmsCode;
import cn.ccsu.entity.User;
import cn.ccsu.mapper.UserMapper;
import cn.ccsu.result.Result;
import cn.ccsu.service.SmsCodeService;
import cn.ccsu.service.UserService;
import cn.ccsu.utils.AliOssUtil;
import cn.ccsu.vo.UserLoginVO;
import cn.ccsu.vo.UserUpdateVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequestMapping("/common")
@RestController
@Slf4j
@Api(tags = "通用接口")
public class CommonController {

    @Autowired
    private SmsCodeService  smsCodeService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 获取短信验证码
     * @param tele
     * @return
     */
    @GetMapping("/getSmsCode")
    @ApiOperation("获取短信验证码")
    public Result<String> getCode(String tele){
        String code = smsCodeService.sendCodeToSms(tele);
        log.info(tele);
        log.info(code);
        return Result.success(code);
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传：{}",file);
        try{
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名的后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件名称
            String objectName = UUID.randomUUID().toString() + extension;

            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);//文件上传
            return Result.success(filePath);
        }catch (IOException e){
            log.error("文件上传失败：{}",e);
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id获取用户信息")
    public Result<UserUpdateVO> getId(@PathVariable int id){
        log.info("id");
        User user = userMapper.getById(id);
        if(user == null){
            return Result.error("用户不存在");
        }
        log.info(user.toString());
        UserUpdateVO userUpdateVO = UserUpdateVO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())

                .phone(user.getPhone())
                .birthdate(user.getBirthdate())
                .sex(user.getSex())
                .signature(user.getSignature())
                .province(user.getProvince())
                .build();
        return Result.success(userUpdateVO);
    }
    @PutMapping("/update")
    @ApiOperation("编辑资料")
    public Result<UserUpdateVO> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        log.info(userUpdateDTO.toString());
        userService.update(userUpdateDTO);
        User user = userMapper.getById(userUpdateDTO.getId());
        if(user == null){
            return Result.error("用户不存在");
        }
        log.info(user.toString());
        UserUpdateVO userUpdateVO = UserUpdateVO.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .name(user.getName())
                .image(user.getImage())
                .phone(user.getPhone())
                .birthdate(user.getBirthdate())
                .sex(user.getSex())
                .signature(user.getSignature())
                .province(user.getProvince())
                .build();
        return Result.success(userUpdateVO);
    }
    @PutMapping("/updatePassword")
    @ApiOperation("修改密码")
    Result updatePassword(@RequestBody UserUpdatePasswordDTO userUpdatePasswordDTO){
        SmsCode smsCode = new SmsCode();
        smsCode.setTele(userUpdatePasswordDTO.getTele());
        smsCode.setCode(userUpdatePasswordDTO.getCode());
        if(!smsCodeService.checkCode(smsCode)){
            return Result.error("手机号或验证码错误");
        }
        User user = userMapper.getById(userUpdatePasswordDTO.getId());
        String password = DigestUtils.md5DigestAsHex(userUpdatePasswordDTO.getPassword().getBytes());
        user.setPassword(password);
        log.info(user.toString());
        userMapper.update(user);
        return Result.success();
    }
}
