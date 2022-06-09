package com.akun.demo.service.impl;

import com.akun.demo.mapper.FileInfoMapper;
import com.akun.demo.model.FileInfo;
import com.akun.demo.service.FileService;
import com.akun.demo.utils.FastDFSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Package: com.akun.demo.service.impl
 * @ClassName: FileServiceImpl
 * @Author: akun
 * @CreateTime: 2022/4/18 15:42
 * @Description: 上传文件（这里上传照片）
 */
@Service("FileServiceImpl")
public class FileServiceImpl implements FileService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public FileInfo selectById(Integer id) {
        FileInfo creditorInfo = fileInfoMapper.selectByPrimaryKey(id);
        return creditorInfo;
    }
    @Override
    public int insertFileInfo(FileInfo fileInfo){
        return  fileInfoMapper.insertSelective(fileInfo);
    }

    @Override
    public int updateFileInfo(FileInfo fileInfo) {
        return   fileInfoMapper.updateByPrimaryKeySelective(fileInfo);
    }

    @Override
    public int deleteFileById(Integer id) {
        //查询文件信息
        FileInfo creditorInfo = fileInfoMapper.selectByPrimaryKey(id);
        //删除文件
        FastDFSUtil.fileDelete(creditorInfo.getGroupName(),creditorInfo.getRemoteFilePath());

        //更新数据库信息为空
        creditorInfo.setRemoteFilePath("");
        creditorInfo.setGroupName("");
        creditorInfo.setOldFilename("");
        creditorInfo.setFileType("");
        creditorInfo.setFileSize(0L);
        return fileInfoMapper.updateByPrimaryKeySelective(creditorInfo);
    }
}
