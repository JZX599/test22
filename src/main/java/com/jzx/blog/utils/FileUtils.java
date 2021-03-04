package com.jzx.blog.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具包
 */
public class FileUtils {

    /*设置本地图片文件上传路径*/
    public static String getLocalPath(){
       String localPath= System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\ArtUpload";
        System.out.println("设置本地图片文件上传路径：   "+localPath);
        File file = new File(localPath);
        /*判断该路径的文件夹是否存在    如果不存在  自动创建文件夹*/
        if (!file.exists()){
            file.mkdirs();
        }
        return localPath;
    }

    /**
     *
     * @param file 文件
     * @param localPath 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static boolean upload(MultipartFile file, String localPath, String fileName){

        //使用原文件名
        String realPath = localPath + "/" + fileName;
        File dest = new File(realPath);
        //判断文件父目录是否存在
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }
        try {
        //保存文件
            file.transferTo(dest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}