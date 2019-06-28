package site.zeng.wukongaq.service;

import site.zeng.wukongaq.exception.UserException;

/**
 * @author zeng
 * @Classname UserService
 * @Description 用户登录，登出和注册等功能
 * @Date 2019/6/18 8:57
 */
public interface UserService {

    /**
     * 用户登入
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    String login(String userName,String passWord)throws UserException;

    /**
     * 用户退出登录
     * @param userId
     * @return
     */
    void logOut(Integer userId)throws UserException;

    /**
     * 用户注册
     * @param userName
     * @param password
     * @return
     */
    void register(String userName,String password)throws UserException;

}
