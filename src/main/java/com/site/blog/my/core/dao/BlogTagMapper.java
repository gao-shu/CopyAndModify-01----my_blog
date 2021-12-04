package com.site.blog.my.core.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BlogTag;
import com.site.blog.my.core.entity.BlogTagCount;
import com.site.blog.my.core.util.PageQueryUtil;
import java.util.List;

public interface BlogTagMapper extends BaseMapper<BlogTag> {
//    int deleteByPrimaryKey(Integer tagId);
//
//    int insert(BlogTag record);
//
//    int insertSelective(BlogTag record);
//
//    BlogTag selectByPrimaryKey(Integer tagId);
//
//    BlogTag selectByTagName(String tagName);
//
//    int updateByPrimaryKeySelective(BlogTag record);
//
//    int updateByPrimaryKey(BlogTag record);
//
//    List<BlogTag> findTagList(PageQueryUtil pageUtil);
//
    List<BlogTagCount> getTagCount();
//
//    int getTotalTags(PageQueryUtil pageUtil);
//
//    int deleteBatch(Integer[] ids);
//
//    int batchInsertBlogTag(List<BlogTag> tagList);
}
