package site.zeng.wukongaq.service;

import java.util.List;

/**
 * @author zeng
 */
public interface RecommendService {
    /**
     * 增加权值
     */
    void increaseWeight(Integer pid,Integer weight);

    /**
     * 降低权值
     */
    void decreaseWeight(Integer pid,Integer weight);

    /**
     * 获取问题Id
     */
    List<String> getProblemsId();

}
