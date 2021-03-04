package com.jzx.blog.domain;


import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/*
* 文章类别
*
* */
@Data
@Entity
@Table(name = "b_category")
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private Long CId; // 类别id
    @NotBlank(message = "类别名称不能为空！")
    @Column(name = "cname")
    private String CName; // 类别名称



    /**
     * 使用注解的形式配置多表关系
     *      1.声明关系
     *          @OneToMany : 配置一对多关系
     *              targetEntity ：对方对象的字节码对象
     *      2.配置外键（中间表）
     *              @JoinColumn : 配置外键
     *                  name：外键字段名称
     *                  referencedColumnName：参照的主表的主键字段名称
     *
     *  * 在客户实体类上（一的一方）添加了外键了配置，所以对于客户而言，也具备了维护外键的作用
     *
     */

//    @OneToMany(targetEntity = Article.class)
//    @JoinColumn(name = "acid",referencedColumnName = "cid")
    /**
     * 放弃外键维护权
     *      mappedBy：对方配置关系的属性名称\
     * cascade : 配置级联（可以配置到设置多表的映射关系的注解上）
     *      CascadeType.all         : 所有
     *                  MERGE       ：更新
     *                  PERSIST     ：保存
     *                  REMOVE      ：删除
     *
     * fetch : 配置关联对象的加载方式
     *          EAGER   ：立即加载
     *          LAZY    ：延迟加载
     */
    // 文章分类  对  文章   ：一对多关系      分类一的一方  设置立即加载EAGER
  /*  //mappedBy相当于之前的inverse，由Contact里面的custommer属性的set方法去维护关联关系。简单点说就是本类放弃维护，即inverse=true
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<Article> articleSet = new HashSet<>();*/



}
