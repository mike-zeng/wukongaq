package site.zeng.wukongaq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.UserException;
import site.zeng.wukongaq.exception.UserInfoException;
import site.zeng.wukongaq.service.UserInfoService;
import site.zeng.wukongaq.service.UserService;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zeng
 */
@Api(value = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    private final UserInfoService userInfoService;

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
    public RetJson login(@PathVariable("username") String username, String password){
        String token;
        try {
            token = userService.login(username,password);
        } catch (UserException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        if (token==null){
            return RetJson.fail(-1,"登入出错");
        }
        return RetJson.success("token",token);
    }

    /**
     * 用户注册
     * @param userName 用户名
     * @param passWord 密码
     */
    @ApiOperation("用户注册接口")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public RetJson register(String userName,String passWord){
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
    public RetJson alterUserInfo(@PathVariable("uid")Integer uid,UserInfo userInfo){
        userInfo.setId(uid);
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
