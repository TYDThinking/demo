package com.akun.demo.mapper;

import com.akun.demo.model.FileInfo;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

/**
 * @Package: com.akun.demo.mapper
 * @ClassName: FileInfoMapper
 * @Author: akun
 * @CreateTime: 2022/4/18 15:45
 * @Description:
 */
@Repository("FileInfoMapper")
public interface FileInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(FileInfo record);

    int insertSelective(FileInfo record);

    FileInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FileInfo record);

    int updateByPrimaryKey(FileInfo record);

}
