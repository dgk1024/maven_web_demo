package dao;

import domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 查询所有用户数据
     * @return
     */
    public List<User> findAllUserInfo();
    /**
     * 通过user_name查找是否存在相应的数据,并返回User对象
     * @param user
     * @return
     */
    public User findUserByName(User user);

    /**
     * 数据库添加新注册用户数据
     * @param user
     * @return
     */
    public Boolean addUser(User user);
}
