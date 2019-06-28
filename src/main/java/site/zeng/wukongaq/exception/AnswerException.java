package site.zeng.wukongaq.exception;

/**
 * @author zeng
 */
public class AnswerException extends Exception{
    public static final String ANWSER_PUT_FAIL="答案上传失败";
    public static final String ANWSER_DEL_FAIL="答案删除失败";
    public static final String ANWSER_GET_FAIL="答案获取失败";
    public static final String ANWSER_ALT_FAIL="答案修改失败";
    public AnswerException(String message) {
        super(message);
    }
}
