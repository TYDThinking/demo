package com.akun.demo.face;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


public class test {


    public static void main(String[] args) {
        String appId = "2zuSCqcSapLoxoMeNKr5qc53WqHYX1eb1gtJSjLvaGk7";
        String sdkKey ="6uhRUfFbwgfS7zeu7e7KcrjM9jG5Kn1zKUm4iqWYqywe";

//        String path = "F:\\Downloads\\编程\\Java\\虹软人脸识别java-sdk\\测试照片\\";

//        FaceEngine faceEngine = new FaceEngine("F:\\Downloads\\编程\\Java\\虹软人脸识别java-sdk\\arcsoft_lib");
        FaceEngine faceEngine = new FaceEngine("/usr/local/software/api-demo-face/arcsoft_lib/arcsoft_lib");

        //激活引擎
        int errorCode = faceEngine.activeOnline(appId,sdkKey);

        if (errorCode != ErrorInfo.MOK.getValue() && errorCode !=  ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()){
            System.out.println("获取激活文件信息失败");
        }
        //引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportAge(true);
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportGender(true);
        functionConfiguration.setSupportLiveness(true);
        functionConfiguration.setSupportIRLiveness(true);
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()){
            System.out.println("初始化引擎失败");
        }

        //人脸检测
        ImageInfo imageInfo = getRGBData(new File("/usr/local/software/api-demo-face/image/test1.jpg"));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo.getImageData(),imageInfo.getWidth(),imageInfo.getHeight(),imageInfo.getImageFormat(),faceInfoList);
        System.out.println("人脸检测faceInfoList = " + faceInfoList);

        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        System.out.println("特征值大小：" + faceFeature.getFeatureData().length);

        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File("/usr/local/software/api-demo-face/image/test2.jpg"));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = faceEngine.detectFaces(imageInfo2.getImageData(),imageInfo2.getWidth(),imageInfo2.getHeight(),imageInfo2.getImageFormat(),faceInfoList2);
        System.out.println("人脸检测faceInfoList = " + faceInfoList2);

        //特征提取
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo2.getImageFormat(), faceInfoList2.get(0), faceFeature2);
        System.out.println("特征值大小：" + faceFeature2.getFeatureData().length);

        //特征对比
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = faceEngine.compareFaceFeature(targetFaceFeature,sourceFaceFeature,faceSimilar);

        System.out.println("相似度：" + faceSimilar.getScore());


        //引擎卸载
        errorCode = faceEngine.unInit();
    }
}
