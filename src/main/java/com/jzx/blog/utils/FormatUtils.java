package com.jzx.blog.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class FormatUtils {

    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return FormatUtils.getUUID() + FormatUtils.getSuffix(fileOriginName);
    }


    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }




    //  获取6为随机验证码
    public static String getRandomCode() {
        String[] letters = new String[] {
                "q","w","e","r","t","y","u","i","o","p","a","s","d","f","g","h","j","k","l","z","x","c","v","b","n","m",
                "A","W","E","R","T","Y","U","I","O","P","A","S","D","F","G","H","J","K","L","Z","X","C","V","B","N","M",
                "0","1","2","3","4","5","6","7","8","9"};//62个
        String code ="";
        for (int i = 0; i < 6; i++) {
            code = code + letters[(int)Math.floor(Math.random()*letters.length)];//Math.floor(Math.random()*62)会对由上面的语句产生的数值（0-62）进行向下取整
        }
        return code;
    }
    //获取当前日期
    public static String getNewDate() {

        Date dNow = new Date( );
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");

        return ft.format(dNow);
    }


    //1.获取当前系统时间格式化后给图片命名 2.yyyyMMddhhmmss
    public static String getDateName(){
        Date t = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddhhmmss");
        String time = sd.format(t);
        return time;
    }

    //1.获取当前系统时间格式化 yyyy-MM-dd hh:mm:ss
    public static String getDatetime(){
        Date t = new Date(System.currentTimeMillis());
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String time = sd.format(t);
        return time;
    }
}
