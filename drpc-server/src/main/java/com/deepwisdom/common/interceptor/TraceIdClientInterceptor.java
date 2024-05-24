package com.deepwisdom.common.interceptor;

import com.deepwisdom.common.utils.Constant;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

/**
* @ClassName: TraceIdClientInterceptor.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:12
* @Description: GRPC客户端关于处理基于Istio链路相关的信息的拦截器，主要是实现GRPC包的ClientInterceptor的interceptCall，
                里面主要是处理从上下文获取基于Istio链路需要的信息，并写入到Metadata中，用于传递到下游服务中，串起整个调用链
* @Version: 1.0
*/
@Slf4j
public class TraceIdClientInterceptor implements ClientInterceptor {
  @Override
  public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> methodDescriptor, CallOptions callOptions, Channel channel) {
    return new ForwardingClientCall.SimpleForwardingClientCall<ReqT, RespT>(channel.newCall(methodDescriptor, callOptions)) {
      @Override
      public void start(Listener<RespT> responseListener, Metadata headers) {
        if (Constant.TRACE_ID_CTX_KEY.get() != null) {
          headers.put(Constant.TRACE_ID_METADATA_KEY, Constant.TRACE_ID_CTX_KEY.get());
        }
        if (Constant.REQUEST_ID_CTX_KEY.get() != null) {
          headers.put(Constant.REQUEST_ID_METADATA_KEY, Constant.REQUEST_ID_CTX_KEY.get());
        }
        if (Constant.SPAN_ID_CTX_KEY.get() != null) {
          headers.put(Constant.SPAN_ID_METADATA_KEY, Constant.SPAN_ID_CTX_KEY.get());
        }
        if (Constant.PARENT_SPAN_ID_CTX_KEY.get() != null) {
          headers.put(Constant.PARENT_SPAN_ID_METADATA_KEY, Constant.PARENT_SPAN_ID_CTX_KEY.get());
        }
        if (Constant.SAMPLED_CTX_KEY.get() != null) {
          headers.put(Constant.SAMPLED_METADATA_KEY, Constant.SAMPLED_CTX_KEY.get());
        }
        if (Constant.FLAGS_ID_CTX_KEY.get() != null) {
          headers.put(Constant.FLAGS_ID_METADATA_KEY, Constant.FLAGS_ID_CTX_KEY.get());
        }
        if (Constant.OT_SPAN_CONTEXT_CTX_KEY.get() != null) {
          headers.put(Constant.OT_SPAN_CONTEXT_METADATA_KEY, Constant.OT_SPAN_CONTEXT_CTX_KEY.get());
        }
        log.info(headers.toString());
        super.start(responseListener, headers);
      }
    };
  }
}
