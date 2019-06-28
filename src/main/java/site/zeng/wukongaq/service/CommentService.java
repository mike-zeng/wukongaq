package site.zeng.wukongaq.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.zeng.wukongaq.entity.answer.Comment;
import site.zeng.wukongaq.entity.answer.SubComment;
import site.zeng.wukongaq.exception.CommentException;

/**
 * @author zeng
 */
public interface CommentService {
    void addComment(Comment comment)throws CommentException;

    void delComment(Integer id,Integer uid)throws CommentException;

    Page<Comment> selectComment(Integer aid, Pageable pageable) throws CommentException;

    void addSubComment(SubComment subComment)throws CommentException;

    void delSubComment(Integer id, Integer uid) throws CommentException;
}
