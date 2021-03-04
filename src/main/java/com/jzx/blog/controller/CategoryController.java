package com.jzx.blog.controller;


import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Category;
import com.jzx.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;



    /*
    * 执行添加文章分类
    * 添加校验@Validated
    *
    * 并在实体类中定义校验输出信息
    * @NotBlank(message = "类别名称不能为空！")
    * */
    @PostMapping(path = "/saveCate")
    public Result saveCate(@Validated @RequestBody Category category){
        System.out.println(category);
        Result result = categoryService.saveCate(category);
        System.out.println("添加文章分类返回"+result);
        return result;
    }



    /*
     * 执行更新文章分类
     * 添加校验@Validated
     *
     * 并在实体类中定义校验输出信息
     * @NotBlank(message = "类别名称不能为空！")
     * */
    @PostMapping(path = "/updCateInfo")
    public Result updCateInfo(@Validated @RequestBody Category category){
        System.out.println(category);
        Result result = categoryService.updCate(category);
        System.out.println("更新文章分类返回"+result);
        return result;
    }

    /*
    * 分页查询所有文章类别
    *
    *
    *
    * */

    @GetMapping(path = "/findAllPageCateList/{currentPage}/{pageSize}")
    public Result findAllPageCateList(@PathVariable(value = "currentPage")Integer currentPage,
                                     @PathVariable(value = "pageSize")Integer pageSize) {

        Result pageCateList = categoryService.findAllPageArtList(currentPage - 1, pageSize);
        System.out.println(currentPage+"-----------查询所有分页文章分类数据-----------"+pageSize);
        System.out.println(pageCateList);
        return pageCateList;

    }

    /*
    * 删除单个文章类别
    *
    *@RequestParam请求参数
    *
    *
    * */
    @DeleteMapping(path = "/delCate/{cid}")
    public Result delCate(@PathVariable(value = "cid") Long cid){
       try {
           Result result = categoryService.delCate(cid);
           return result;
       }catch (Exception e){
           e.printStackTrace();
           return Result.fail(400,"删除分类失败");
       }
    }


    /*
     * 分页查询所有文章类别
     *
     *
     *
     * */

    @GetMapping(path = "/getCateinfoByCid/{cid}")
    public Result getCateinfoByCid(@PathVariable(value = "cid")Long cid) {
        Result cateInfo = categoryService.findByCid(cid);
        System.out.println(cateInfo+"单个分类对象信息-----------"+cid);
        return cateInfo;

    }


}
