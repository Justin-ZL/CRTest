package com.deepwisdom.common.interceptor;

import com.deepwisdom.common.utils.Constant;
import io.grpc.*;

/**
* @ClassName: JwtClientInterceptor.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:10
* @Description: GRPC客户端关于处理JWT（JSON WEB TOKEN）的拦截器，主要是实现GRPC包的ClientInterceptor的interceptCall，
                里面主要是处理从上下文获取JWT相关的字段并写入到Metedata（header），用于传递到下游服务中
* @Version: 1.0
*/
public class JwtClientInterceptor implements ClientInterceptor {
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        headers.put(Constant.JWT_METADATA_KEY, Constant.JWT_CTX_KEY.get());
        super.start(responseListener, headers);
      }
    };
  }
}
