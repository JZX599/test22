package com.jzx.blog.service;

import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Category;

/*
    * 文章分类 服务层
    * */
public interface CategoryService {

    /*添加分类*/
    public Result saveCate(Category category);
    /*更新分类*/
    public Result updCate(Category category);

    /*分页查询所有文章类别*/
    public Result findAllPageArtList(int currentPage, Integer pageSize);

    /*通过id删除分类*/
    public Result delCate(Long cid);

    /*查询单个分类对象信息*/
    Result findByCid(Long cid);
}
