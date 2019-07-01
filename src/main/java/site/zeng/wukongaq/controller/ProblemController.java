package site.zeng.wukongaq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import site.zeng.wukongaq.entity.problem.Problem;
import site.zeng.wukongaq.exception.ProblemException;
import site.zeng.wukongaq.service.ProblemService;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;

/**
 * @author zeng
 */
@Api("问题相关")
@RestController
@Validated
public class ProblemController {
    private Logger logger= LoggerFactory.getLogger(ProblemController.class);
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RetJson handleConstraintViolationException(ConstraintViolationException cve){
        logger.warn(cve.getMessage());
        return RetJson.fail(-1,"参数校验失败");
    }
    @ApiOperation("提交问题")
    @PostMapping("/problem")
    public RetJson commitProblem(@NotNull String title,String description,HttpServletRequest request){
        Problem problem=new Problem();
        problem.setTitle(title);
        problem.setDescription(description);
        problem.setUid((Integer) request.getAttribute("uid"));
        //添加问题
        try {
            problemService.addProblem(problem);
        } catch (ProblemException e) {
            e.printStackTrace();
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success();
    }

    @ApiOperation("删除问题")
    @DeleteMapping("/problem")
    public RetJson delProblem(@NotNull Integer pid,HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            problemService.delProblem(uid,pid);
        } catch (ProblemException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success();
    }

    @ApiOperation("获取问题详细信息")
    @GetMapping("/problem/info/{pid}")
    public RetJson getProblem(@PathVariable("pid")Integer pid){
        Problem problem;
        try {
            problem=problemService.selectProblemByPid(pid);
        } catch (ProblemException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("problem",problem);
    }

    @ApiOperation("查看用户的所有问题")
    @GetMapping("/problem/{uid}")
    public RetJson getProblemByUser(@PathVariable("uid")Integer uid,@NotNull Integer pageNum,@NotNull Integer pageSize){
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        try {
            problemService.selectProblemByUser(uid,pageable);
        } catch (ProblemException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("page",pageable);
    }

}
