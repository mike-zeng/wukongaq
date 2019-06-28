package site.zeng.wukongaq.repository.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import site.zeng.wukongaq.entity.answer.Comment;

/**
 * @author zeng
 */
@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Integer>,CrudRepository<Comment,Integer> {
    void deleteByIdAndUid(Integer id,Integer uid);

    Page<Comment> findCommentsByAid(Integer aid, Pageable pageable);

    void deleteCommentsByIdAndUid(Integer id,Integer uid);

}
