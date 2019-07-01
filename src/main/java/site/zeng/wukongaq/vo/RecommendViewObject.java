package site.zeng.wukongaq.vo;

import lombok.Data;

/**
 * @author zeng
 */
@Data
public class RecommendViewObject {

    /**
     * 问题id
     */
    private Integer pid;

    /**
     * 答案id
     */
    private Integer aid;
    /**
     * 问题标题
     */
    private String problemTitle;

    /**
     * 答主头像
     */
    private String userHeadPhoto;

    /**
     * 答主名称
     */
    private String userName;

    /**
     * 简要答案内容
     */
    private String answerContent;

    /**
     * 赞同数
     */
    private Integer approvalNum;

    /**
     * 评论数
     */
    private Integer commentNum;
}
