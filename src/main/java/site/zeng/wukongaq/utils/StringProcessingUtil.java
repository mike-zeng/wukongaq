package site.zeng.wukongaq.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

/**
 * @author zeng
 * @Classname StringProcessingUtil
 * @Description 用来处理字符串
 * @Date 2019/6/21 14:21
 */
public class StringProcessingUtil {
    private StringProcessingUtil(){

    }
    public static String md5(String text, String salt){
        String encodeStr= DigestUtils.md5Hex(text + salt);
        return encodeStr;
    }
    public static String getSalt(){
        int length=5;
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(str.length());
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
