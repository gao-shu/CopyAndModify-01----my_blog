package com.gaoshu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshu.entity.PO.Category;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
public interface ICategoryService extends IService<Category> {

    List<Category> getCategorieList();

    void addCategory(Category category);

    void updateCategory(Category category);

    void deleteByIds(List<Integer> idsTo);
}
