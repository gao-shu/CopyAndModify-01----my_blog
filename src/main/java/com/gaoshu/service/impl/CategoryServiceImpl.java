package com.gaoshu.service.impl;

import com.gaoshu.entity.PO.Category;
import com.gaoshu.mapper.CategoryMapper;
import com.gaoshu.service.ICategoryService;
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
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

    @Override
    public List<Category> getCategorieList() {
        return list();
    }

    @Override
    public void addCategory(Category category) {
        save(category);
    }

    @Override
    public void updateCategory(Category category) {
        updateById(category);
    }

    @Override
    public void deleteByIds(List<Integer> idsTo) {
        deleteByIds(idsTo);
    }
}
