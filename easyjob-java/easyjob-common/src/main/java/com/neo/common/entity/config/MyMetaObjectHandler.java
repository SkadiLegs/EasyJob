package com.neo.common.entity.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description TODO
 * @Author Lenove
 * @Date 2024/5/27
 * @ClassName MyMetaObjectHandler
 * @MethodName
 * @Params
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", Date.class, new Date()); // 创建时间
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date()); // 更新时间
        this.strictUpdateFill(metaObject, "clientLastSendTime", Date.class, new Date()); // Feedback最后回复时间
    }


}