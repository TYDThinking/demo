package com.akun.demo.service;


import com.akun.demo.model.domain.UserFaceInfo;

public interface UserFaceInfoService {

    void insertSelective(UserFaceInfo userFaceInfo);
    /**
     * 根据id号查询人脸
     * @param id
     * @return
     */
    UserFaceInfo findUserFaceInfoById(Integer id);

    /**
     * 更新信息
     * @param userFaceInfo
     * @return
     */
    public int updateUserFaceInfoByMessage(UserFaceInfo userFaceInfo);
}
