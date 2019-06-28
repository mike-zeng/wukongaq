package site.zeng.wukongaq.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import site.zeng.wukongaq.entity.answer.Comment;
import site.zeng.wukongaq.entity.answer.SubComment;
import site.zeng.wukongaq.exception.CommentException;
import site.zeng.wukongaq.service.CommentService;
import site.zeng.wukongaq.utils.RetJson;
import site.zeng.wukongaq.vo.CommentViewObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zeng
 */
@RestController
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation("提交一级评论")
    @PostMapping("/comment")
    public RetJson addComment(Comment comment, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            comment.setUid(uid);
            commentService.addComment(comment);
        } catch (CommentException e) {
            return RetJson.fail(-1,"评论失败");
        }
        return RetJson.success("评论成功");
    }

    @ApiOperation("提交二级评论")
    @PostMapping("/comment/sub")
    public RetJson addSubComment(SubComment subComment, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            subComment.setUid(uid);
            commentService.addSubComment(subComment);
        } catch (CommentException e) {
            return RetJson.fail(-1,"评论失败");
        }
        return RetJson.success("评论成功");
    }
    @ApiOperation("根据评论id删除一级评论")
    @DeleteMapping("/comment/{cid}")
    public RetJson delComment(@PathVariable("cid")Integer id, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            commentService.delComment(id,uid);
        } catch (CommentException e) {
            return RetJson.fail(-1,"删除评论失败");
        }
        return RetJson.success("评论删除成功");
    }

    @ApiOperation("根据评论id删除二级评论")
    @DeleteMapping("/comment/sub/{cid}")
    public RetJson delSubComment(@PathVariable("cid")Integer id, HttpServletRequest request){
        Integer uid= (Integer) request.getAttribute("uid");
        try {
            commentService.delSubComment(id,uid);
        } catch (CommentException e) {
            return RetJson.fail(-1,"删除评论失败");
        }
        return RetJson.success("评论删除成功");
    }
    @ApiOperation("查看某个答案的所有评论")
    @GetMapping("/comment/{aid}")
    public RetJson getComment(@PathVariable("aid")Integer aid,Integer pageNumber,Integer pageSize){
        Pageable pageable=PageRequest.of(pageNumber,pageSize);
        Page<Comment> page;
        try {
            page=commentService.selectComment(aid,pageable);
        } catch (CommentException e) {
            return RetJson.fail(-1,e.getMessage());
        }
        if (page==null){
            return RetJson.fail(-1,"没有更多评论");
        }
        return RetJson.success("comment",new CommentViewObject(page));
    }
}
