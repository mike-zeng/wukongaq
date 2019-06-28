package site.zeng.wukongaq.entity.answer;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zeng
 */
@Entity
@Table(name = "tb_sub_comment")
@Data
public class SubComment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer parentId;

    @Column
    private Integer uid;

    @Column
    private String content;

    @Column
    private Date updateTime=new Date();
}
