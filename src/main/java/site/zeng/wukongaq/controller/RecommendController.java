package site.zeng.wukongaq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import site.zeng.wukongaq.entity.answer.Answer;
import site.zeng.wukongaq.entity.problem.Problem;
import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.ProblemException;
import site.zeng.wukongaq.service.AnswerService;
import site.zeng.wukongaq.service.ProblemService;
import site.zeng.wukongaq.service.RecommendService;
import site.zeng.wukongaq.service.UserInfoService;
import site.zeng.wukongaq.utils.RetJson;
import site.zeng.wukongaq.vo.RecommendViewObject;

import javax.validation.ConstraintViolationException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zeng
 */
@RestController
@Validated
public class RecommendController {
    private Logger logger=LoggerFactory.getLogger(RecommendController.class);
    private final RecommendService recommendService;

    private final ProblemService problemService;

    private final AnswerService answerService;

    private final UserInfoService userInfoService;

    @ExceptionHandler(ConstraintViolationException.class)
    public RetJson handleConstraintViolationException(ConstraintViolationException cve){
        logger.warn(cve.getMessage());
        return RetJson.fail(-1,"参数校验失败");
    }


    public RecommendController(RecommendService recommendService, ProblemService problemService, AnswerService answerService, UserInfoService userInfoService) {
        this.recommendService = recommendService;
        this.problemService = problemService;
        this.answerService = answerService;
        this.userInfoService = userInfoService;
    }

    @GetMapping("/recommend")
    public RetJson getRecommend(){
        List<RecommendViewObject> recommendViewObject=new LinkedList<>();
        long start=System.currentTimeMillis();
        List<String> list=recommendService.getProblemsId();
        long end=System.currentTimeMillis();
        System.out.println("获取推荐id的时间"+(end-start)+"毫秒");
        try {
            //获取所有问题
            start=System.currentTimeMillis();
            List<Problem> problems=problemService.selectProblemByPids(list);
            end=System.currentTimeMillis();
            System.out.println("查询问题的时间"+(end-start)+"毫秒");
            Sort sort=new Sort(Sort.Direction.DESC,"approvalNum");
            Pageable pageable= PageRequest.of(0,1,sort);
            RecommendViewObject viewObject;
            start=System.currentTimeMillis();
            for (Problem problem:problems) {
                //获取该问题对应的最前面的答案
                try {
                    Page<Answer> answers=answerService.selectAnswerList(problem.getId(),pageable);
                    if (!answers.isEmpty()){
                        viewObject=new RecommendViewObject();
                        Answer answer=answers.get().findFirst().get();
                        if (answer!=null){
                            viewObject.setPid(problem.getId());
                            viewObject.setAid(answer.getId());
                            viewObject.setAnswerContent(answer.getContent());
                            viewObject.setApprovalNum(answer.getApprovalNum());
                            viewObject.setCommentNum(answer.getCommentNum());
                            viewObject.setProblemTitle(problem.getTitle());

                            UserInfo userInfo=userInfoService.getUserInfoById(answer.getUid());
                            if (userInfo!=null){
                                viewObject.setUserName(userInfo.getNickName());
                                viewObject.setUserHeadPhoto(userInfo.getHeadPhoto());
                            }else {
                                continue;
                            }
                            recommendViewObject.add(viewObject);
                        }
                    }
                } catch (Exception e) {
                    logger.warn("获取推荐内容出现问题");
                }
            }
            end=System.currentTimeMillis();
            System.out.println("组装的时间"+(end-start)+"毫秒");
        } catch (ProblemException e) {
            e.printStackTrace();
        }
        return RetJson.success("recommends",recommendViewObject);
    }
}
