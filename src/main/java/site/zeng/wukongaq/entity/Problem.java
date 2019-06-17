package site.zeng.wukongaq.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zeng
 * @Classname Problem
 * @Description 问题
 * @Date 2019/6/17 15:41
 */
@Entity
@Table(name = "tb_problem")
@Getter
@Setter
public class Problem {
    /**
     * 问题id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  Integer id;

    /**
     * 问题标题
     */
    @Column(length = 100)
    private String title;

    /**
     * 问题描述
     */
    @Column(length = 1000)
    private String description;

    /**
     * 提问者，外键
     */
    @Column
    private Integer questioner;
}
