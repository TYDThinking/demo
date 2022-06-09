package com.akun.demo.service;

import com.akun.demo.model.FileInfo;

/**
 * @Package: com.akun.demo.service
 * @ClassName: CreditorService
 * @Author: akun
 * @CreateTime: 2022/4/18 15:41
 * @Description:
 */
public interface FileService {

    FileInfo selectById(Integer id);

    int insertFileInfo(FileInfo fileInfo);

    int updateFileInfo(FileInfo fileInfo);

    int deleteFileById(Integer id);
}
