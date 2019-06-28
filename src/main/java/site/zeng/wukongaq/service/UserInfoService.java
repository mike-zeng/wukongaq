package site.zeng.wukongaq.service;

import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.UserInfoException;

import java.io.InputStream;

/**
 * @author zeng
 * @Classname UserInfoService
 * @Description TODO
 * @Date 2019/6/18 9:03
 */
public interface UserInfoService {
    /**
     * 通过id获取用户信息
     * @param id 用户id
     * @return 用户信息
     */
    UserInfo getUserInfoById(Integer id) throws UserInfoException;

    /**
     * 根据修改用户信息

     */
    void alterUserInfo(UserInfo userInfo)throws UserInfoException;

    /**
     * 修改用户头像
     */
    String alterHeadPhoto(Integer key,InputStream inputStream)throws UserInfoException;

}
