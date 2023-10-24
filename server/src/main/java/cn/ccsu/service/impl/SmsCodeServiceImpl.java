package cn.ccsu.service.impl;

import cn.ccsu.entity.SmsCode;
import cn.ccsu.service.SmsCodeService;
import cn.ccsu.utils.CodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SmsCodeServiceImpl implements SmsCodeService {

    @Autowired
    private CodeUtils codeUtils;

    @CachePut(value = "smsCode", key = "#tele")
    public String sendCodeToSms(String tele) {
        String code = codeUtils.generator(tele);
        return code;
    }

    public Boolean checkCode(SmsCode SmsCode) {
        String code = SmsCode.getCode();
        String cacheCode = codeUtils.get(SmsCode.getTele());
        log.info("cacheCode是：{}",cacheCode);
        log.info("code是：{}",code);
        return code.equals(cacheCode);
    }
}