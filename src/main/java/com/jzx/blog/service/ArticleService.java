package com.jzx.blog.service;

    /*
    * 文章  服务层
    *
    * */

import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Article;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;

public interface ArticleService {
    /*通过id查新单条文章数据*/
    public Article findOneArt(Long AId);

    /*查询所有文章列表  前端调用此方法查询所有文章*/
    public List<Article> findAllArt();

    /*查询所有文章分页列表*/
    public Page<Article> findAllPage(Integer currentPage, Integer pageSize);

    /*查询所有文章分页列表加结果集封装   页面数据、总记录数、当前页总记录数、当前页*/
    public Result findAllPageArtList(int currentPage, Integer pageSize);

    /*通过字符串转long集合 批量删除文章数据*/
    public Result deleteCheckArtListIn(String aids);

    /*添加文章操作*/
    public Result saveArt(HashMap<String, String> map);

    /*查询单个文章信息*/
    public Result getArtinfo(Long aid);

    /*更新文章操作*/
    public Result updArt(HashMap<String, String> map);
}
