package com.jzx.blog.mapper;

import com.jzx.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

//继承两个接口  生成动态代理对象执行增删改查
public interface ArticleMapper extends JpaRepository<Article,Long>, JpaSpecificationExecutor<Article> {


    @Query(value = "from Article where AName like ?1")
    public List<Article> findByANameLike(String AName);

    @Query(value = "from Article where AName=?1 and AContent=?2")
    public Article findByANameAndAContentLike(String AName,String AContent);

    @Query(value = "update Article set AName=?1 , AContent=?2 where AId =?3")
    @Transactional
    @Modifying
    public int updMsg(String AName,String AContent,Long AId);

    @Query(value = "from Article where AId =?1")
    public Article findOne(Long AId);


    @Modifying
    @Transactional
    @Query("delete from Article a where a.AId in (?1)")
    public void deleteCheckArtListIn(List<Long> cids);


    //public Customer findByCustNameLikeAndCustIndustry(String custName,String custIndustry);


}
