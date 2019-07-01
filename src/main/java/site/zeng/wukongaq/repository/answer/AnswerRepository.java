package site.zeng.wukongaq.repository.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import site.zeng.wukongaq.entity.answer.Answer;

/**
 * @author zeng
 */
@Repository
public interface AnswerRepository  extends  PagingAndSortingRepository<Answer,Integer>,CrudRepository<Answer,Integer>{
    void deleteByIdAndUid(Integer id,Integer uid);

    Page<Answer> findAllByPid(Integer pid, Pageable pageable);

    Answer findByIdAndUid(Integer id,Integer uid);
}
