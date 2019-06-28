package site.zeng.wukongaq.entity.answer;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author zeng
 */
@Entity
@Table(name = "tb_answer")
@Getter
@Setter
public class Answer {
    /**
     * 回答id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 问题id
     */
    @Column
    private Integer pid;

    @OneToMany
    @JoinColumn(name = "aid")
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<Comment> comments;
    /**
     * 用户id
     */
    @Column
    private Integer uid;

    /**
     * 回答内容
     */
    @Column
    private String content;

    /**
     * 阅读数
     */
    @Column
    private Integer readNum=0;

    /**
     * 评论数
     */
    @Column
    private Integer commentNum=0;

    /**
     * 赞同数
     */
    @Column
    private Integer approvalNum=0;

    /**
     * 反对数
     */
    @Column
    private Integer oppositionNum=0;

    /**
     * 更新时间
     */
    @Column
    private Date updateTime=new Date();
}
