package com.akun.demo.service.impl;


import com.akun.demo.model.domain.UserFaceInfo;
import com.akun.demo.mapper.MybatisUserFaceInfoMapper;
import com.akun.demo.service.UserFaceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("UserFaceInfoServiceImpl")
public class UserFaceInfoServiceImpl implements UserFaceInfoService {


    @Autowired
    private MybatisUserFaceInfoMapper userFaceInfoMapper;

    @Override
    public void insertSelective(UserFaceInfo userFaceInfo) {
        userFaceInfoMapper.insertUserFaceInfo(userFaceInfo);
    }

    /**
     * 根据id号查询人脸
     * @param id
     * @return
     */
    public UserFaceInfo findUserFaceInfoById(Integer id){
      return   userFaceInfoMapper.findUserFaceInfoById(id);
    }

    /**
     * 更新信息
     * @param userFaceInfo
     * @return
     */
    public int updateUserFaceInfoByMessage(UserFaceInfo userFaceInfo){
        return userFaceInfoMapper.updateUserFaceInfoByMessage(userFaceInfo);
    }
}
