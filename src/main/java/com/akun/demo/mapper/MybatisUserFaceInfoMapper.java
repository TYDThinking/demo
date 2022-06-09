package com.akun.demo.mapper;


import com.akun.demo.model.domain.UserFaceInfo;
import com.akun.demo.model.dto.FaceUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("MybatisUserFaceInfoMapper")
public interface MybatisUserFaceInfoMapper {

    List<UserFaceInfo> findUserFaceInfoList();

    //2、根据手机号查询用户信息
    UserFaceInfo findUserFaceInfoById(Integer id);
    //更新信息
    int updateUserFaceInfoByMessage(UserFaceInfo userFaceInfo);

    void insertUserFaceInfo(UserFaceInfo userFaceInfo);

    List<FaceUserInfo> getUserFaceInfoByGroupId(Integer groupId);
}
