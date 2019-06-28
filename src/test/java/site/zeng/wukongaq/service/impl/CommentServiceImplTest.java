package site.zeng.wukongaq.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import site.zeng.wukongaq.WukongaqApplication;
import site.zeng.wukongaq.entity.answer.Comment;
import site.zeng.wukongaq.exception.CommentException;
import site.zeng.wukongaq.service.CommentService;

/**
 * @author zeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WukongaqApplication.class)
public class CommentServiceImplTest {

    @Autowired
    CommentService commentService;
    @Test
    public void addComment() throws CommentException {
        Comment comment=new Comment();
        comment.setAid(4);
        commentService.addComment(comment);
    }

    @Test
    public void delComment() throws CommentException {
        commentService.delComment(5,null);
    }

    @Test
    public void selectComment() throws CommentException {
        Pageable pageable=new PageRequest(0,1);
        Page<Comment> page=commentService.selectComment(5,pageable);
        System.out.println(page.getSize());
    }
}