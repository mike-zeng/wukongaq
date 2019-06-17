package site.zeng.wukongaq.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

/**
 * @author zeng
 * @Classname JwtUtil
 * @Description TODO
 * @Date 2019/6/17 16:08
 */
public class JwtUtil {

    /**
     * token秘钥,不能泄露
     */
    private static final String SECRET="zxh18100738792";

    private static final int CALENDAR_FIELD= Calendar.DATE;

    /**
     * 过期时间,一周
     */
    private static  final int CALENDAR_INTERVAL=7;

    /**
     * 创建token
     * @param id 用户id
     * @return token值
     */
    public static String createToken(Integer id) {
        Date iatDate=new Date();
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(CALENDAR_FIELD,CALENDAR_INTERVAL);
        //过期时间
        Date expiresDate=nowTime.getTime();
        //头部信息
        Map<String,Object> map=new HashMap<>(2);
        map.put("alg","HS256");
        map.put("typ","JWT");

        String token= JWT.create().withHeader(map).withClaim("iss","Service")
                .withClaim("aud","APP")
                .withClaim("id",id)
                .withIssuedAt(iatDate)
                .withExpiresAt(expiresDate)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * token校验
     * @param token token值
     * @return 返回token中的信息
     */
    private   static Map<String, Claim> verifyToken(String token){
        DecodedJWT jwt=null;
        try {
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt=verifier.verify(token);
        }catch (Exception e){
            //校验失败
        }
        if (jwt==null){
            return null;
        }
        return jwt.getClaims();
    }

    /**
     * 从token中判断用户id
     * @param token token值
     * @return 返回用户id
     */
    public static Integer getId(String token){
        Map<String,Claim> map=JwtUtil.verifyToken(token);
        return Integer.valueOf(map.get("id").asString());
    }
}