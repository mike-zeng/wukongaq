package site.zeng.wukongaq.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import site.zeng.wukongaq.WukongaqApplication;
import site.zeng.wukongaq.service.RecommendService;

/**
 * @author zeng
 * @Classname RecommendServiceImplTest
 * @Description TODO
 * @Date 2019/6/29 16:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WukongaqApplication.class)
public class RecommendServiceImplTest {
    @Autowired
    RecommendService recommendService;
    @Test
    public void increaseWeight() {
    }

    @Test
    public void decreaseWeight() {
    }

    @Test
    public void getProblemsId() {
//        List<String> list=recommendService.getProblemsId(10);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i));
//        }
    }
}