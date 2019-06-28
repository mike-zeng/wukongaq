package site.zeng.wukongaq.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.zeng.wukongaq.entity.user.UserInfo;

/**
 * @author zeng

 */
@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Integer> {

}
