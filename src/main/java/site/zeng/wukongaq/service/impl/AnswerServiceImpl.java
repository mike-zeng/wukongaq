package site.zeng.wukongaq.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.zeng.wukongaq.entity.answer.Answer;
import site.zeng.wukongaq.exception.AnswerException;
import site.zeng.wukongaq.repository.answer.AnswerRepository;
import site.zeng.wukongaq.service.AnswerService;

import java.util.Optional;

/**
 * @author zeng
 */
@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addAnswer(Answer answer)throws AnswerException {
        try {
            answerRepository.save(answer);
        }catch (Exception e){
            throw new AnswerException(AnswerException.ANWSER_PUT_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delAnswer(Integer uid, Integer aid) throws AnswerException {
        try {
            answerRepository.deleteByIdAndUid(aid,uid);
        }catch (Exception e){
            throw new AnswerException(AnswerException.ANWSER_DEL_FAIL);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<Answer> selectAnswerList(Integer pid, Pageable pageable) throws AnswerException {
        Page<Answer> page;
        try {
            page=answerRepository.findAllByPid(pid,pageable);
        }catch (Exception e){
            throw new AnswerException(AnswerException.ANWSER_GET_FAIL);
        }
        return page;
    }

    @Override
    public Answer getAnswer(Integer id) throws AnswerException{
        Answer answer;
        try {
            Optional<Answer> optionalAnswer = answerRepository.findById(id);
            if (optionalAnswer.isPresent()){
                answer=optionalAnswer.get();
            }else {
                throw new AnswerException(AnswerException.ANWSER_GET_FAIL);
            }
        }catch (Exception e){
            throw new AnswerException(AnswerException.ANWSER_GET_FAIL);
        }
        return answer;
    }
}
