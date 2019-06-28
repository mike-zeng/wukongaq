package site.zeng.wukongaq.exception;

/**
 * @author zeng
 */
public class UserException extends Exception {
    public static final String LOGIN_FAIL="登入失败";
    public static final String LOGOUT_FAIL="注销失败";
    public static final String REGISTER_FAIL="注册失败";
    public static final String USER_IS_EXIST="用户已存在";
    public UserException(String message) {
        super(message);
    }
}
