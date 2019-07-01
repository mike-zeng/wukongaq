package site.zeng.wukongaq.controller;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.zeng.wukongaq.entity.answer.Answer;
import site.zeng.wukongaq.exception.AnswerException;
import site.zeng.wukongaq.service.AnswerService;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

/**
 * @author zeng
 */
@Api("答案相关")
@RestController
@Validated
public class AnswerController {
    private Logger logger= LoggerFactory.getLogger(AnswerController.class);

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RetJson handleConstraintViolationException(ConstraintViolationException cve){
        logger.warn(cve.getMessage());
        return RetJson.fail(-1,"参数校验失败");
    }

    @PostMapping("/answer")
    public RetJson addAnswer(@NotNull Integer pid,@NotNull String content, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        Answer answer=new Answer();
        answer.setContent(content);
        answer.setPid(pid);
        answer.setUid(uid);
        try {
            answerService.addAnswer(answer);
        } catch (AnswerException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("回答上传成功");
    }

    @DeleteMapping("/answer/{aid}")
    public RetJson delAnswer(@PathVariable("aid")Integer aid, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            answerService.delAnswer(uid,aid);
        } catch (AnswerException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("删除回答成功");
    }

    @GetMapping("/answer/problem/{pid}")
    public RetJson getAnswerList(@PathVariable("pid") Integer pid,Integer pageNum,Integer pageSize){
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        try {
            answerService.selectAnswerList(pid,pageable);
        } catch (AnswerException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success();
    }

    @GetMapping("/answer/{id}")
    public RetJson getAnswer(@PathVariable("id")Integer id){
        Answer answer;
        try {
            answer=answerService.getAnswer(id);
        } catch (AnswerException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("answer",answer);
    }
}
