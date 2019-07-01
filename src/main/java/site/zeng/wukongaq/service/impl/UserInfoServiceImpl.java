package site.zeng.wukongaq.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.zeng.wukongaq.entity.user.UserInfo;
import site.zeng.wukongaq.exception.UserInfoException;
import site.zeng.wukongaq.repository.user.UserInfoRepository;
import site.zeng.wukongaq.service.UserInfoService;
import site.zeng.wukongaq.utils.TencentCosUtil;
import site.zeng.wukongaq.utils.UpdateUtil;

import java.io.InputStream;
import java.util.Date;
import java.util.Optional;

/**
 * @author zeng
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private static final Long TERM=(long)1000*60*60*24*30;
    private static Date now=new Date();

    private Logger logger= LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfo getUserInfoById(Integer id)throws UserInfoException {
        UserInfo userInfo=null;
        try {
            Optional<UserInfo> optionalUserInfo=userInfoRepository.findById(id);
            if (optionalUserInfo.isPresent()){
                userInfo=optionalUserInfo.get();
            }
            if (null == userInfo){
                logger.warn(UserInfoException.USER_NOT_EXIST);
                throw new UserInfoException(UserInfoException.USER_NOT_EXIST);
            }
        }catch (Exception e){
            logger.warn(UserInfoException.GET_USER_INFO_ERROR);
            throw new UserInfoException(UserInfoException.GET_USER_INFO_ERROR);
        }
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void alterUserInfo(UserInfo userInfo) throws UserInfoException{
        //检查用户是否可以修改个人资料，一个月之内只允许修改一次
        UserInfo u=getUserInfoById(userInfo.getId());
        Date now=new Date();
        //如果修改30天内已经修改资料，则不允许修改
//        if ((now.getTime()-u.getUpdateTime().getTime())<=TERM){
//            throw new UserInfoException(UserInfoException.MODIFY_USERINFO_FREQUENTLY);
//        }
        try {
            userInfo.setApproval(null);
            userInfo.setFans(null);
            userInfo.setAttention(null);
            userInfo.setUpdateTime(now);
            UpdateUtil.copyNonNullProperties(userInfo,u);
            userInfoRepository.save(u);
        }catch (Exception e){
            logger.warn(UserInfoException.ALT_USER_INFO_ERROR);
            throw new UserInfoException(UserInfoException.ALT_USER_INFO_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String alterHeadPhoto(Integer uid,InputStream inputStream) throws UserInfoException{
        String key="/head_photo/u"+uid;
        try {
            TencentCosUtil.put(key,inputStream);
        }catch (Exception e){
            logger.warn(UserInfoException.ALT_HEAD_PHOTO_ERROR);
            throw new UserInfoException(UserInfoException.ALT_HEAD_PHOTO_ERROR);
        }
        UserInfo userInfo=new UserInfo();
        userInfo.setId(uid);
        userInfo.setUpdateTime(now);
        userInfo.setHeadPhoto(key);
        //保存数据库
        alterUserInfo(userInfo);
        return key;
    }
}
