package cn.ccsu.mapper;

import cn.ccsu.dto.UserDTO;
import cn.ccsu.entity.User;
import cn.ccsu.vo.UserUpdateVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    /**
     * 根据账号查询用户
     * @param userId
     * @return
     */
    @Select("select * from user where user_id = #{userId}")
    User getByUserId(String  userId);

    /**
     * 根据手机号查询用户
     * @param tele
     * @return
     */
    @Select("select * from user where phone = #{tele}")
    User getByPhone(String tele);

    /**
     * 注册，将数据存入用户表
     * @param user
     */
    @Insert("insert into user(name,user_id,image,password,phone,signature,status,xp,level,create_time,update_time) values(#{name},#{userId},#{image},#{password},#{phone},#{signature},#{status},#{xp},#{level},#{createTime},#{updateTime})")
    void insert(User user);

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(int id);

    /**
     * 修改用户信息
     * @param user
     */
    void update(User user);

}
