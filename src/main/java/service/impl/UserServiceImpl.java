package service.impl;

import dao.Impl.UserDaoImpl;
import dao.UserDao;
import domain.User;
import service.UserService;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();

    @Override
    public User findUserByName(User user) {
        return dao.findUserByName(user);
    }

    @Override
    public Boolean addUser(User user) {
        return dao.addUser(user);
    }
}
