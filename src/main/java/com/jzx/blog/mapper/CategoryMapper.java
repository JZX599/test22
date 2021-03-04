package com.jzx.blog.mapper;

import com.jzx.blog.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

//文章类别接口  继承  动态代理执行增删改查 接口
public interface CategoryMapper extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {


    @Query(value = "from Category where CId =?1")
    public Category findOne(Long CId);



}
