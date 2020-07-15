package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    //1. 定义成员变量DataSource
    private static DataSource datasource;
    //2. 定义静态代码块用于加载配置文件
    static{
        try{
            //加载配置文件
            Properties properties = new Properties();
            properties.load(utils.JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            //获取DataSource数据库连接池
            datasource = DruidDataSourceFactory.createDataSource(properties);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * 获取连接对象
     */
    public static Connection getConnection() throws Exception{
        return datasource.getConnection();
    }
    /**
     * 获取连接对象
     * @return
     */
    public static DataSource getDatasource() {
        return datasource;
    }
    /**
     * 释放资源
     */
    public static void close(Statement statement, Connection connection){
        close(null,statement,connection);
    }
    public static void close(ResultSet resultSet, Statement statement, Connection connection){
        if(resultSet!=null){
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(statement!=null){
            try{
                statement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        if(connection!=null){
            try{
                connection.close();//归还连接对象
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
