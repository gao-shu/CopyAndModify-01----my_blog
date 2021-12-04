package com.site.blog.my.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.dao.BlogCategoryMapper;
import com.site.blog.my.core.dao.BlogCommentMapper;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BlogComment;
import com.site.blog.my.core.service.CommentService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class CommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment>  implements CommentService {
    @Autowired
    private BlogCommentMapper blogCommentMapper;

    @Override
    public Boolean addComment(BlogComment blogComment) {
//        return blogCommentMapper.insertSelective(blogComment) > 0;
        return save(blogComment);
    }

    @Override
    public PageResult getCommentsPage(PageQueryUtil pageUtil) {
//        List<BlogComment> comments = blogCommentMapper.findBlogCommentList(pageUtil);
//        int total = blogCommentMapper.getTotalBlogComments(pageUtil);
        Page<BlogComment> hutoolPage = new Page<>();
        BeanUtil.copyProperties(pageUtil, hutoolPage);
        Page<BlogComment> commentPage = lambdaQuery().eq(BlogComment::getIsDeleted, 0).orderByDesc(BlogComment::getCommentId)
                .page(hutoolPage);
        PageResult pageResult = new PageResult(commentPage.getRecords(), Convert.toInt(commentPage.getTotal()), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalComments() {
//        return blogCommentMapper.getTotalBlogComments(null);
        return count();
    }

    @Override
    public Boolean checkDone(Integer[] ids) {
//        return blogCommentMapper.checkDone(ids) > 0;
        boolean update = lambdaUpdate().eq(BlogComment::getCommentStatus, 0)
                .in(BlogComment::getCommentId, Arrays.asList(ids))
                .set(BlogComment::getCommentStatus, 1).update();
        return update;

    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
//        return blogCommentMapper.deleteBatch(ids) > 0;
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public Boolean reply(Long commentId, String replyBody) {
//        BlogComment blogComment = blogCommentMapper.selectByPrimaryKey(commentId);
        BlogComment blogComment = getById(commentId);
        //blogComment不为空且状态为已审核，则继续后续操作
        if (blogComment != null && blogComment.getCommentStatus().intValue() == 1) {
            blogComment.setReplyBody(replyBody);
            blogComment.setReplyCreateTime(new Date());
//            return blogCommentMapper.updateByPrimaryKeySelective(blogComment) > 0;
            return updateById(blogComment);
        }
        return false;
    }

    @Override
    public PageResult getCommentPageByBlogIdAndPageNum(Long blogId, int page) {
        if (page < 1) {
            return null;
        }
        Map params = new HashMap();
        params.put("page", page);
        //每页8条
        params.put("limit", 8);
        params.put("blogId", blogId);
        params.put("commentStatus", 1);//过滤审核通过的数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        Page<BlogComment> hutoolPage = new Page<>();
        BeanUtil.copyProperties(pageUtil, hutoolPage);
        Page<BlogComment> commentPage = lambdaQuery().eq(BlogComment::getIsDeleted, 0).orderByDesc(BlogComment::getCommentId)
                .eq(blogId != null, BlogComment::getBlogId, blogId).eq(BlogComment::getCommentStatus, 1).page(hutoolPage);
//        List<BlogComment> comments = blogCommentMapper.findBlogCommentList(pageUtil);
        List comments = commentPage.getRecords();
        if (!CollectionUtils.isEmpty(comments)) {
//            int total = blogCommentMapper.getTotalBlogComments(pageUtil);
            PageResult pageResult = new PageResult(comments, Convert.toInt(commentPage.getTotal()), pageUtil.getLimit(), pageUtil.getPage());
            return pageResult;
        }
        return null;
    }
}
