package site.zeng.wukongaq.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author zeng
 * @Classname UserInfo
 * @Description 用户信息实体类
 * @Date 2019/6/17 15:33
 */
@Entity
@Table(name = "tb_user_info")
@Getter
@Setter
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * 用户昵称
     */
    @Column(length = 12)
    private String nickName;
    /**
     * 用户头像
     */
    private String headPhoto;

    /**
     * 个人简介
     */
    @Column(length = 50)
    private String personalProfile;

}
