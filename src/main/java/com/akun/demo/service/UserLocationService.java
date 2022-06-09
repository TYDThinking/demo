package com.akun.demo.service;

import com.akun.demo.model.UserLocation;
import com.akun.demo.model.vo.UserLocationVO;

/**
 * @Package: com.akun.demo.service
 * @ClassName: UserLocationService
 * @Author: akun
 * @CreateTime: 2022/5/6 11:33
 * @Description:
 */
public interface UserLocationService {

    //3、查询呼应：通过Id
    UserLocation selectUserLocationById(Integer id);

    //4、调用更新UserLocation对象的：业务层代码
    int updateUserLocation(UserLocationVO userLocationVO);

    //5、添加用户：通过UserLocation对象进行添加
    int insertUserByUserLocation(UserLocationVO userLocationVO);

    //6、删除用户：通过userId
    int delUserByUserLocationId(Integer userId);
}
