package site.zeng.wukongaq.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.zeng.wukongaq.entity.answer.Answer;
import site.zeng.wukongaq.exception.AnswerException;

/**
 * @author zeng
 */
public interface AnswerService {

    void addAnswer(Answer answer) throws AnswerException;

    void delAnswer(Integer uid,Integer aid) throws AnswerException;

    Page<Answer> selectAnswerList(Integer pid, Pageable pageable) throws AnswerException;

    Answer getAnswer(Integer id) throws AnswerException;
}
