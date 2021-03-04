package com.jzx.blog;


import com.jzx.blog.domain.Article;
import com.jzx.blog.domain.Category;
import com.jzx.blog.mapper.ArticleMapper;
import com.jzx.blog.mapper.CategoryMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)//声明spring提供的单元测试环境
@SpringBootTest(classes = BlogApplication.class)//指定spring容器的配置信息
public class CateTest {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void test() {
        for (int i = 0; i < 2; i++) {
            Category category = new Category();
            category.setCName("新增分类"+i);
            Category cate = categoryMapper.save(category);
        }

    }

    @Test// 删除单个分类
    public void test1() {
        categoryMapper.deleteById(84L);
    }
    @Test// 查询单个分类
    public void testjilian(){
        Category one = new Category();
        one.setCName("haha");
        one.setCId(105L);
        Article article = new Article();
        article.setARtime(new Date());
        article.setAName("夏至未至");
        article.setAContent("aaaaa");
        /*保存文章对象信息*/
        Article save = articleMapper.save(article);


        System.out.println("测试保存文章对象  分类多对一关系："+save.toString());



    }

}
