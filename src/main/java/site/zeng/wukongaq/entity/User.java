package site.zeng.wukongaq.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zeng
 * @Classname User
 * @Description 用户实体类
 * @Date 2019/6/17 14:53
 */
@Entity
@Table(name = "tb_user")
@Getter
@Setter
public class User {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 登录名
     */
    @Column(nullable = false,length = 32)
    private String name;

    /**
     * 密码（32位md5加密序列）
     */
    @Column(nullable = false,length = 32,columnDefinition = "char")
    private String password;

    /**
     * md5加密盐值
     */
    @Column(nullable = false,length = 8,columnDefinition = "char")
    private String salt;
}
