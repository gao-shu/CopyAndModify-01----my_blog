package com.gaoshu.service.impl;

import com.gaoshu.entity.PO.ArticleTag;
import com.gaoshu.mapper.ArticleTagMapper;
import com.gaoshu.service.IArticleTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    @Override
    public List<ArticleTag> getArticleTagList() {
        return null;
    }

    @Override
    public void addArticleTag(ArticleTag articleTag) {

    }

    @Override
    public void updateArticletag(ArticleTag articleTag) {

    }

    @Override
    public void deleteArticleTagByIds(List<Integer> ids) {

    }
}
