package websocket;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
@WebServlet("/checkCodeServlet")
public class CheckCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession httpSession = request.getSession();
        String code = (String)httpSession.getAttribute("code");
        httpSession.removeAttribute("code");
        String codeInput = request.getParameter("code");
        if(codeInput.equalsIgnoreCase(code)){
            response.getWriter().write(URLEncoder.encode("验证通过","utf-8"));
        }else{
            response.getWriter().write(URLEncoder.encode("验证码错误","utf-8"));
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }
}
