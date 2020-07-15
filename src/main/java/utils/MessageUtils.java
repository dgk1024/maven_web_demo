package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import pojo.ResultMessage;

public class MessageUtils {
    public static String getMessage(boolean isSystemMessage,String fromName,Object message){
        try {
            //将对象封装成json格式数据
            ResultMessage resultMessage = new ResultMessage();
            resultMessage.setIsSystem(isSystemMessage);
            resultMessage.setMessage(message);
            if(fromName!=null){
                resultMessage.setFromName(fromName);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(resultMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
