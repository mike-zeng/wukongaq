package site.zeng.wukongaq.vo;

import lombok.Data;
import org.springframework.data.domain.Page;
import site.zeng.wukongaq.entity.answer.Comment;

import java.util.List;

/**
 * @author zeng
 */
@Data
public class CommentViewObject {
    private Integer totalPage;
    private Long totalElements;
    private Integer pageNumber;
    private Integer size;
    private List<Comment> comments;

    public CommentViewObject(Page<Comment> page){
        totalPage=page.getTotalPages();
        totalElements=page.getTotalElements();
        size=page.getSize();
        pageNumber=page.getNumber();
    }
}
