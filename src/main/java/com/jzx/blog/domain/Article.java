package com.jzx.blog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 1.实体类和表的映射关系
 *      @Eitity
 *      @Table
 * 2.类中属性和表中字段的映射关系
 *      @Id
 *      @GeneratedValue
 *      @Column
 */
@Data
@Entity //标明这就只是个实体，其他什么都不干
@Table(name = "b_article") //对应表名
public class Article implements Serializable {


    @Id //标明是id字段
    @GeneratedValue(strategy = GenerationType.IDENTITY) //指定主键生成策略
    @Column(name = "aid") //字段名称
    private Long AId; // 文章id
    @Column(name = "aname")
    private String AName; // 文章名称
    @Column(name = "artime")
    @JsonFormat(pattern="yyyy年MM月dd日 HH:mm",timezone = "GMT+8")
    private Date ARtime; // 文章发表时间
    @Column(name = "acontent")
    private String AContent; // 文章内容


    /**
     * 配置联系人到客户的多对一关系
     *     使用注解的形式配置多对一关系
     *      1.配置表关系
     *          @ManyToOne : 配置多对一关系
     *              targetEntity：对方的实体类字节码
     *      2.配置外键（中间表）
     *
     * * 配置外键的过程，配置到了多的一方，就会在多的一方维护外键
     *
     */
/*    //  多的一方设置懒加载LAZY

    @ManyToOne(targetEntity = Category.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "acid",referencedColumnName = "cid")
    private Category category;*/



}
