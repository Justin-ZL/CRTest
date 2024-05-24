/**
 * deepwisdom
 */

package com.deepwisdom.modules.oauth2;

import com.alibaba.fastjson.JSONObject;
import com.deepwisdom.common.utils.RedisKeys;
import com.deepwisdom.common.utils.RedisUtils;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * 认证
 *
 * @author justin zhanglei@fuzhi.ai
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Object principal = principals.getPrimaryPrincipal();
        SysUserEntity user = JSONObject.parseObject(String.valueOf(principal), SysUserEntity.class);

        //用户权限列表
        Set<String> permsSet = redisUtils.get(RedisKeys.getUserPermissionsKey(String.valueOf(user.getUserId())), Set.class);

        //权限缓存失效，需要重新登录刷新
        if(permsSet == null){
            throw new IncorrectCredentialsException("登录失效，请重新登录");
        }

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accessToken = (String) token.getPrincipal();

        String user = redisUtils.get(RedisKeys.getUserSessionKey(accessToken));

        //token失效
        if(user == null){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        SysUserEntity sysUserEntity = JSONObject.parseObject(user,SysUserEntity.class);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
    }
}
