package cn.ccsu.service;


import cn.ccsu.entity.SmsCode;

public interface SmsCodeService {

    public String sendCodeToSms(String tele);

    public Boolean checkCode(SmsCode SmsCode);

}
