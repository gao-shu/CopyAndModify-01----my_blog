package com.site.blog.my.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Title: BaseMetaObjectHandler
 * @Description: mybatis-plus
 * @author: gaoshu
 * @date: 2021/12/4 16:59
 */
@Component
public class BaseMetaObjectHandler implements MetaObjectHandler {
    public static final String FIELD_CREATE_TIME = "createTime";
    public static final String FIELD_UPDATE_TIME = "updateTime";
    public static final String FIELD_CREATE_USER = "createUserId";
    public static final String COMMENT_CREATE_TIME = "commentCreateTime";

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.strictInsertFill(metaObject, FIELD_CREATE_TIME, Date.class, date);
        this.strictInsertFill(metaObject, COMMENT_CREATE_TIME, Date.class, date);
    }


    @Override
    public MetaObjectHandler fillStrategy(MetaObject metaObject, String fieldName, Object fieldVal) {
        setFieldValByName(fieldName, fieldVal, metaObject);
        return this;
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, FIELD_UPDATE_TIME, Date.class, new Date());
        TableInfo tableInfo = findTableInfo(metaObject);
    }
}
