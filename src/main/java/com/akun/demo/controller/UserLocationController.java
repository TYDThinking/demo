package com.akun.demo.controller;

import com.akun.demo.model.User;
import com.akun.demo.model.UserLocation;
import com.akun.demo.model.vo.UserLocationVO;
import com.akun.demo.service.UserLocationService;
import com.akun.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Package: com.akun.demo.controller
 * @ClassName: UserLocationController
 * @Author: akun
 * @CreateTime: 2022/5/6 12:01
 * @Description:
 */
@RestController
@RequestMapping("api/vakun/pri/clocking")
public class UserLocationController {

    @Autowired
    private UserLocationService userLocationService;

    /**
     * 根据id查询打卡信息***
     * @param id
     * @return
     */
    @RequestMapping(value = "selectUserLocationById", method = RequestMethod.GET)
    public JsonData selectUserLocationById(@RequestParam("id") Integer id){
        if (id==null&& id==0){
            return JsonData.buildError("id值为空");
        }
        UserLocation userLocation = userLocationService.selectUserLocationById(id);

        return userLocation==null?JsonData.buildError("获取信息失败"):JsonData.buildSuccess(userLocation);
    }

    /**
     * 添加打卡信息
     * @param userLocationVO
     * @param request
     * @return
     */
    @RequestMapping(value = "addUserLocation", method = RequestMethod.POST)
    public JsonData addUserLocation (@RequestBody UserLocationVO userLocationVO, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        userLocationVO.setUserId(userId);
        System.out.println("userId = " + userId);
        int i = userLocationService.insertUserByUserLocation(userLocationVO);

        return i==0?JsonData.buildError("插入失败"):JsonData.buildSuccess();
    }

    /**
     * 更新打卡信息
     * @param userLocationVO
     * @param request
     * @return
     */
    @RequestMapping(value = "updateUserLocation", method = RequestMethod.POST)
    public JsonData updateUserLocation (@RequestBody UserLocationVO userLocationVO, HttpServletRequest request){
        Integer userId = (Integer) request.getAttribute("user_id");
        userLocationVO.setUserId(userId);

        int i = userLocationService.updateUserLocation(userLocationVO);

        return i==0?JsonData.buildError("插入失败"):JsonData.buildSuccess();
    }

    /**
     * 删除打卡信息
     * @param map
     * @return
     */
    @RequestMapping(value = "deleteUserLocation", method = RequestMethod.POST)
    public JsonData deleteUserLocation (@RequestBody Map<String,Object> map){
        String idString = (String) map.get("id");
        if (idString==null&&"".equals(idString)){
            return JsonData.buildError("id值为空");
        }
        Integer id = Integer.parseInt(idString);
        int i = userLocationService.delUserByUserLocationId(id);
        return i==0? JsonData.buildError("删除打卡信息失败"): JsonData.buildSuccess(i);
    }
}
