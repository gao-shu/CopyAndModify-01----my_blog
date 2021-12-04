package com.site.blog.my.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.dao.BlogCategoryMapper;
import com.site.blog.my.core.dao.BlogMapper;
import com.site.blog.my.core.entity.Blog;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.service.BlogService;
import com.site.blog.my.core.service.CategoryService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl extends ServiceImpl<BlogCategoryMapper, BlogCategory> implements CategoryService {

    @Autowired
    private BlogCategoryMapper blogCategoryMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogService blogService;

    @Override
    public PageResult getBlogCategoryPage(PageQueryUtil pageUtil) {
//        List<BlogCategory> categoryList = blogCategoryMapper.findCategoryList(pageUtil);
//        int total = blogCategoryMapper.getTotalCategories(pageUtil);
        Page page = BeanUtil.copyProperties(pageUtil, Page.class);
        Page categoryPage = lambdaQuery().eq(BlogCategory::getIsDeleted, 0).orderByDesc(BlogCategory::getCategoryRank)
                .orderByDesc(BlogCategory::getCreateTime).page(page);
        PageResult pageResult = new PageResult(categoryPage.getRecords(), Convert.toInt(categoryPage.getTotal()), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalCategories() {
//        return blogCategoryMapper.getTotalCategories(null);
        return count();
    }

    @Override
    public Boolean saveCategory(String categoryName, String categoryIcon) {
//        BlogCategory temp = blogCategoryMapper.selectByCategoryName(categoryName);
//        if (temp == null) {
//            BlogCategory blogCategory = new BlogCategory();
//            blogCategory.setCategoryName(categoryName);
//            blogCategory.setCategoryIcon(categoryIcon);
//            return blogCategoryMapper.insertSelective(blogCategory) > 0;
//        }
        List<BlogCategory> categoryList = lambdaQuery().eq(BlogCategory::getCategoryName, categoryName).list();
        if (categoryList.isEmpty()) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryName(categoryName);
            blogCategory.setCategoryIcon(categoryIcon);
            return save(blogCategory);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean updateCategory(Integer categoryId, String categoryName, String categoryIcon) {
//        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(categoryId);
//        if (blogCategory != null) {
//            blogCategory.setCategoryIcon(categoryIcon);
//            blogCategory.setCategoryName(categoryName);
//            //修改分类实体
//            blogMapper.updateBlogCategorys(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
//            return blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0;
//        }
        List<BlogCategory> categoryList = lambdaQuery().eq(BlogCategory::getCategoryName, categoryName).list();
        if (!categoryList.isEmpty()) {
            BlogCategory blogCategory = new BlogCategory();
            blogCategory.setCategoryIcon(categoryIcon);
            blogCategory.setCategoryName(categoryName);
            //修改分类实体
//            blogMapper.updateBlogCategorys(categoryName, blogCategory.getCategoryId(), new Integer[]{categoryId});
            lambdaUpdate().eq(BlogCategory::getIsDeleted, 0).eq(BlogCategory::getCategoryId, categoryId)
                    .set(BlogCategory::getCategoryName, categoryName)
                    .set(BlogCategory::getCategoryId, blogCategory.getCategoryId()).update();
//            return blogCategoryMapper.updateByPrimaryKeySelective(blogCategory) > 0;
            return updateById(blogCategory);
        }
        return false;
    }

    @Override
    @Transactional
    public Boolean deleteBatch(Integer[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //修改tb_blog表
//        blogMapper.updateBlogCategorys("默认分类", 0, ids);
        blogService.lambdaUpdate().eq(Blog::getIsDeleted, 0).in(Blog::getBlogCategoryId,  Arrays.asList(ids))
                .set(Blog::getBlogCategoryId, 0)
                .set(Blog::getBlogCategoryName, "默认分类").update();
        //删除分类数据
//        return blogCategoryMapper.deleteBatch(ids) > 0;
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public List<BlogCategory> getAllCategories() {
//        return blogCategoryMapper.findCategoryList(null);
        List<BlogCategory> list = lambdaQuery().eq(BlogCategory::getIsDeleted, 0).orderByDesc(BlogCategory::getCategoryRank)
                .orderByDesc(BlogCategory::getCreateTime).list();
        return list;
    }

}
