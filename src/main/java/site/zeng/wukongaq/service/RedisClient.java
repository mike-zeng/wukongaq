package site.zeng.wukongaq.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * @author zeng
 */
@Component
public class RedisClient{

    private final JedisPool jedisPool;

    Logger logger= LoggerFactory.getLogger(RedisClient.class);

    public RedisClient(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public Jedis getRedis(){
        Jedis jedis = null;
        if (jedisPool != null) {
            try {
                if (jedis == null) {
                    jedis = jedisPool.getResource();
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
        return jedis;
    }

    public synchronized void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }


}