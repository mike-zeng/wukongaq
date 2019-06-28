package site.zeng.wukongaq.repository.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import site.zeng.wukongaq.entity.user.User;

/**
 * @author zeng
 * @Classname UserRepository
 * @Description TODO
 * @Date 2019/6/21 14:16
 */
@Repository
public interface UserRepository extends CrudRepository<User,Integer> {
    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    User findByName(String name);
}
