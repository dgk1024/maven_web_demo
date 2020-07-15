package websocket;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * 验证码图片生成
 * @author 李轩
 */
@WebServlet("/getCodeServlet")
public class GetCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("获取验证码");
        int width = 100;
        int height= 50;
        //1. 创建一对象,在内存中(验证码图片对象)
        BufferedImage bufferedImage = new BufferedImage(width,height, TYPE_INT_RGB);
        //2. 美化图片
        //2.1 填充背景颜色
        //画笔对象
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setColor(Color.PINK);
        graphics.fillRect(0,0,width,height);
        //2.2 画边框
        graphics.setColor(Color.CYAN);
        graphics.drawRect(0,0,width-1,height-1);
        //2.3 写验证码
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        graphics.setColor(Color.black);
        //存储验证码
        String Code = "";
        for(int i=1;i<=4;i++){
            int index = random.nextInt(str.length());
            //随机字符
            char ch = str.charAt(index);
            graphics.drawString(ch+"",width/5*i,height/2);
            Code+=ch;
        }
        //session对象保存验证码
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("code");
        httpSession.setAttribute("code",Code);

        System.out.println("生成的验证码为: "+Code);
        //2.4 画干扰线
        graphics.setColor(Color.ORANGE);
        for (int i = 0; i < 10; i++) {
            int x1 = random.nextInt(width);
            int x2 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1,y1,x2,y2);
        }
        //3. 将图片输出到页面展示
        ImageIO.write(bufferedImage,"jpg",response.getOutputStream());

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}