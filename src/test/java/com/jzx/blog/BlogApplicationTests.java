package com.jzx.blog;

import com.jzx.blog.common.lang.Result;
import com.jzx.blog.domain.Article;
import com.jzx.blog.domain.Category;
import com.jzx.blog.mapper.ArticleMapper;
import com.jzx.blog.mapper.CategoryMapper;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.*;

@RunWith(SpringRunner.class)//声明spring提供的单元测试环境
@SpringBootTest(classes = BlogApplication.class)//指定spring容器的配置信息
public class BlogApplicationTests {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;
    /*
     * 1、执行查询所有文章
     *
     * */
    @Test
    public void getAll() {

        List<Article> articleList = articleMapper.findAll();

        for (Article article : articleList) {
            System.out.println(article);
        }
    }


    /*
     * 2、执行添加文章
     *
     *
     * */
    @Test
    public void save() {


        Article article = new Article();
        article.setAName("今日头条");
        article.setAContent("头条内容:");
        article.setARtime(new Date());
        Article art = articleMapper.save(article);
        System.out.println(art);

    }



    /*
     * 2、根据id查询单条文章
     *
     *getOne懒加载（需要配置事务）
     * findOne立即加载
     * */
    @Test
    @Transactional
    public void finone() {
        Article article = articleMapper.getOne(1l);

        System.out.println(article);

    }

    /*
     * 4、执行文章总条数查询
     *
     * */

    @Test
    public void getCount() {

        long count = articleMapper.count();
        System.out.println(count);
    }


    /*
     * 6、根据文章名模糊查询
     *
     * */
    @Test
    public void finByAName() {
        List<Article> artList = articleMapper.findByANameLike("%今%");
        for (Article article : artList) {
            System.out.println(article);
        }

    }

    /*
     * 6、根据文章名及文章内容  精确查询 只能查询一条数据   有待学习
     *
     * */
    @Test
    public void finOne() {
        Article art = articleMapper.findByANameAndAContentLike("哈哈333", "头条内容");
        System.out.println(art);

    }


    /*
     * 7、根据文章名及文章内容  精确查询 只能查询一条数据   有待学习
     *
     * */

    /**
     * 测试jpql的更新操作
     * * springDataJpa中使用jpql完成 更新/删除操作
     * * 需要手动添加事务的支持
     * * 默认会执行结束之后，回滚事务
     *
     * @Rollback : 设置是否自动回滚
     * false | true
     */
    @Test
    @Transactional //添加事务的支持
    @Rollback(value = false)// 设置不回滚
    public void updArticle() {
        int i = articleMapper.updMsg("文章13", "内容12", 12L);
        System.out.println(i + "------------------------------------");

    }


    /**
     * 根据id从数据库查询
     *
     * @Transactional : 保证getOne正常运行
     * <p>
     * findOne：
     * em.find()           :立即加载
     * getOne：
     * em.getReference     :延迟加载
     * * 返回的是一个客户的动态代理对象
     * * 什么时候用，什么时候查询
     */
    @Test
    @Transactional
    public void testGetOne() {
        Article article = articleMapper.getOne(2l);
        System.out.println(article);
    }

    /*
    * 分页查询：
    *Specification:查询条件
    *Pageable:分页
    * 返回Page   为我们封装好pageBean对象
    *
    * Pageable:分页  有以下实现方式并且需要传递两个参数
    * PageRequest.of(page, size);
    * 一、page为当前页
    * 二、size为当前页所展示的条数
    * */
    @Test
    public void PageInfoList() {
        Pageable pageable = PageRequest.of(2, 20);
        Page<Article> page=articleMapper.findAll(pageable);
        System.out.println("数据  总页数："+page.getTotalPages());
        System.out.println("数据  总条数："+page.getTotalElements());
        System.out.println("数据  当前页："+page.getNumber());
        System.out.println("数据  当前页数据集合："+page.getContent());
        System.out.println("数据  当前页集合条数："+page.getSize());
    }



    /*
    * 删除多条数据
    * 通过delete前端请求执行
    * */


    @Test
    public void deleteCheckArtListIn() {
        String aid="1,2,3,4,5";
        /*通过jdk8新特性   将字符串转为long集合*/
        List<Long> cidList = stream(aid.split(","))
                .map(s -> Long.parseLong(s.trim()))
                .collect(Collectors.toList());

       /* for (Long aLong : cidList) {
            System.out.println(aLong);
        }*/
        articleMapper.deleteCheckArtListIn(cidList);
    }




    /***************************动态查询***************************************/
    /*
    * 根据单个条件   查询单个对象
    *未经测试使用   有待后期探索
    * */


    @Test
    public void test(){


        /*通过匿名内部类
        *
        * 1、自定义查询条件
        * 实现Specification接口（提供泛型：查询对象的类型）
        * 2、构造查询条件----------通过toPredicate方法
        *root：获取需要查询对象的属性
        *CriteriaBuilder： 构造查询条件的  内部封装很多的（模糊匹配，精准匹配）
        *
        * */
        Specification<Article> art =  new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Path<Object> aName = root.get("AName");
                Predicate predicate = criteriaBuilder.equal(aName, "头条文章");
                return predicate;
            }
        };
        /*通过使用匿名内部类  来设置一个对象的查询条件  并通过mapper查询这个对象对应的数据*/
        Optional<Article> one = articleMapper.findOne(art);

        System.out.println(one);
    }


    /*
     * 根据多个条件查询对象
     *未经测试使用   有待后期探索
     * */


    @Test
    public void test1(){


        /*通过匿名内部类
         *
         * 实现Specification接口（提供泛型：查询对象的类型）
         * 2、构造查询条件----------通过toPredicate方法
         *
         * root·：获取属性
         * 客户名
         * 所属行业
         * 1、自定义查询条件
         *CriteriaBuilder： 1、构造文章名精准查询
         * 2、构造所属行业的精准匹配
         *3、将两个以上的查询联系起来
         * */
        Specification<Article> art =  new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

                Path<Object> aName = root.get("AName");
                Path<Object> aContent = root.get("AContent");
                Predicate predicate = criteriaBuilder.equal(aName, "头条文章");
                Predicate predicate1 = criteriaBuilder.equal(aContent,"内容");
                Predicate and = criteriaBuilder.and(predicate, predicate1);// 满足一和满足二的   and
                // criteriaBuilder.or() or满足一或者满足二

                return and;
            }
        };
        /*通过使用匿名内部类  来设置一个对象的查询条件  并通过mapper查询这个对象对应的数据*/
        Optional<Article> one = articleMapper.findOne(art);


        System.out.println(one);
    }


    /*动态查询之------------------模糊查询
    * 模糊查询
    *
    *
    * */

    @Test
    public void  test3(){

      Specification<Article> a=  new Specification<Article>(){
          @Override
          public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

              Path<Object> aName = root.get("AName");
              Predicate like = criteriaBuilder.like(aName.as(String.class), "%文章%");

              return like;
          }
      };

      /*降序排列
      * 第一个参数。排序的顺序（倒序，正序）
      *第二个参数，排序的属性名称
      *
      * */
        Sort sort = new Sort(Sort.Direction.DESC,"aid");
        List<Article> atcList = articleMapper.findAll(a, sort);

        for (Article article : atcList) {
            System.out.println(article);
        }



    }

    /*
    * 动态查询之分页    按条件分页查询
    *
    *有待验证--------
    *
    *
    * */

    /*@Test
    public Page<Article> getPageUpgradeScheduleView(Article article, int pageNumber, int pageSize) {
        Specification querySpeci = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {

                *//*查询条件
                *
                * *//*
                return null;
            }
        };
        *//* Pageable pageable = PageRequest.of(2, 5);     前面是当前页、后面是当期页面数据条数*//*
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return articleMapper.findAll(querySpeci,pageRequest);
    }*/




    /*
    * 级联操作  测试
    *
    *
    *
    * */
}
