package site.zeng.wukongaq.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.zeng.wukongaq.WukongaqApplication;
import site.zeng.wukongaq.exception.UserException;
import site.zeng.wukongaq.service.UserService;

/**
 * @author zeng
 * @Classname UserServiceImplTest
 * @Description TODO
 * @Date 2019/6/21 15:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WukongaqApplication.class)
public class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    public void login() throws UserException {
        String token=userService.login("18100738792","123456");
        System.out.println(token);
    }

    @Test
    public void logOut() throws UserException {
        userService.logOut(1);
    }

    @Test
    public void register() throws UserException {
        userService.register("18100738792","123456");
    }
}