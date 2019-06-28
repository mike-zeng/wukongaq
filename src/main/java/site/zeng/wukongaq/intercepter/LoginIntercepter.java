package site.zeng.wukongaq.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import site.zeng.wukongaq.utils.JwtUtil;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zeng
 */

public class LoginIntercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        //从请求头中获取token
        String token=request.getHeader("token");
        try {
            Integer uid=JwtUtil.getId(token);
            request.setAttribute("uid",uid);
            return true;
        }catch (Exception e){
            try {
                response.setCharacterEncoding("utf8");
                response.getWriter().write(RetJson.fail(-2,"token校验失败").toString());
            } catch (IOException ex) {

            }
            return false;
        }
    }
}
