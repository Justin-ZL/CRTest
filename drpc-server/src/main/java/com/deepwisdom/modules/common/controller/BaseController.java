package com.deepwisdom.modules.common.controller;

import com.deepwisdom.common.enums.ExceptionCodeEnum;
import com.deepwisdom.common.exception.RRException;
import com.deepwisdom.common.utils.MinioUtils;
import com.deepwisdom.common.utils.RedisKeys;
import com.deepwisdom.common.utils.RedisUtils;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Controller公共组件
 *
 * @author justin zhanglei@fuzhi.ai
 */
public abstract class BaseController {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MinioUtils minioUtils;

    protected SysUserEntity getUser() {
        SysUserEntity user = redisUtils.get(RedisKeys.getUserSessionKey(request.getHeader("token")), SysUserEntity.class);
        return user;
    }

}
