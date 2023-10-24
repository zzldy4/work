package cn.ccsu.utils;

import java.util.UUID;

public class UUIDUtil {
    public static String getUUID(){
        String id = UUID.randomUUID().toString();
        id = id.replaceAll("-", "");
        String uid = String.format("%-10s", id);
        return uid;
    }
}
