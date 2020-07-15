package dao.Impl;

import dao.UserDao;
import domain.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.JDBCUtils;

import javax.sql.DataSource;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDatasource());
    @Override
    public List<User> findAllUserInfo() {
        return null;
    }

    @Override
    public User findUserByName(User user) {
        //定义sql语句
        String sql = "select * from users_table where user_name = ?";
        DataSource dataSource = JDBCUtils.getDatasource();
        try {
            //若数据库存在此对象,则返回含有全部信息的对象
            User res = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),
                    user.getUser_name());
            return res;
        } catch (DataAccessException e) {
            //否则返回空
            return null;
        }
    }

    @Override
    public Boolean addUser(User user) {
        //定义sql语句
        String sql = "insert into users_table values(null,?,?,?)";
        int num = jdbcTemplate.update(sql,
                user.getUser_name(),
                user.getUser_password(),
                user.getUser_address()
        );
        if (num > 0) {return true;}
        return false;
    }
}
