package com.akun.demo.controller;

import com.akun.demo.model.User;
import com.akun.demo.model.UserMessage;
import com.akun.demo.model.vo.LoginRequestByE;
import com.akun.demo.model.vo.LoginRequestByN;
import com.akun.demo.model.vo.MessageRequest;
import com.akun.demo.service.UserService;
import com.akun.demo.service.impl.UserMessageServiceImpl;
import com.akun.demo.service.util.PhoneCodeUtils;
import com.akun.demo.service.util.TokenUtils;
import com.akun.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Package: com.akun.demo.controller
 * @ClassName: UserController
 * @Author: akun
 * @CreateTime: 2022/4/13 23:04
 * @Description:
 */
@RestController
@RequestMapping("api/vakun/pri/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @Autowired
//    private EmailCodeUtils emailCodeUtils;

    @Autowired
    private UserMessageServiceImpl userMessageService;
    @Autowired
    private PhoneCodeUtils phoneCodeUtils;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     *  完善信息
     * @param message
     * @param request
     * @return
     */
    @RequestMapping(value = "userMessage", method = RequestMethod.POST)
    public JsonData usermessage(@RequestBody MessageRequest message, HttpServletRequest request){
        UserMessage userMessage = new UserMessage();
        String number = (String) request.getAttribute("number");
        Integer userId = (Integer) request.getAttribute("user_id");

        System.out.println("userId = " + userId);
        System.out.println("number = " + number);

        if (userId==null){
            return JsonData.buildError("userId为空");
        }
        userMessage.setId(userId);
        userMessage.setNumber(number);
        userMessage.setAge(message.getAge());
        userMessage.setEmail(message.getEmail());
        userMessage.setGender(message.getGender());
        userMessage.setGroupId(message.getGroupId());
        userMessage.setHomeAddress(message.getHomeAddress());
        userMessage.setCurrentAddress(message.getCurrentAddress());
        userMessage.setRealName(message.getRealName());

        int ii =  userMessageService.updateUserMessage(userMessage);
        if (ii==0){
            int i = userMessageService.insertUserByUserMessage(userMessage);

            if (i==0){
                return JsonData.buildError("完善数据失败");
            }
        }
        System.out.println("完善信息成功");
        return JsonData.buildSuccess("完善信息成功",userMessage);
    }


    /**
     *  注册接口
     * @param userInfo
     * @return
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JsonData register(@RequestBody Map<String,String> userInfo){
        if (!userInfo.get("password").equals(userInfo.get("password1"))){
            return JsonData.buildError("两次密码不一致");
        }
        User user = new User();
        user.setUsername(userInfo.get("username"));
        user.setPassword(userInfo.get("password"));
//        user.setEmail(userInfo.get("email"));
        user.setNumber(userInfo.get("number"));
        user.setAvatar("https://img0.baidu.com/it/u=1343856373,4207068692&fm=253&fmt=auto&app=120&f=JPEG?w=640&h=640");
        String code = userInfo.get("code");

        //redis验证code是否一样  1 code一样 0 code不一致
        int inspetionValue = phoneCodeUtils.getRedisCode(userInfo.get("number"),code);
        if (inspetionValue==2||inspetionValue==0){
            return JsonData.buildError("验证码错误");
        }
        int rows = userService.insUserByUser(user);
        if (rows==0){
            return JsonData.buildError("该手机号已经注册");
        }
//        if(rows==2){
//            return JsonData.buildError("该邮箱已经注册");
//        }
        return rows == 1 ? JsonData.buildSuccess("注册成功"): JsonData.buildError("注册失败，请重试");
    }


    /**
     * 登陆接口 By number（手机号）
     *
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "loginByN", method = RequestMethod.POST)
    public JsonData loginBynumber(@RequestBody LoginRequestByN loginRequest) {
        if(loginRequest.getNumber()==null &&"".equals(loginRequest.getNumber())){
            return JsonData.buildError("手机号为空");
        }
        if (loginRequest.getPassword()==null&&"".equals(loginRequest.getPassword())){
            return JsonData.buildError("密码为空");
        }
        String token = userService.findByNumberAndPwd(loginRequest.getNumber(), loginRequest.getPassword());
        if (token==null){
           return JsonData.buildError("登陆失败。账号密码错误");
        }
        boolean i = tokenUtils.savetoken(loginRequest.getNumber(),token);

        return i == false ? JsonData.buildError("登陆失败。账号密码错误") : JsonData.buildSuccess(token);
    }

    /**
     * 登陆接口 By email（邮箱）
     *
     * @param loginRequest
     * @return
     */
    @RequestMapping(value = "loginByE", method = RequestMethod.POST)
    public JsonData loginByemail(@RequestBody LoginRequestByE loginRequest) {
        String token = userService.findByEmailAndPwd(loginRequest.getEmail(), loginRequest.getPassword());
        return token == null ? JsonData.buildError("登陆失败。账号密码错误") : JsonData.buildSuccess(token);
    }

    /**
     * 更新数据（照片）
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "updateMessageAvatar",method = RequestMethod.POST)
    public JsonData updateMessageAvatar(@RequestBody Map<String,String> userInfo, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        String avatar = userInfo.get("avatar");
        User user = userService.queryUserById(userId);
        user.setAvatar(avatar);
        int i=  userService.updateUserInfo(user);
        return i==0 ?JsonData.buildError("上传头像信息失败，请重新尝试") : JsonData.buildSuccess();
    }
    /**
     * 更新数据（部门信息Id）
     * @param userInfo
     * @param request
     * @return
     */
    @RequestMapping(value = "updateMessageGroupId",method = RequestMethod.POST)
    public JsonData updateMessageGroupId(@RequestBody Map<String,String> userInfo, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        String groupId = userInfo.get("groupId");
        System.out.println("groupId = " + groupId);
        System.out.println("int类型的groupId"+Integer.parseInt(groupId));
        User user = userService.queryUserById(userId);

        user.setGroupId(Integer.parseInt(groupId));
        int i=  userService.updateUserInfo(user);
        return i==0 ?JsonData.buildError("更新部门信息失败，请重新尝试") : JsonData.buildSuccess();
    }

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    @RequestMapping(value = "findUser",method = RequestMethod.GET)
    public JsonData userMessage(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        User user = userService.queryUserById(userId);
        user.setPassword("密码是保密信息不可查看");
        return JsonData.buildSuccess(user);
    }
    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public JsonData userLogout(HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        String phone = (String) request.getAttribute("number");
//        User user = userService.queryUserById(userId);
//        user.setPassword("密码是保密信息不可查看");
        boolean i = tokenUtils.deletetoken(phone);
        return i == false ? JsonData.buildError("退出登陆失败") : JsonData.buildSuccess("退出登录成功");
    }
}