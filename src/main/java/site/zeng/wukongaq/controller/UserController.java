package site.zeng.wukongaq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.UserException;
import site.zeng.wukongaq.exception.UserInfoException;
import site.zeng.wukongaq.service.UserInfoService;
import site.zeng.wukongaq.service.UserService;
import site.zeng.wukongaq.utils.JwtUtil;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zeng
 */
@Api(value = "用户相关")
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private Logger logger= LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final UserInfoService userInfoService;

    @ExceptionHandler(ConstraintViolationException.class)
    public RetJson handleConstraintViolationException(ConstraintViolationException cve){
        logger.warn(cve.getMessage());
        return RetJson.fail(-1,"参数校验失败");
    }

    public UserController(UserService userService, UserInfoService userInfoService) {
        this.userService = userService;
        this.userInfoService = userInfoService;
    }

    /**
     * 用户登入
     * @param username 用户名
     * @param password 密码
     */
    @ApiOperation("用户登入接口")
    @RequestMapping(value = "/login/{username}",method = RequestMethod.POST)
    public RetJson login(@NotNull @PathVariable("username") String username, @NotNull String password){
        String token;
        try {
            token = userService.login(username,password);
        } catch (UserException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        if (token==null){
            return RetJson.fail(-1,"登入出错");
        }
        Integer uid=JwtUtil.getId(token);
        Map<String,Object> map=new HashMap<>(2);
        map.put("uid",uid);
        map.put("token",token);
        return RetJson.success(map);
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param passWord 密码
     */
    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public RetJson register(@NotNull String userName,@NotNull String passWord){
        try {
            userService.register(userName,passWord);
            return RetJson.success("注册成功");
        } catch (UserException e) {
            return RetJson.fail(-1,e.getMessage());
        }
    }

    @ApiOperation("用户注销接口")
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public RetJson logOut(HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            userService.logOut(uid);
            return RetJson.success("注销成功");
        } catch (UserException e) {
            return RetJson.fail(-1,e.getMessage());
        }
    }

    @ApiOperation("查看用户信息")
    @RequestMapping(value = "info/{uid}",method = RequestMethod.GET)
    public RetJson getUserInfo(@PathVariable("uid") Integer uid){
        UserInfo userInfo;
        try {
            userInfo = userInfoService.getUserInfoById(uid);
        } catch (UserInfoException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("userInfo",userInfo);
    }

    @ApiOperation("修改用户信息")
    @RequestMapping(value = "/info/{uid}",method = RequestMethod.PUT)
    public RetJson alterUserInfo(@PathVariable("uid")Integer uid,String nickName,String headPhoto,String personalProfile){
        UserInfo userInfo=new UserInfo();
        userInfo.setId(uid);
        userInfo.setNickName(nickName);
        userInfo.setHeadPhoto(headPhoto);
        userInfo.setPersonalProfile(personalProfile);
        try {
            userInfoService.alterUserInfo(userInfo);
        } catch (UserInfoException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success();
    }

    @ApiOperation("头像上传")
    @PostMapping(value = "/headPhoto")
    public RetJson alterHeadPhoto(@RequestParam("photo") MultipartFile multipartFile,HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        if (multipartFile==null||multipartFile.isEmpty()){
            return RetJson.fail(-1,"头像不能为null");
        }
        try {
            InputStream inputStream=multipartFile.getInputStream();
            String key=userInfoService.alterHeadPhoto(uid,inputStream);
            if (null==key){
                return RetJson.fail(-1,"头像更换失败");
            }
            return RetJson.success("headPhotoKey",key);
        } catch (IOException e) {
            return RetJson.fail(-1,"头像上传失败");
        } catch (UserInfoException e) {
            return RetJson.fail(-1,e.getMessage());
        }
    }
}
