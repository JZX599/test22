package com.jzx.blog.controller;


import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Article;
import com.jzx.blog.service.ArticleService;
import com.jzx.blog.utils.FileUtils;
import com.jzx.blog.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /*
    * 1、测试方法
    *
    * */
    @GetMapping(path = "/test")
    public Article tset(){
        Article article = new Article();
        article.setAContent("haha");
        article.setAName("666666");
        article.setARtime(new Date());
        article.setAId(1L);
        return article;
    }


    /*
     * 2、根据id查询单条文章
     *
     *getOne懒加载（需要配置事务）  使用json返回会出现报错信息有待研究
     * findOne立即加载
     * */
    @GetMapping(path = "/findOneArt/{AId}")
    public Article findOneArt(@PathVariable(value = "AId")Long AId) {
        System.out.println(AId+"----------------------------------9999999999999-----------------");
        Article article = articleService.findOneArt(AId);
        System.out.println(article);
        return article;
    }


    /*
    * 3、查询所有文章
    * */
    @GetMapping(path = "/findAllArt")
    public List<Article> findAllArt() {
        System.out.println("----------------------------------9999999999999-----------------");
        List<Article> artList = articleService.findAllArt();
        for (Article article : artList) {
            System.out.println(article);
        }

        return artList;
    }


    /*
    * 添加单个文章
    *
    * */

    /*
     * 3、查询所有文章     前端调用此方法查询所有文章
     * */
    @GetMapping(path = "/findAllArtList")
    public Result findAllArtList() {
        System.out.println("----------------------------------9999999999999-----------------");
        List<Article> artList = articleService.findAllArt();

        for (Article article : artList) {
            System.out.println(article);
        }
        return  Result.success(artList);
    }


    /*
     * 3、查询所有文章并进行分页     前端调用此方法查询所有文章并进行分页
     * */
    @GetMapping(path = "/findAllPageArtList")
    public Result findAllPageArtList() {
      List<Article> artList = articleService.findAllArt();


        for (Article article : artList) {
            System.out.println(article);
        }
        return  Result.success(artList);
    }

    @GetMapping(path = "/findAllPage/{currentPage}/{pageSize}")
    public Page<Article> findAllPage(@PathVariable(value = "currentPage")Integer currentPage,
                                            @PathVariable(value = "pageSize")Integer pageSize) {

        Page<Article> list = articleService.findAllPage(currentPage-1, pageSize);


        System.out.println(list);
        return list;
    }

    /*
     * 3、查询所有文章并进行分页     前端调用此方法查询所有文章并进行分页  最终
     * */
    @GetMapping(path = "/findAllPageArtList/{currentPage}/{pageSize}")
    public Result findAllPageArtList(@PathVariable(value = "currentPage")Integer currentPage,
                                            @PathVariable(value = "pageSize")Integer pageSize) {

        Result pageArtList = articleService.findAllPageArtList(currentPage - 1, pageSize);
        System.out.println(currentPage+"-----------9999999999999-----------"+pageSize);
        System.out.println();
        System.out.println(pageArtList);
        return pageArtList;

    }


    /*
    * 执行批量删除
    *
    *
    * */
    @DeleteMapping(path = "/deleteCheckArtListIn/{aids}")
    public Result deleteCheckArtList(@PathVariable(value = "aids")String aids){
        Result result = articleService.deleteCheckArtListIn(aids);
        return result;
    }


    /*
    *
    * 保存文章
    * 注意前端使用的是表单形式进行json提交   后台必须使用请求体方式进行取值
    * 请求为hashmap形式   并通过键名  取得对应键值
    *
    * */

    @PostMapping(path = "/saveMd")
    public Result saveMd(@RequestBody HashMap<String, String> map){


        Result result = articleService.saveArt(map);

        return result;

    }


    /*
    * 图片上传
    *
    *
    * */

    @PostMapping(path = "/uploadFile")
    public Result uploadFile(@RequestParam("image") MultipartFile imgfile, HttpServletRequest request){
// 本地项目图片文件夹路径
        String localPath = FileUtils.getLocalPath();
        String newImgName = FormatUtils.getUUID() + "_" + imgfile.getOriginalFilename();

        if (FileUtils.upload(imgfile, localPath,newImgName)){
// 上传成功，给出页面提示

            /*图片上传的服务器相对路径*/
            String returnUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/files/ArtUpload/";//存储路径
            System.out.println("服务器图片文件夹路径:  "+returnUrl);
//必须使用相对路径返回     返回服务器图片路径  不能使用本地图片路径
            String imgRelativePaht =returnUrl+ newImgName;
            System.out.println("服务器图片路径:  "+imgRelativePaht);

           return Result.succ(200,"图片上传成功",imgRelativePaht);

        }else {
            System.out.println("上传失败");
          return   Result.fail(200,"图片上传失败");
        }

    }


    /*
    *
    * 查询单个文章信息
    *
    * */

    @GetMapping(path = "/getArtinfo/{aid}")
    public Result getArtinfo(@PathVariable(value = "aid")Long aid) {

        Result result = articleService.getArtinfo(aid);

        return result;

    }


    /*
     *
     * 更新文章
     * 注意前端使用的是表单形式进行json提交   后台必须使用请求体方式进行取值
     * 请求为hashmap形式   并通过键名  取得对应键值
     *
     * */

    @PostMapping(path = "/updMd")
    public Result updMd(@RequestBody HashMap<String, String> map){


        Result result = articleService.updArt(map);

        return result;

    }


}
