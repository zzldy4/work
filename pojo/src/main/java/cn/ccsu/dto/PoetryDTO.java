package cn.ccsu.dto;

import lombok.Data;

@Data
//检索诗歌
public class PoetryDTO {

    private String dynasty;//朝代

    private String writer;//作者

    private String type;//类型

    private String form;//形式

}
