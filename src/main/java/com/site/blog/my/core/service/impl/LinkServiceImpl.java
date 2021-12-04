package com.site.blog.my.core.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.site.blog.my.core.dao.BlogCategoryMapper;
import com.site.blog.my.core.dao.BlogLinkMapper;
import com.site.blog.my.core.entity.BlogCategory;
import com.site.blog.my.core.entity.BlogLink;
import com.site.blog.my.core.service.LinkService;
import com.site.blog.my.core.util.PageQueryUtil;
import com.site.blog.my.core.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LinkServiceImpl extends ServiceImpl<BlogLinkMapper, BlogLink> implements LinkService {

    @Autowired
    private BlogLinkMapper blogLinkMapper;

    @Override
    public PageResult getBlogLinkPage(PageQueryUtil pageUtil) {
        Page<BlogLink> hutoolPage = new Page<>();
        BeanUtil.copyProperties(pageUtil, hutoolPage);
        Page<BlogLink> blogLinkPage = lambdaQuery().eq(BlogLink::getIsDeleted, 0).orderByDesc(BlogLink::getLinkId)
                .page(hutoolPage);
//        List<BlogLink> links = blogLinkMapper.findLinkList(pageUtil);
//        int total = blogLinkMapper.getTotalLinks(pageUtil);
        PageResult pageResult = new PageResult(blogLinkPage.getRecords(), Convert.toInt(blogLinkPage.getRecords()), pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public int getTotalLinks() {
//        return blogLinkMapper.getTotalLinks(null);
        return lambdaQuery().eq(BlogLink::getIsDeleted, 0).count();
    }

    @Override
    public Boolean saveLink(BlogLink link) {
//        return blogLinkMapper.insertSelective(link) > 0;
        return save(link);
    }

    @Override
    public BlogLink selectById(Integer id) {
//        return blogLinkMapper.selectByPrimaryKey(id);
        return getById(id);
    }

    @Override
    public Boolean updateLink(BlogLink tempLink) {
//        return blogLinkMapper.updateByPrimaryKeySelective(tempLink) > 0;
        return updateById(tempLink);
    }

    @Override
    public Boolean deleteBatch(Integer[] ids) {
//        return blogLinkMapper.deleteBatch(ids) > 0;
        return removeByIds(Arrays.asList(ids));
    }

    @Override
    public Map<Byte, List<BlogLink>> getLinksForLinkPage() {
        //获取所有链接数据
//        List<BlogLink> links = blogLinkMapper.findLinkList(null);
        List<BlogLink> links = list();
        if (!CollectionUtils.isEmpty(links)) {
            //根据type进行分组
            Map<Byte, List<BlogLink>> linksMap = links.stream().collect(Collectors.groupingBy(BlogLink::getLinkType));
            return linksMap;
        }
        return null;
    }
}
