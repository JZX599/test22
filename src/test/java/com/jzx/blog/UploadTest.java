package com.jzx.blog;
import com.jzx.blog.service.ArticleService;
import com.jzx.blog.utils.FileUtils;
import com.jzx.blog.utils.FormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)//声明spring提供的单元测试环境
@SpringBootTest(classes = BlogApplication.class)//指定spring容器的配置信息
@RestController
public class UploadTest {

    @Autowired
    private ArticleService articleService;


    @PostMapping(path = "/ArtUpload")
    public String  fileLoad(HttpServletRequest request, MultipartFile multipartFile){
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        File file = new File(path);
        /*判断该路径的文件夹是否存在    如果不存在  自动创建文件夹*/
        if (!file.exists()){
            file.mkdirs();
        }
        /*获取前端传入的文件对象的名称*/
        String filename = multipartFile.getOriginalFilename();
        /*获取当前本机的随机UUID编码   并进行UUID格式修改   用于新生成图片名称*/
        String uuid = UUID.randomUUID().toString().replace("-", "");
        /*设置服务器上传的图片的新名称为  UUID编码加原来图片名*/
        filename=uuid+"_"+filename;
        try {
            multipartFile.transferTo(new File(path,filename));
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }

    }

    @PostMapping(path = "/ArtUpload2")
    public String  fileLoad2(@RequestParam("fileName") MultipartFile file, Map<String, Object> map){
        // 要上传的目标文件存放路径
        String localPath = System.getProperty("user.dir")+"/src/main/resources/static/files/ArtUpload";
// 上传成功或者失败的提示
        String msg = "";
        /*使用工具类  传入参数  前端文件对象，保存路径，文件名*/
        if (FileUtils.upload(file, localPath, file.getOriginalFilename())){
// 上传成功，给出页面提示
            msg = "上传成功！";
        }else {
            msg = "上传失败！";
        }
        // 显示图片    返回响应数据
        map.put("msg", msg);
        map.put("fileName", file.getOriginalFilename());
        return "forward:/test";
    }



        @Test
        public void test(){

        /*获取当前项目路径*/
            String property = System.getProperty("user.dir");
            System.out.println(property);

        }

        /*
        * 测试图片路径生成以及图片名称生成
        *
        * */

            @Test
            public void test1(){
                String localPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files\\ArtUpload";

                System.out.println(localPath);
                File file = new File(localPath);
                /*判断该路径的文件夹是否存在    如果不存在  自动创建文件夹*/
                if (!file.exists()){
                    file.mkdirs();
                }
                /*获取当前本机的随机UUID编码   并进行UUID格式修改   用于新生成图片名称*/
                /*设置服务器上传的图片的新名称为  UUID编码加原来图片名*/
                String s = FormatUtils.getUUID() + "_" + "图片.jpg";
                System.out.println(s);

            }

}
