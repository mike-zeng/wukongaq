package site.zeng.wukongaq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import site.zeng.wukongaq.entity.user.User;
import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.UserException;
import site.zeng.wukongaq.repository.user.UserInfoRepository;
import site.zeng.wukongaq.repository.user.UserRepository;
import site.zeng.wukongaq.service.RedisClient;
import site.zeng.wukongaq.service.UserService;
import site.zeng.wukongaq.utils.JwtUtil;
import site.zeng.wukongaq.utils.StringProcessingUtil;

/**
 * @author zeng
 */
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final UserInfoRepository userInfoRepository;

    private final RedisClient redisClient;

    private Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(UserInfoRepository userInfoRepository, RedisClient redisClient, UserRepository userRepository) {
        this.userInfoRepository = userInfoRepository;
        this.redisClient = redisClient;
        this.userRepository = userRepository;
    }

    /**
     * 用户登入
     * @param userName 用户名
     * @param passWord 密码
     * @return 是否登入成功
     */
    @Override
    public String login(String userName, String passWord)throws UserException {
        try {
            User user=userRepository.findByName(userName);
            if (user!=null){
                if (StringProcessingUtil.md5(passWord,user.getSalt()).equals(user.getPassword())){
                    //登入成功，发放token，并在redis中设置key-value
                    String token=JwtUtil.createToken(user.getId());
                    Jedis jedis=redisClient.getRedis();
                    try {
                        jedis.hset("user","u"+user.getId(), token);
                    }finally {
                        redisClient.returnResource(jedis);
                    }
                    return token;
                }
            }
        }catch (Exception e){
            logger.warn(UserException.LOGIN_FAIL);
            throw new UserException(UserException.LOGIN_FAIL);
        }
        return null;
    }

    /**
     * 用户注销
     * @param userId
     * @return 注销是否成功
     */
    @Override
    public void logOut(Integer userId) throws UserException{
        try {
            Jedis jedis=redisClient.getRedis();
            try {
                jedis.hdel("user","u"+userId);
            }finally {
                jedis.close();
            }
        }catch (Exception e){
            logger.warn(UserException.LOGOUT_FAIL);
            throw new UserException(UserException.LOGOUT_FAIL);
        }
    }

    /**
     * 注册用户，使用md5和随机的5位长度盐值加密
     * @param userName
     * @param passWord
     * @return 登入是否成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String userName, String passWord) throws UserException{
        try {
            //判断用户是否已经注册
            User u=userRepository.findByName(userName);
            if (u!=null){
                throw new UserException(UserException.USER_IS_EXIST);
            }
            User user=new User();
            user.setName(userName);
            user.setPassword(passWord);
            //生成salt
            String salt=StringProcessingUtil.getSalt();
            user.setSalt(salt);
            //md5+salt 加密明文密码
            user.setPassword(StringProcessingUtil.md5(passWord,salt));
            u=userRepository.save(user);
            UserInfo userInfo=new UserInfo();
            userInfo.setId(u.getId());
            userInfoRepository.save(userInfo);
        }catch (Exception e){
            throw new UserException(UserException.REGISTER_FAIL);
        }
    }
}
