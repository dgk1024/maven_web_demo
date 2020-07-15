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
 * @date 2020/7/8 16:51
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        /**
         * 获取用户提交的表单数据
         * 先保存其密码,然后将表单数据封装成user对象
         * 调用servlet,servlet调用dao进行查询,
         * 先判断用户名是否存在,若存在则比对密码是否正确
         * 验证通过之后跳转聊天界面
         */
        Map<String, String[]> userMember = request.getParameterMap();
        System.out.println(userMember);
        String password =  request.getParameter("user_password");
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
        //调用servlet查询
        User res = new UserServiceImpl().findUserByName(user);
        if(res!=null){
            if(password.equals(res.getUser_password())){
                //登陆成功
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("user",user);
                response.getWriter().write(URLEncoder.encode("验证通过","utf-8"));
            }else{
                //密码错误
                response.getWriter().write(URLEncoder.encode("密码错误","utf-8"));
            }
        }else{
            //用户不存在
            response.getWriter().write(URLEncoder.encode("用户不存在","utf-8"));
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
