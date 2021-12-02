package com.gaoshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshu.entity.PO.Tag;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
public interface ITagService extends IService<Tag> {

    /**
     * @Description: 产看标签列表
     * @Param: []
     * @return: java.util.List<com.gaoshu.entity.PO.ArticleTag>
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    List<Tag> getTagList();

    /**
     * @Description: 新增标签
     * @Param: [articleTag]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    void addTag(Tag tag);

    /**
     * @Description: 修改标签
     * @Param: [articleTag]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    void updateTag(Tag t);

    /**
     * @Description: 删除标签
     * @Param: [ids]
     * @return: void
     * @Author: gaoshu
     * @Date: 2021/11/30
     */
    void deleteTagByIds(List<Integer> ids);
}
