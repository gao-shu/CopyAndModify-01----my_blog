package com.site.blog.my.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper extends BaseMapper<Blog> {
//    int deleteByPrimaryKey(Long blogId);
//
//    int insert(Blog record);
//
//    int insertSelective(Blog record);
//
//    Blog selectByPrimaryKey(Long blogId);
//
//    int updateByPrimaryKeySelective(Blog record);
//
//    int updateByPrimaryKeyWithBLOBs(Blog record);
//
//    int updateByPrimaryKey(Blog record);
//
    List<Blog> findBlogList(PageQueryUtil pageUtil);
//
//    List<Blog> findBlogListByType(@Param("type") int type, @Param("limit") int limit);
//
    int getTotalBlogs(PageQueryUtil pageUtil);
//
//    int deleteBatch(Integer[] ids);
//
//    List<Blog> getBlogsPageByTagId(PageQueryUtil pageUtil);
//
//    int getTotalBlogsByTagId(PageQueryUtil pageUtil);
//
//    Blog selectBySubUrl(String subUrl);
//
//    int updateBlogCategorys(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId, @Param("ids")Integer[] ids);

}
