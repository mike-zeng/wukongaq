package site.zeng.wukongaq.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import site.zeng.wukongaq.WukongaqApplication;
import site.zeng.wukongaq.entity.answer.Answer;
import site.zeng.wukongaq.exception.AnswerException;
import site.zeng.wukongaq.service.AnswerService;

/**
 * @author zeng
 * @Classname AnswerServiceImplTest
 * @Description TODO
 * @Date 2019/6/27 14:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WukongaqApplication.class)
public class AnswerServiceImplTest {
    @Autowired
    AnswerService answerService;
    @Test
    public void addAnswer() {
        Answer answer=new Answer();
        answer.setUid(1);
        answer.setPid(2);
        answer.setContent("我也觉得很ok");
        try {
            answerService.addAnswer(answer);
        } catch (AnswerException e) {
            System.out.println("失败");
        }
    }

    @Test
    public void delAnswer() throws AnswerException {
        answerService.delAnswer(null,3);
    }

    @Test
    public void selectAnserList() throws AnswerException {
        Sort sort=new Sort(Sort.Direction.DESC,"updateTime");
        Pageable pageable=new PageRequest(0,5,sort);
//        Page<Answer> page=answerService.selectAnserList(2,pageable);
//        System.out.println(page.getTotalElements());
    }

    @Test
    public void getAnswer() throws AnswerException {
        Answer answer=answerService.getAnswer(4);
        System.out.println(answer);
    }
}