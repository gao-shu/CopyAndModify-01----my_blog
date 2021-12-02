package com.gaoshu.service.impl;

import com.gaoshu.entity.PO.Tag;
import com.gaoshu.mapper.TagMapper;
import com.gaoshu.service.ITagService;
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
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Override
    public List<Tag> getTagList() {
        return null;
    }

    @Override
    public void addTag(Tag tag) {

    }

    @Override
    public void updateTag(Tag t) {

    }

    @Override
    public void deleteTagByIds(List<Integer> ids) {

    }
}
