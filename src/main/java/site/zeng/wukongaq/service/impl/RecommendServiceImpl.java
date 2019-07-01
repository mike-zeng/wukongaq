package site.zeng.wukongaq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import site.zeng.wukongaq.service.RecommendService;
import site.zeng.wukongaq.service.RedisClient;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author zeng
 */
@Service
public class RecommendServiceImpl implements RecommendService {
    private final RedisClient redisClient;
    private Logger logger= LoggerFactory.getLogger(RecommendServiceImpl.class);

    private static final String Z_SET_NAME="hotProblem";
    private static final Integer COUNT=10;

    private List<String> rangeGet(){
        Jedis jedis=null;
      try {
          Set<String> res=new HashSet<>();
          jedis=redisClient.getRedis();
          Long sum=jedis.zcard(Z_SET_NAME);
          int count=4;
          int start=0,end=0;
          int i=0;
          //数量不够，不进行推送
          if (sum>=COUNT*4){
              while (count!=0){
                  start=end;
                  end=((Double)(sum*((5-count)*0.1))).intValue();
                  while (i<count){
                      int temp=(int)(start+Math.random()*(end-start+1));
                      Set<String> range=jedis.zrange(Z_SET_NAME,temp,temp);
                      int oldSize=res.size();
                      res.addAll(range);
                      int newSize=res.size();
                      if (newSize-oldSize!=0){
                          i++;
                      }
                  }
                  i=0;
                  count--;
              }
          }else {
              return null;
          }
          List<String> ret = new LinkedList<>(res);
          return ret;
      }catch (Exception e){
          logger.warn("获取问题列表失败"+e.getMessage());
      }finally {
          redisClient.returnResource(jedis);
      }
      return null;

    }

    public RecommendServiceImpl(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public void increaseWeight(Integer pid, Integer weight) {
        Jedis jedis=null;
        try {
            jedis = redisClient.getRedis();
            jedis.zadd(Z_SET_NAME, weight, pid + "");
        } catch (Exception e) {
            logger.warn("增加权重失败");
        }finally {
            redisClient.returnResource(jedis);
        }

    }

    @Override
    public void decreaseWeight(Integer pid, Integer weight) {
        Jedis jedis=null;
        try {
            jedis = redisClient.getRedis();
            jedis.zadd(Z_SET_NAME, -weight, pid + "");
        } catch (Exception e) {
            logger.warn("减少权重失败");
        }finally {
            redisClient.returnResource(jedis);
        }
    }

    @Override
    public List<String> getProblemsId() {
        Jedis jedis=null;
        try {
            List<String> strings=rangeGet();
            return strings;
        }catch (Exception e){
            logger.warn("获取问题列表失败");
        }finally {
            redisClient.returnResource(jedis);
        }
        return null;
    }
}
