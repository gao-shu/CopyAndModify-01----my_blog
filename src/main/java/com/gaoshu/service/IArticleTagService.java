package com.gaoshu.service;

import com.gaoshu.entity.PO.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * @Description: 产看标签列表
     * @Param: []
     * @return: java.util.List<com.gaoshu.entity.PO.ArticleTag>
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    List<ArticleTag> getArticleTagList();

    /**
     * @Description: 新增标签
     * @Param: [articleTag]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void addArticleTag(ArticleTag articleTag);

    /**
     * @Description: 修改标签
     * @Param: [articleTag]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void updateArticletag(ArticleTag articleTag);

    /**
     * @Description: 删除标签
     * @Param: [ids]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void deleteArticleTagByIds(List<Integer> ids);

}
