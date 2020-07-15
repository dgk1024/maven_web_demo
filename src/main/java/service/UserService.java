package service;

import domain.User;

public interface UserService {
    /**
     * 查找数据库是否存在
     * @param user 包含用户名于密码的User对象
     * @return 若校验通过返回包含所有用户信息的User对象,否则返回null
     */
    public User findUserByName(User user);

    /**
     * 数据库添加新注册用户数据
     * @param user
     * @return
     */
    public Boolean addUser(User user);
}
