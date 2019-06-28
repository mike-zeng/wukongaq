package site.zeng.wukongaq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.zeng.wukongaq.entity.answer.Comment;
import site.zeng.wukongaq.entity.answer.SubComment;
import site.zeng.wukongaq.exception.CommentException;
import site.zeng.wukongaq.repository.comment.CommentRepository;
import site.zeng.wukongaq.repository.comment.SubCommentRepository;
import site.zeng.wukongaq.service.CommentService;

/**
 * @author zeng
 */
@Service
public class CommentServiceImpl implements CommentService {
    private Logger logger= LoggerFactory.getLogger(CommentServiceImpl.class);
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    SubCommentRepository subCommentRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addComment(Comment comment) throws CommentException {
        try {
            commentRepository.save(comment);
        }catch (Exception e){
            throw new CommentException("评论提交失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSubComment(SubComment subComment)throws CommentException {
        try {
            subCommentRepository.save(subComment);
        }catch (Exception e){
            throw new CommentException("提交评论失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delComment(Integer id, Integer uid) throws CommentException {
        try {
            commentRepository.deleteCommentsByIdAndUid(id,uid);
        }catch (Exception e){
            logger.warn("评论删除失败");
            throw new CommentException("评论删除失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delSubComment(Integer id, Integer uid) throws CommentException {
        try {
            subCommentRepository.deleteByIdAndUid(id,uid);
        }catch (Exception e){
            throw new CommentException("评论删除失败");
        }
    }
    @Override
    public Page<Comment> selectComment(Integer aid,Pageable pageable) throws CommentException {
        Page<Comment> page=null;
        try {
            page=commentRepository.findCommentsByAid(aid,pageable);
        }catch (Exception e){
            throw new CommentException("评论获取失败");
        }
        return page;
    }
}
