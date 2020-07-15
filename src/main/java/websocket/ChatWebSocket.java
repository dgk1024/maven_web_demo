package websocket;


import com.fasterxml.jackson.databind.ObjectMapper;
import domain.User;
import pojo.Message;
import utils.GetHttpSessionConfigurator;
import utils.MessageUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dgk1024
 * @date 2020/7/9 13:01
 */
@ServerEndpoint(value = "/chatWebsocket",configurator= GetHttpSessionConfigurator.class)
public class ChatWebSocket {
    /**存储用户名*/
    private String username;
    /**存储每一个客户端对应的ChatEndpoint对象*/
    private static Map<String, ChatWebSocket> onlineUsers=new ConcurrentHashMap<>();
    /**声明session对象,通过该对象可以发送消息给指定用户*/
    private Session session;
    /**声明HttpSession对象,通过该对象可以获取获取用户的名称*/
    private HttpSession httpSession;
    /**获取在线用户名*/
    private Set<String>getUsernames(){
        return onlineUsers.keySet();
    }
    /**通知所有在线用户*/
    private void sendAllUsers(String message){
        for(String user:getUsernames()){
            try {
                onlineUsers.get(user).session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 连接建立时被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        this.httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());
        User user = (User)this.httpSession.getAttribute("user");
        this.username = user.getUser_name();
        onlineUsers.put(this.username,this);
        String message = MessageUtils.getMessage(true,"showOnlineUsers",onlineUsers.keySet());
        sendAllUsers(message);
    }

    /**
     * 接收客户端消息时被调用
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session){
        System.out.println(message);
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message msg =mapper.readValue(message, Message.class);
            if("multiChat".equals(msg.getToName())){
                String returnMsg = MessageUtils.getMessage(
                        false,"multiChat","from_"+this.username+": "+msg.getMessage()
                );
                sendAllUsers(returnMsg);
            }
        } catch (IOException e) {
            System.out.println("onMessage中解析json错误");
        }
    }

    /**
     * 连接断开时被调用
     * @param session
     */
    @OnClose
    public void onClose(Session session){
        /**
         * 待解决
         * Caused by: java.lang.IllegalStateException: getAttribute: Session already invalidated
         * 	at org.apache.catalina.session.StandardSession.getAttribute(StandardSession.java:1178)
         * 	at org.apache.catalina.session.StandardSessionFacade.getAttribute(StandardSessionFacade.java:103)
         * 	at com.dgk1024.websocket.ChatWebSocket.onClose(ChatWebSocket.java:89)
         * 	... 19 more
         */
        //User user = (User)this.httpSession.getAttribute("user");
        //onlineUsers.remove(user.getUser_name());
        onlineUsers.remove(this.username);
        String message = MessageUtils.getMessage(true,"showOnlineUsers",onlineUsers.keySet());
        sendAllUsers(message);
    }
}