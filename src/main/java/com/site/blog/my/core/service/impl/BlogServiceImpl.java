package com.site.blog.my.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.controller.vo.BlogDetailVO;
import com.site.blog.my.core.controller.vo.BlogListVO;
import com.site.blog.my.core.controller.vo.SimpleBlogListVO;
import com.site.blog.my.core.dao.*;
import com.site.blog.my.core.entity.*;
import com.site.blog.my.core.service.*;
import com.site.blog.my.core.util.MarkDownUtil;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import com.site.blog.my.core.util.PatternUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private BlogCategoryMapper categoryMapper;
    @Autowired
    private BlogTagMapper tagMapper;
    @Autowired
    private BlogTagRelationMapper blogTagRelationMapper;
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ITbBlogTagRelationService blogTagRelationService;

    @Autowired
    private CommentService commentService;

    @Override
    @Transactional
    public String saveBlog(Blog blog) {
//        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        BlogCategory blogCategory = categoryService.getById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blog.setBlogCategoryId(0);
            blog.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blog.setBlogCategoryName(blogCategory.getCategoryName());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        //保存文章
//        if (blogMapper.insertSelective(blog) > 0) {
        if (save(blog)) {
            //新增的tag对象
            List<BlogTag> tagListForInsert = new ArrayList<>();
            //所有的tag对象，用于建立关系数据
            List<BlogTag> allTagsList = new ArrayList<>();
            for (int i = 0; i < tags.length; i++) {
//                BlogTag tag = tagMapper.selectByTagName(tags[i]);
//                if (tag == null) {
//                    //不存在就新增
//                    BlogTag tempTag = new BlogTag();
//                    tempTag.setTagName(tags[i]);
//                    tagListForInsert.add(tempTag);
//                } else {
//                    allTagsList.add(tag);
//                }
                List<BlogTag> tagList = tagService.lambdaQuery().eq(BlogTag::getTagName, tags[i])
                        .eq(BlogTag::getIsDeleted, 0).list();
                if (tagList.isEmpty()) {
                    //不存在就新增
                    BlogTag tempTag = new BlogTag();
                    tempTag.setTagName(tags[i]);
                    tagListForInsert.add(tempTag);
                } else {
                    allTagsList.addAll(tagList);
                }
            }
            //新增标签数据并修改分类排序值
            if (!CollectionUtils.isEmpty(tagListForInsert)) {
//                tagMapper.batchInsertBlogTag(tagListForInsert);
                tagService.saveBatch(tagListForInsert);
            }
//            categoryMapper.updateByPrimaryKeySelective(blogCategory);
            categoryService.updateById(blogCategory);
            List<BlogTagRelation> blogTagRelations = new ArrayList<>();
            //新增关系数据
            allTagsList.addAll(tagListForInsert);
            for (BlogTag tag : allTagsList) {
                BlogTagRelation blogTagRelation = new BlogTagRelation();
                blogTagRelation.setBlogId(blog.getBlogId());
                blogTagRelation.setTagId(tag.getTagId());
                blogTagRelations.add(blogTagRelation);
            }
//            if (blogTagRelationMapper.batchInsert(blogTagRelations) > 0) {
            if (blogTagRelationService.saveBatch(blogTagRelations)) {
                return "success";
            }
        }
        return "保存失败";
    }

    @Override
    public PageResult getBlogsPage(PageQueryUtil pageUtil) {
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        int total = blogMapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
//        return blogMapper.deleteBatch(ids) > 0;
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public int getTotalBlogs() {
        return blogMapper.getTotalBlogs(null);
    }

    @Override
    public Blog getBlogById(Long blogId) {
//        return blogMapper.selectByPrimaryKey(blogId);
        return getById(blogId);
    }

    @Override
    @Transactional
    public String updateBlog(Blog blog) {
//        Blog blogForUpdate = blogMapper.selectByPrimaryKey(blog.getBlogId());
        Blog blogForUpdate = getById(blog.getBlogId());
        if (blogForUpdate == null) {
            return "数据不存在";
        }
        blogForUpdate.setBlogTitle(blog.getBlogTitle());
        blogForUpdate.setBlogSubUrl(blog.getBlogSubUrl());
        blogForUpdate.setBlogContent(blog.getBlogContent());
        blogForUpdate.setBlogCoverImage(blog.getBlogCoverImage());
        blogForUpdate.setBlogStatus(blog.getBlogStatus());
        blogForUpdate.setEnableComment(blog.getEnableComment());
//        BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
        BlogCategory blogCategory = categoryService.getById(blog.getBlogCategoryId());
        if (blogCategory == null) {
            blogForUpdate.setBlogCategoryId(0);
            blogForUpdate.setBlogCategoryName("默认分类");
        } else {
            //设置博客分类名称
            blogForUpdate.setBlogCategoryName(blogCategory.getCategoryName());
            blogForUpdate.setBlogCategoryId(blogCategory.getCategoryId());
            //分类的排序值加1
            blogCategory.setCategoryRank(blogCategory.getCategoryRank() + 1);
        }
        //处理标签数据
        String[] tags = blog.getBlogTags().split(",");
        if (tags.length > 6) {
            return "标签数量限制为6";
        }
        blogForUpdate.setBlogTags(blog.getBlogTags());
        //新增的tag对象
        List<BlogTag> tagListForInsert = new ArrayList<>();
        //所有的tag对象，用于建立关系数据
        List<BlogTag> allTagsList = new ArrayList<>();
        for (int i = 0; i < tags.length; i++) {
//            BlogTag tag = tagMapper.selectByTagName(tags[i]);
//            if (tag == null) {
//                //不存在就新增
//                BlogTag tempTag = new BlogTag();
//                tempTag.setTagName(tags[i]);
//                tagListForInsert.add(tempTag);
//            } else {
//                allTagsList.add(tag);
//            }
            List<BlogTag> tagList = tagService.lambdaQuery().eq(BlogTag::getTagName, tags[i])
                    .eq(BlogTag::getIsDeleted, 0).list();
            if (tagList.isEmpty()) {
                //不存在就新增
                BlogTag tempTag = new BlogTag();
                tempTag.setTagName(tags[i]);
                tagListForInsert.add(tempTag);
            } else {
                allTagsList.addAll(tagList);
            }
        }
        //新增标签数据不为空->新增标签数据
        if (!CollectionUtils.isEmpty(tagListForInsert)) {
//            tagMapper.batchInsertBlogTag(tagListForInsert);
            tagService.saveBatch(tagListForInsert);
        }
        List<BlogTagRelation> blogTagRelations = new ArrayList<>();
        //新增关系数据
        allTagsList.addAll(tagListForInsert);
        for (BlogTag tag : allTagsList) {
            BlogTagRelation blogTagRelation = new BlogTagRelation();
            blogTagRelation.setBlogId(blog.getBlogId());
            blogTagRelation.setTagId(tag.getTagId());
            blogTagRelations.add(blogTagRelation);
        }
        //修改blog信息->修改分类排序值->删除原关系数据->保存新的关系数据
//        categoryMapper.updateByPrimaryKeySelective(blogCategory);
//        blogTagRelationMapper.deleteByBlogId(blog.getBlogId());
//        blogTagRelationMapper.batchInsert(blogTagRelations);
        categoryService.updateById(blogCategory);
        blogTagRelationService.lambdaUpdate().eq(BlogTagRelation::getBlogId, blog.getBlogId()).remove();
        blogTagRelationService.updateBatchById(blogTagRelations);

//        if (blogMapper.updateByPrimaryKeySelective(blogForUpdate) > 0) {
        if (updateById(blogForUpdate)) {
            return "success";
        }
        return "修改失败";
    }

    @Override
    public PageResult getBlogsForIndexPage(int page) {
        Map params = new HashMap();
        params.put("page", page);
        //每页8条
        params.put("limit", 8);
        params.put("blogStatus", 1);//过滤发布状态下的数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        List<Blog> blogList = blogMapper.findBlogList(pageUtil);
        List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
        int total = blogMapper.getTotalBlogs(pageUtil);
        PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public List<SimpleBlogListVO> getBlogListForIndexPage(int type) {
        List<SimpleBlogListVO> simpleBlogListVOS = new ArrayList<>();
//        List<Blog> blogs = blogMapper.findBlogListByType(type, 9);
        List<Blog> blogs = lambdaQuery().orderByDesc(type == 0, Blog::getBlogViews)
                .orderByDesc(type == 1, Blog::getBlogId).last("limit 9").list();

        if (!CollectionUtils.isEmpty(blogs)) {
            for (Blog blog : blogs) {
                SimpleBlogListVO simpleBlogListVO = new SimpleBlogListVO();
                BeanUtils.copyProperties(blog, simpleBlogListVO);
                simpleBlogListVOS.add(simpleBlogListVO);
            }
        }
        return simpleBlogListVOS;
    }

    @Override
    public BlogDetailVO getBlogDetail(Long id) {
//        Blog blog = blogMapper.selectByPrimaryKey(id);
        Blog blog = getById(id);
        //不为空且状态为已发布
        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByTag(String tagName, int page) {
        if (PatternUtil.validKeyword(tagName)) {
//            BlogTag tag = tagMapper.selectByTagName(tagName);
//            if (tag != null && page > 0) {
//                Map param = new HashMap();
//                param.put("page", page);
//                param.put("limit", 9);
//                param.put("tagId", tag.getTagId());
//                PageQueryUtil pageUtil = new PageQueryUtil(param);
//                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
//                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
//                int total = blogMapper.getTotalBlogsByTagId(pageUtil);
//                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
//                return pageResult;
//            }
            List<BlogTag> tagList = tagService.lambdaQuery().eq(BlogTag::getTagName, tagName)
                    .eq(BlogTag::getIsDeleted, 0).list();
            if (!tagList.isEmpty() && page > 0) {
                BlogTag tag = tagList.get(0);
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("tagId", tag.getTagId());
                PageQueryUtil pageUtil = new PageQueryUtil(param);
//                List<Blog> blogList = blogMapper.getBlogsPageByTagId(pageUtil);
//                int total = blogMapper.getTotalBlogsByTagId(pageUtil);
//                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                List<Long> blogIds = blogTagRelationService.lambdaQuery().eq(BlogTagRelation::getTagId, tag.getTagId()).list()
                        .stream().map(BlogTagRelation::getBlogId).collect(Collectors.toList());
                List<Blog> blogList = lambdaQuery().eq(Blog::getBlogStatus, 1).eq(Blog::getIsDeleted, 0)
                        .in(Blog::getBlogId, blogIds).last("limit " + page + " 9").list();
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                PageResult pageResult = new PageResult(blogListVOS, blogList.size(), pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }

        }
        return null;
    }

    @Override
    public PageResult getBlogsPageByCategory(String categoryName, int page) {
        if (PatternUtil.validKeyword(categoryName)) {
//            BlogCategory blogCategory = categoryMapper.selectByCategoryName(categoryName);
//            if ("默认分类".equals(categoryName) && blogCategory == null) {
//                blogCategory = new BlogCategory();
//                blogCategory.setCategoryId(0);
//            }
//            if (blogCategory != null && page > 0) {
//                Map param = new HashMap();
//                param.put("page", page);
//                param.put("limit", 9);
//                param.put("blogCategoryId", blogCategory.getCategoryId());
//                param.put("blogStatus", 1);//过滤发布状态下的数据
//                PageQueryUtil pageUtil = new PageQueryUtil(param);
//                List<Blog> blogList = blogMapper.findBlogList(pageUtil);
//                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
//                int total = blogMapper.getTotalBlogs(pageUtil);
//                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
//                return pageResult;
//            }

            List<BlogCategory> categoryList = categoryService.lambdaQuery().eq(BlogCategory::getCategoryName, categoryName)
                    .eq(BlogCategory::getIsDeleted, 0).list();
            BlogCategory blogCategory = new BlogCategory();
            if ("默认分类".equals(categoryName) && categoryList.isEmpty()) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
            }
            if (!categoryList.isEmpty() && page > 0) {
                blogCategory = categoryList.get(0);
                Map param = new HashMap();
                param.put("page", page);
                param.put("limit", 9);
                param.put("blogCategoryId", blogCategory.getCategoryId());
                param.put("blogStatus", 1);//过滤发布状态下的数据
                PageQueryUtil pageUtil = new PageQueryUtil(param);
                List<Blog> blogList = blogMapper.findBlogList(pageUtil);
                List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
                int total = blogMapper.getTotalBlogs(pageUtil);
                PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
                return pageResult;
            }
        }
        return null;
    }

    @Override
    public PageResult getBlogsPageBySearch(String keyword, int page) {
        if (page > 0 && PatternUtil.validKeyword(keyword)) {
            Map param = new HashMap();
            param.put("page", page);
            param.put("limit", 9);
            param.put("keyword", keyword);
            param.put("blogStatus", 1);//过滤发布状态下的数据
            PageQueryUtil pageUtil = new PageQueryUtil(param);
            List<Blog> blogList = blogMapper.findBlogList(pageUtil);
            List<BlogListVO> blogListVOS = getBlogListVOsByBlogs(blogList);
            int total = blogMapper.getTotalBlogs(pageUtil);
            PageResult pageResult = new PageResult(blogListVOS, total, pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }

    @Override
    public BlogDetailVO getBlogDetailBySubUrl(String subUrl) {
//        Blog blog = blogMapper.selectBySubUrl(subUrl);
//        //不为空且状态为已发布
//        BlogDetailVO blogDetailVO = getBlogDetailVO(blog);
//        if (blogDetailVO != null) {
//            return blogDetailVO;
//        }
//        return null;

        List<Blog> list = lambdaQuery().eq(Blog::getIsDeleted, 0).last("limit 1").list();
        if (list.isEmpty()) {
            return null;
        }
        //不为空且状态为已发布
        BlogDetailVO blogDetailVO = getBlogDetailVO(list.get(0));
        if (blogDetailVO != null) {
            return blogDetailVO;
        }
        return null;

    }

    /**
     * 方法抽取
     *
     * @param blog
     * @return
     */
    private BlogDetailVO getBlogDetailVO(Blog blog) {
        if (blog != null && blog.getBlogStatus() == 1) {
            //增加浏览量
            blog.setBlogViews(blog.getBlogViews() + 1);
//            blogMapper.updateByPrimaryKey(blog);
            updateById(blog);
            BlogDetailVO blogDetailVO = new BlogDetailVO();
            BeanUtils.copyProperties(blog, blogDetailVO);
            blogDetailVO.setBlogContent(MarkDownUtil.mdToHtml(blogDetailVO.getBlogContent()));
//            BlogCategory blogCategory = categoryMapper.selectByPrimaryKey(blog.getBlogCategoryId());
            BlogCategory blogCategory = categoryService.getById(blog.getBlogCategoryId());
            if (blogCategory == null) {
                blogCategory = new BlogCategory();
                blogCategory.setCategoryId(0);
                blogCategory.setCategoryName("默认分类");
                blogCategory.setCategoryIcon("/admin/dist/img/category/00.png");
            }
            //分类信息
            blogDetailVO.setBlogCategoryIcon(blogCategory.getCategoryIcon());
            if (!StringUtils.isEmpty(blog.getBlogTags())) {
                //标签设置
                List<String> tags = Arrays.asList(blog.getBlogTags().split(","));
                blogDetailVO.setBlogTags(tags);
            }
            //设置评论数
            Map params = new HashMap();
            params.put("blogId", blog.getBlogId());
            params.put("commentStatus", 1);//过滤审核通过的数据
//            blogDetailVO.setCommentCount(blogCommentMapper.getTotalBlogComments(params));
            Integer count = commentService.lambdaQuery().eq(BlogComment::getCommentStatus, 1)
                    .eq(blog.getBlogId() != null, BlogComment::getBlogId, blog.getBlogId())
                    .count();
            blogDetailVO.setCommentCount(count);
            return blogDetailVO;
        }
        return null;
    }

    private List<BlogListVO> getBlogListVOsByBlogs(List<Blog> blogList) {
        List<BlogListVO> blogListVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(blogList)) {
            List<Integer> categoryIds = blogList.stream().map(Blog::getBlogCategoryId).collect(Collectors.toList());
            Map<Integer, String> blogCategoryMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(categoryIds)) {
//                List<BlogCategory> blogCategories = categoryMapper.selectByCategoryIds(categoryIds);
                List<BlogCategory> blogCategories = categoryService.lambdaQuery()
                        .in(BlogCategory::getCategoryId, categoryIds).list();
                if (!CollectionUtils.isEmpty(blogCategories)) {
                    blogCategoryMap = blogCategories.stream().collect(Collectors.toMap(BlogCategory::getCategoryId, BlogCategory::getCategoryIcon, (key1, key2) -> key2));
                }
            }
            for (Blog blog : blogList) {
                BlogListVO blogListVO = new BlogListVO();
                BeanUtils.copyProperties(blog, blogListVO);
                if (blogCategoryMap.containsKey(blog.getBlogCategoryId())) {
                    blogListVO.setBlogCategoryIcon(blogCategoryMap.get(blog.getBlogCategoryId()));
                } else {
                    blogListVO.setBlogCategoryId(0);
                    blogListVO.setBlogCategoryName("默认分类");
                    blogListVO.setBlogCategoryIcon("/admin/dist/img/category/00.png");
                }
                blogListVOS.add(blogListVO);
            }
        }
        return blogListVOS;
    }

}
