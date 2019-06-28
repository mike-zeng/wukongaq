package site.zeng.wukongaq.exception;

/**
 * @author zeng
 */
public class ProblemException extends Exception {
    public static final String GET_PROBLEM_ERROR="获取问题失败";
    public static final String DEL_PROBLEM_ERROR="删除问题失败";

    public ProblemException(String message){
        super(message);
    }
}
