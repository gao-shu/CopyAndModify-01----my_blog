package com.gaoshu.service.impl;

import com.gaoshu.entity.PO.Comment;
import com.gaoshu.mapper.CommentMapper;
import com.gaoshu.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言/评论表 服务实现类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
