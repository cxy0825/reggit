package com.cxy.reggit.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
  @Override
  public void insertFill(MetaObject metaObject) {
    log.info("公共字段插入  自动更新");
    this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    this.strictInsertFill(metaObject, "createUser", Long.class, BaseContext.getCurrentId());
    this.strictInsertFill(metaObject, "updateUser", Long.class, BaseContext.getCurrentId());
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    log.info("公共字段更新  自动更新");
    this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
    this.strictInsertFill(metaObject, "updateUser", Long.class, BaseContext.getCurrentId());
  }
}
