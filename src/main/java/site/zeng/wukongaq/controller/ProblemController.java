package site.zeng.wukongaq.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import site.zeng.wukongaq.entity.problem.Problem;
import site.zeng.wukongaq.exception.ProblemException;
import site.zeng.wukongaq.service.ProblemService;
import site.zeng.wukongaq.utils.RetJson;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zeng
 */
@Api("问题相关")
@RestController
public class ProblemController {
    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    @ApiOperation("提交问题")
    @PostMapping("/problem")
    public RetJson commitProblem(Problem problem,HttpServletRequest request){
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
    public RetJson delProblem(Integer pid,HttpServletRequest request){
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
        return RetJson.success();
    }

    @ApiOperation("查看用户的所有问题")
    @GetMapping("/problem/{uid}")
    public RetJson getProblemByUser(@PathVariable("uid")Integer uid,Integer pageNum,Integer pageSize){
        Pageable pageable= PageRequest.of(pageNum,pageSize);
        try {
            problemService.selectProblemByUser(uid,pageable);
        } catch (ProblemException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        return RetJson.success("page",pageable);
    }

}
