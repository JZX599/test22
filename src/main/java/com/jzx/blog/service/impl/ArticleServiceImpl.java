package com.jzx.blog.service.impl;


import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Article;
import com.jzx.blog.mapper.ArticleMapper;
import com.jzx.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public Article findOneArt(Long AId) {
        Article article = articleMapper.findOne(AId);
        return article;
    }


    @Override/*前端调用此方法查询所有文章*/
    public List<Article> findAllArt() {
        List<Article> articleList = articleMapper.findAll();
        return articleList;
    }

    @Override/*查询所有文章分页列表*/
    public Page<Article> findAllPage(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Article> page=articleMapper.findAll(pageable);
        return page;
    }

    @Override /*查询所有文章分页列表加结果集封装   页面数据、总记录数、当前页总记录数、当前页*/
    public Result findAllPageArtList(int currentPage, Integer pageSize) {
        // Map<String,Object> map = new HashMap<>();
        //map.put("msg","查询列表成功");
        //        map.put("code",200);

        Pageable pageable = PageRequest.of(currentPage, pageSize);
        Page<Article> page=articleMapper.findAll(pageable);
        Result result = Result.succPage(200, "查询文章数据列表成功", page.getContent(), page.getNumber(), page.getSize(), page.getTotalElements());
        return result;
    }

    /*
    * 字符串转long集合
    * 实现批量删除功能
    * */
    @Override
    public Result deleteCheckArtListIn(String aids) {
        // JDK8数组转换集合
        List<Long> cidList = stream(aids.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());
        // 执行批量删除
        articleMapper.deleteCheckArtListIn(cidList);
        return Result.success(200,"批量删除执行成功！");
    }

    // 文章添加   不具备多对一
    @Override
    public Result saveArt(HashMap<String, String> map) {

        String aname = map.get("aname");
        String cid = map.get("cid");
        String acontent = map.get("acontent");
        Article article = new Article();
        article.setAName(aname);
        article.setAContent(acontent);
        article.setARtime(new Date());
        Article article1 = articleMapper.save(article);
        System.out.println("aname: "+aname+"-----cid："+cid+"----------acontent："+acontent);

        return Result.success(200,"添加文章成功！");
    }

    @Override/*查询单个文章*/
    public Result getArtinfo(Long aid) {

        Article article = articleMapper.findOne(aid);

        return Result.succ(200,"查询单个文章信息成功",article);
    }


    // 文章更新   不具备多对一
    @Override
    public Result updArt(HashMap<String, String> map) {
        Long  aid = Long.parseLong(map.get("aid"));
        String aname = map.get("aname");
        String acontent = map.get("acontent");
        int i = articleMapper.updMsg(aname,acontent,aid);

        return Result.success(200,"修改文章成功！");
    }

    /*
    *
    *
    * 实现文章添加操作
    * 通过多对一形式   文章 分类
    * */





}
