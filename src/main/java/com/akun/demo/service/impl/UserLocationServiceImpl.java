package com.akun.demo.service.impl;

import com.akun.demo.mapper.UserLocationMapper;
import com.akun.demo.model.User;
import com.akun.demo.model.UserLocation;
import com.akun.demo.model.vo.UserLocationVO;
import com.akun.demo.service.UserLocationService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.akun.demo.service.impl
 * @ClassName: UserLocationServiceImpl
 * @Author: akun
 * @CreateTime: 2022/5/6 11:36
 * @Description:
 */
@Service("UserLocationServiceImpl")
public class UserLocationServiceImpl implements UserLocationService {

    @Autowired
    private UserLocationMapper userLocationMapper;

    //根据打卡id查询其详细信息
    @Override
    public UserLocation selectUserLocationById(Integer id) {
        return userLocationMapper.selectByPrimaryKey(id);
    }

    //更新打卡信息
    @Override
    public int updateUserLocation(UserLocationVO userLocationVO) {
        UserLocation userLocation = new UserLocation(userLocationVO.getId(),userLocationVO.getLongitude(),userLocationVO.getLatitude(),userLocationVO.getMorningTemperature(),userLocationVO.getMiddayTemperature(),userLocationVO.getNightTemperature(),userLocationVO.getCurrentHealthStatus(),userLocationVO.getInstructions());
        User user = new User();
        user.setId(userLocationVO.getUserId());
        userLocation.setUser(user);
        return userLocationMapper.updateByPrimaryKeySelective(userLocation);
    }
    //插入打卡信息
    @Override
    public int insertUserByUserLocation(UserLocationVO userLocationVO) {
        UserLocation userLocation = new UserLocation(userLocationVO.getLongitude(),userLocationVO.getLatitude(),userLocationVO.getMorningTemperature(),userLocationVO.getMiddayTemperature(),userLocationVO.getNightTemperature(),userLocationVO.getCurrentHealthStatus(),userLocationVO.getInstructions());
        User user = new User();
        user.setId(userLocationVO.getUserId());
        System.out.println("user = " + user);
        userLocation.setUser(user);
        System.out.println("userLocation = " + userLocation);
        return userLocationMapper.insert(userLocation);
    }
    //删除打卡信息根据id
    @Override
    public int delUserByUserLocationId(Integer userId) {
        return userLocationMapper.deleteByPrimaryKey(userId);
    }
}
