package com.gaoshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshu.common.PageEntity;
import com.gaoshu.entity.PO.Article;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
public interface IArticleService extends IService<Article> {

    /**
     * @Description: 查看文章列表分页
     * @Param: []
     * @return: java.util.List<com.gaoshu.entity.PO.Article>
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    List<Article> getArticleList(PageEntity page);

    /**
     * @Description: 新增文章
     * @Param: [article]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void addArticle(Article article);

    /**
     * @Description: 修改文章
     * @Param: [article]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void updateArticle(Article article);

    /**
     * @Description: 删除文章
     * @Param: [ids]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
    */
    void deleteByIds(List<Integer> ids);
}
