package site.zeng.wukongaq.repository.comment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import site.zeng.wukongaq.entity.answer.SubComment;

/**
 * @author zeng
 */
public interface SubCommentRepository  extends PagingAndSortingRepository<SubComment,Integer>, CrudRepository<SubComment,Integer> {
    void deleteByIdAndUid(Integer id,Integer uid);
}
