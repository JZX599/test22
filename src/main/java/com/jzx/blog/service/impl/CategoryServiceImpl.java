package com.jzx.blog.service.impl;

import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Category;
import com.jzx.blog.mapper.CategoryMapper;
import com.jzx.blog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    @Override/*添加分类*/
    public Result saveCate(Category category) {

        Result result = Result.succ(200, "添加分类成功", categoryMapper.save(category));
        return result;
    }
    @Override/*修改更新分类*/
    public Result updCate(Category category) {


        Result result = Result.succ(200, "更新分类成功", categoryMapper.save(category));
        return result;
    }

    @Override/*分页查询所有文章类别*/
    public Result findAllPageArtList(int currentPage, Integer pageSize) {
        // 创建分页操作对象 取得pageBean对象
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        // 查询所有分类数据  并通过分页对象来执行 可以取得  当前页 当页数据总数  总记录数   当页分类数据等
        Page<Category> page = categoryMapper.findAll(pageable);
        Result result = Result.succPage(200, "查询文章分类数据成功", page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
        return result;
    }

    @Override// 删除单个类别
    public Result delCate(Long cid) {
        categoryMapper.deleteById(cid);
        return Result.success(200,"删除单个分类成功");
    }

    @Override// 查询单个分类信息
    public Result findByCid(Long cid) {

        Category cate = categoryMapper.findOne(cid);
        return Result.succ(200,"查询单个分类信息成功",cate);
    }
}
