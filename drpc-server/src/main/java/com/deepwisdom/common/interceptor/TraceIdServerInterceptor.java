package com.deepwisdom.common.interceptor;

import com.deepwisdom.common.utils.Constant;
import io.grpc.*;
import lombok.extern.slf4j.Slf4j;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

/**
* @ClassName: TraceIdServerInterceptor.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:12 
* @Description: GRPC服务端关于处理基于Istio链路相关信息的拦截器，主要是实现GRPC包的ServerInterceptor的interceptCall，
                里面主要是处理从上游带过来的基于Istio链路需要的相关字段，并将这些信息存入上下文，串起整个调用链
* @Version: 1.0
*/
@Slf4j
public class TraceIdServerInterceptor implements ServerInterceptor {
  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
    Context ctx = Context.current().withValue(Constant.TRACE_ID_CTX_KEY, metadata.get(Constant.TRACE_ID_METADATA_KEY))
            .withValue(Constant.REQUEST_ID_CTX_KEY,metadata.get(Constant.REQUEST_ID_METADATA_KEY))
            .withValue(Constant.SPAN_ID_CTX_KEY,metadata.get(Constant.SPAN_ID_METADATA_KEY))
            .withValue(Constant.PARENT_SPAN_ID_CTX_KEY,metadata.get(Constant.PARENT_SPAN_ID_METADATA_KEY))
            .withValue(Constant.SAMPLED_CTX_KEY,metadata.get(Constant.SAMPLED_METADATA_KEY))
            .withValue(Constant.FLAGS_ID_CTX_KEY,metadata.get(Constant.FLAGS_ID_METADATA_KEY))
            .withValue(Constant.OT_SPAN_CONTEXT_CTX_KEY,metadata.get(Constant.OT_SPAN_CONTEXT_METADATA_KEY))
            .withValue(Constant.USER_NAME_CTX_KEY,metadata.get(Constant.USER_NAME_METADATA_KEY))
            .withValue(Constant.GRPC_CLIENT_IP,serverCall.getAttributes().get(Grpc.TRANSPORT_ATTR_REMOTE_ADDR).toString());

    return Contexts.interceptCall(ctx, serverCall, metadata, serverCallHandler);
  }
}
