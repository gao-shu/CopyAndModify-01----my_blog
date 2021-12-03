package com.gaoshu.controller;


import cn.hutool.db.Page;
import com.gaoshu.common.PageEntity;
import com.gaoshu.entity.PO.Article;
import com.gaoshu.entity.VO.Result;
import com.gaoshu.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author gaoshu
 * @since 2021-11-30
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    public Result<List<Article>> getArticleList(@RequestBody PageEntity page){
        return Result.ok(articleService.getArticleList(page));
    }
}

