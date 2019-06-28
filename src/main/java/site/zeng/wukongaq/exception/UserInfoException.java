package site.zeng.wukongaq.exception;

/**
 * @author zeng
 */
public class UserInfoException extends Exception {
    public static final String USER_NOT_EXIST="用户不存在";
    public static final String GET_USER_INFO_ERROR="获取用户信息失败";
    public static final String ALT_USER_INFO_ERROR="修改用户信息失败";
    public static final String ALT_HEAD_PHOTO_ERROR="修改用户头像失败";
    public static final String MODIFY_USERINFO_FREQUENTLY="一个月只能修改一次个人信息";

    public UserInfoException(String message){
        super(message);
    }
}
