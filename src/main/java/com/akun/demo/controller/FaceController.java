package com.akun.demo.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import com.akun.demo.model.domain.UserFaceInfo;
import com.akun.demo.model.dto.FaceSearchResDto;
import com.akun.demo.model.dto.FaceUserInfo;
import com.akun.demo.model.dto.ProcessInfo;
import com.akun.demo.enums.ErrorCodeEnum;
import com.akun.demo.model.vo.FaceRequest;
import com.akun.demo.service.FaceEngineService;
import com.akun.demo.service.UserFaceInfoService;
import com.akun.demo.utils.JsonData;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/vakun/pri/face")
public class FaceController {

    public final static Logger logger = LoggerFactory.getLogger(FaceController.class);


    @Autowired
    FaceEngineService faceEngineService;

    @Autowired
    UserFaceInfoService userFaceInfoService;

    /*
    人脸添加
     */
    @RequestMapping(value = "faceAdd", method = RequestMethod.POST)
    public JsonData faceAdd(@RequestBody FaceRequest faceRequest, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        String file=faceRequest.getFile();
        Integer groupId =faceRequest.getGroupId();
//        System.out.println("groupId = " + groupId);
        String name=faceRequest.getName();
        try {
            if (file == null && "".equals(file)) {
                return JsonData.buildError("file is null");
            }
            if (groupId == null) {
                return JsonData.buildError("groupId is null");
            }
            if (name == null && "".equals(name)) {
                return JsonData.buildError("name is null");
            }
            byte[] decode = Base64.decode(base64Process(file));
            ImageInfo imageInfo = ImageFactory.getRGBData(decode);
            String str = ObjectUtils.toString(imageInfo);
            if(imageInfo==null &&StringUtils.isEmpty(str)){
                System.out.println("imageInfo = " + imageInfo);
                return JsonData.buildError("解析base64失败");
            }
            //人脸特征获取
            byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
            if (bytes == null) {
                return JsonData.buildError(ErrorCodeEnum.NO_FACE_DETECTED);
            }

            UserFaceInfo userFaceInfo = new UserFaceInfo();
            userFaceInfo.setId(userId);
            userFaceInfo.setName(name);
            userFaceInfo.setGroupId(groupId);
            userFaceInfo.setFaceFeature(bytes);
            userFaceInfo.setFaceId(RandomUtil.randomString(10));

           UserFaceInfo userFaceInfo1 = userFaceInfoService.findUserFaceInfoById(userId);
            if (userFaceInfo1!=null){
                int i = userFaceInfoService.updateUserFaceInfoByMessage(userFaceInfo);
                if (i==0){
                    return JsonData.buildError("更新失败");
                }
                System.out.println("faceAdd:" + name);
                logger.info("faceAdd:" + name);
                return JsonData.buildSuccess("更新成功");
            }
            //人脸特征插入到数据库
            userFaceInfoService.insertSelective(userFaceInfo);
            System.out.println("faceAdd:" + name);
            logger.info("faceAdd:" + name);
            return JsonData.buildSuccess("人脸注册成功");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("", e);
        }
        return JsonData.buildError(ErrorCodeEnum.UNKNOWN);
    }

    /*
    人脸识别
     */
    @RequestMapping(value = "faceSearch", method = RequestMethod.POST)
    public JsonData faceSearch(@RequestBody FaceRequest faceRequest) throws Exception {
        String file =faceRequest.getFile();
        Integer groupId=faceRequest.getGroupId();

        if (groupId == null) {
            return JsonData.buildError("groupId is null");
        }
        byte[] decode = Base64.decode(base64Process(file));
        BufferedImage bufImage = ImageIO.read(new ByteArrayInputStream(decode));
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(bufImage);
        if(imageInfo==null){
            System.out.println("imageInfo = " + imageInfo);
            return JsonData.buildError("解析base64失败");
        }

        //人脸特征获取
        byte[] bytes = faceEngineService.extractFaceFeature(imageInfo);
        if (bytes == null) {
            return JsonData.buildError(ErrorCodeEnum.NO_FACE_DETECTED);
        }
        //人脸比对，获取比对结果
        List<FaceUserInfo> userFaceInfoList = faceEngineService.compareFaceFeature(bytes, groupId);

        if (CollectionUtil.isNotEmpty(userFaceInfoList)) {
            FaceUserInfo faceUserInfo = userFaceInfoList.get(0);
            FaceSearchResDto faceSearchResDto = new FaceSearchResDto();
            BeanUtil.copyProperties(faceUserInfo, faceSearchResDto);
            List<ProcessInfo> processInfoList = faceEngineService.process(imageInfo);
            if (CollectionUtil.isNotEmpty(processInfoList)) {
                //人脸检测
                List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);
                int left = faceInfoList.get(0).getRect().getLeft();
                int top = faceInfoList.get(0).getRect().getTop();
                int width = faceInfoList.get(0).getRect().getRight() - left;
                int height = faceInfoList.get(0).getRect().getBottom() - top;

                Graphics2D graphics2D = bufImage.createGraphics();
                graphics2D.setColor(Color.RED);//红色
                BasicStroke stroke = new BasicStroke(5f);
                graphics2D.setStroke(stroke);
                graphics2D.drawRect(left, top, width, height);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufImage, "jpg", outputStream);
                byte[] bytes1 = outputStream.toByteArray();
                faceSearchResDto.setImage("data:image/jpeg;base64," + Base64Utils.encodeToString(bytes1));
                faceSearchResDto.setAge(processInfoList.get(0).getAge());
                faceSearchResDto.setGender(processInfoList.get(0).getGender().equals(1) ? "女" : "男");

            }
            String str = ObjectUtils.toString(faceSearchResDto);
            if (faceSearchResDto==null&&StringUtils.isEmpty(str)){
                return JsonData.buildError("该人脸未注册");
            }
            Integer similarry = faceSearchResDto.getSimilarValue();
            if (similarry<80){
                return JsonData.buildError("人脸不匹配");
            }
//            return JsonData.buildSuccess(faceSearchResDto);
            return JsonData.buildSuccess(similarry);
        }
        return JsonData.buildError(ErrorCodeEnum.FACE_DOES_NOT_MATCH);
    }

    @RequestMapping(value = "detectFaces", method = RequestMethod.POST)
    public List<FaceInfo> detectFaces(@RequestBody Map<String,Object> map) throws IOException {
        String image= (String) map.get("image");
        byte[] decode = Base64.decode(image);
        InputStream inputStream = new ByteArrayInputStream(decode);
        ImageInfo imageInfo = ImageFactory.getRGBData(inputStream);
        if(imageInfo==null){
            System.out.println("imageInfo = " + imageInfo);
            return null;
        }

        if (inputStream != null) {
            inputStream.close();
        }
        List<FaceInfo> faceInfoList = faceEngineService.detectFaces(imageInfo);

        return faceInfoList;
    }


    private String base64Process(String base64Str) {
        if (!StringUtils.isEmpty(base64Str)) {
            String photoBase64 = base64Str.substring(0, 30).toLowerCase();
            int indexOf = photoBase64.indexOf("base64,");
            if (indexOf > 0) {
                base64Str = base64Str.substring(indexOf + 7);
            }

            return base64Str;
        } else {
            return "";
        }
    }
}