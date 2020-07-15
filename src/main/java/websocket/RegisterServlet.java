package websocket;


import domain.User;
import org.apache.commons.beanutils.BeanUtils;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author dgk1024
 * @date 2020/7/8 20:15
 */
@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取表单数据
        Map<String, String[]> userMember = request.getParameterMap();
        System.out.println(userMember);
        //封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user,userMember);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);
        //调用UserServiceImpl查询用户名是否存在
        User res = new UserServiceImpl().findUserByName(user);
        if(res==null){
            //注册成功
            boolean isSuccess = new UserServiceImpl().addUser(user);
            if(isSuccess){
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user",user);
                response.getWriter().write(URLEncoder.encode("验证通过","utf-8"));
            }else{
                response.getWriter().write(URLEncoder.encode("注册错误,请重新尝试","utf-8"));
            }
        }else{
            //注册失败,用户已存在
            response.getWriter().write(URLEncoder.encode("用户名已存在,请更换","utf-8"));
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
