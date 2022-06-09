package com.akun.demo.utils;

import com.akun.demo.model.User;
import com.akun.demo.model.UserMessage;
import com.akun.demo.model.vo.MessageRequest;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @Package: com.akun.demo.utils
 * @ClassName: NullUtils
 * @Author: akun
 * @CreateTime: 2022/4/24 16:12
 * @Description:
 */
public class NullUtils {
    /**
     * 获取属性名数组
     */
    private static String[] getFiledName(Object o) {
        //获取对象的属性数组
        Field[] fields = o.getClass().getDeclaredFields();
        //获取属性名称
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            //获取属性的类型
//            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /* 根据属性名获取属性值
     *
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {

            return null;
        }
    }

    public static void panduanUsermessage(MessageRequest message, HttpServletRequest request) {
        UserMessage userMessage = new UserMessage();
        String number = (String) request.getAttribute("number");
        Integer userId = (Integer) request.getAttribute("user_id");
        userMessage.setId(userId);
        userMessage.setNumber(number);
        userMessage.setAge(message.getAge());
        userMessage.setEmail(message.getEmail());
        userMessage.setGender(message.getGender());
        userMessage.setGroupId(message.getGroupId());
        userMessage.setHomeAddress(message.getHomeAddress());
        userMessage.setCurrentAddress(message.getCurrentAddress());
        userMessage.setRealName(message.getRealName());

        String[] fieldNames = getFiledName(userMessage);
        for (int j = 0; j < fieldNames.length; j++) {     //遍历所有属性
            String name = fieldNames[j];    //获取属性的名字
            //判断需要判断的对象某些属性（字段名称）
            if("age".equals(name)||"email".equals(name)||"number".equals(name)
                    ||"gender".equals(name)||"groupId".equals(name)||"homeAddress".equals(name)
                    ||"currentAddress".equals(name)){
                //根据字段获取值
                Object value = getFieldValueByName(name, userMessage);
                if(value==null){
                    //如果对象的这些属性为空,然后进行的操作...
                    System.out.println(name);
                }
            }


        }

    }

}
