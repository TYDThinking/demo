package com.akun.demo.controller;



import com.akun.demo.model.FileInfo;
import com.akun.demo.model.User;
import com.akun.demo.service.FileService;
import com.akun.demo.service.UserService;
import com.akun.demo.utils.FastDFSUtil;
import com.akun.demo.utils.JsonData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * @Package: com.akun.demo.controller
 * @ClassName: FileController
 * @Author: akun
 * @CreateTime: 2022/4/18 9:41
 * @Description:
 */

@RestController
@RequestMapping("api/vakun/pri/file")
public class FileController {

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;

    @Value("${user.file.updatePath}")
    private String path;

    /**
     * 文件上传
     * 参数为MltipartFile 为Spring提供的一个类，专门用于封装请求中的文件数据
     * 属性名必须与表单文件域的名字完全相同否则无法获取文件数据
     * @param myFile
     * @return
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public JsonData upload(@RequestParam MultipartFile myFile, HttpServletRequest request) throws IOException {
        System.out.println("upload的控制层");
        if (Objects.nonNull(myFile) && myFile.isEmpty()){
            return JsonData.buildError("controller层出现问题，未获取到文件");
        }
        Integer id = (Integer) request.getAttribute("user_id");
        if (id==null){
            return JsonData.buildError("controller层出现问题，未获取到Id值");
        }
        //获取文件对应的字节数组流
        byte[] buffFile = myFile.getBytes();
        //获取文件名
        String fileName = myFile.getOriginalFilename();
        Long fileSize = myFile.getSize();
        String fileType = myFile.getContentType();
        //可能会出现有些文件可能没有扩展名，因此必要时需要做逻辑控制
        String fileExtname = fileName.substring(fileName.lastIndexOf(".")+1);
        String [] result = FastDFSUtil.fileUpload(buffFile,fileExtname);
        System.out.println("result = " + result);
        for (String str:result){
            System.out.println("str = " + str);
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(id);
        fileInfo.setFileSize(fileSize);
        fileInfo.setFileType(fileType);
        fileInfo.setOldFilename(fileName);
        fileInfo.setGroupName(result[0]);
        fileInfo.setRemoteFilePath(result[1]);

        int i = fileService.updateFileInfo(fileInfo);
        if (i==0){
            fileService.insertFileInfo(fileInfo);
        }

        String avatar = path+result[0]+"/"+result[1];
        System.out.println("avatar = " + avatar);
        User user = userService.queryUserById(id);
        if (user==null){
            return JsonData.buildError("在数据库，为查到该用户，更新头像信息失败");
        }
        user.setAvatar(avatar);
        int i1 =  userService.updateUserInfo(user);
        return i1==0 ?JsonData.buildError("上传头像信息失败，请重新尝试") : JsonData.buildSuccess("上传成功",user.getAvatar());
    }

    /**
     * 完成文件下载
     * @param id  需要下载文件的主键
     * @return  ResponseEntity 表示一个响应的实体，这个类是spring提供的一个类，这个类是spring响应数据时的一个对象
     *          这个对象用包含则 应的头文件信息，以及响应时的具体数据
     *          这个数据可以是一段htmL代码，也可以是一段js，也可以是一段普通字符串，也可以是一个文件的流
     */
    @RequestMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Integer id){

        FileInfo fileInfo = fileService.selectById(id);
        byte[] buffFile = FastDFSUtil.fileDownload(fileInfo.getGroupName(),fileInfo.getRemoteFilePath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);//设置响应类型为文件类型
        headers.setContentLength(fileInfo.getFileSize());//设置响应时的文件大小用于自动提供下载进度
        //设置下载时的默认文件名
        headers.setContentDispositionFormData("attachment",fileInfo.getOldFilename());
        /**
         * 创建响应实体对象，Spring会将这个对象返回给浏览器，作为响应数据
         * 参数 1 为响应时的具体数据
         * 参数 2 为响应时的头文件信息
         * 参数 3 为响应时的状态码
         */
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(buffFile,headers, HttpStatus.OK);
        return responseEntity;
    }

    @RequestMapping("/delete/{id}")
    public  JsonData delete(@PathVariable Integer id){
        int i =   fileService.deleteFileById(id);
        if (i==0){
            return JsonData.buildError("删除失败");
        }
        return JsonData.buildSuccess();
    }
}
