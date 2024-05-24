package com.deepwisdom.common.utils;

import io.grpc.Context;
import io.grpc.Metadata;

import static io.grpc.Metadata.ASCII_STRING_MARSHALLER;

/**
 * @ClassName: Constant.java
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/8/2 16:22
 * @Description: 项目的公共常量
 * @Version: 1.0
 */
public class Constant {
    /**
     * 超级管理员ID
     */
    public static final int SUPER_ADMIN = 1;
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     * 升序
     */
    public static final String ASC = "asc";

    public static final String JWT_SECRET = "jwt-secret";

    public static final Context.Key<String> USER_ID_CTX_KEY = Context.key("userId");

    public static final Context.Key<String> USER_NAME_CTX_KEY = Context.key("userName");

    public static final Context.Key<String> GRPC_CLIENT_IP = Context.key("clientIp");

    public static final Context.Key<String> JWT_CTX_KEY = Context.key("jwt");

    public static final Context.Key<String> TRACE_ID_CTX_KEY = Context.key("traceId");
    public static final Context.Key<String> REQUEST_ID_CTX_KEY = Context.key("requestId");
    public static final Context.Key<String> SPAN_ID_CTX_KEY = Context.key("spanId");
    public static final Context.Key<String> PARENT_SPAN_ID_CTX_KEY = Context.key("parentspanId");
    public static final Context.Key<String> SAMPLED_CTX_KEY = Context.key("sampled");
    public static final Context.Key<String> FLAGS_ID_CTX_KEY = Context.key("flags");
    public static final Context.Key<String> OT_SPAN_CONTEXT_CTX_KEY = Context.key("context");

    public static final Context.Key<String> CLIENT_ID_KEY = Context.key("clientId");

    public static final Metadata.Key<String> CLIENT_ID_MD_KEY = Metadata.Key.of("client-id", ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> USER_NAME_METADATA_KEY = Metadata.Key.of("user-name", ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> JWT_METADATA_KEY = Metadata.Key.of("jwt", ASCII_STRING_MARSHALLER);

    public static final Metadata.Key<String> TRACE_ID_METADATA_KEY = Metadata.Key.of("x-b3-traceid", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> REQUEST_ID_METADATA_KEY = Metadata.Key.of("x-request-id", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> SPAN_ID_METADATA_KEY = Metadata.Key.of("x-b3-spanid", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> PARENT_SPAN_ID_METADATA_KEY = Metadata.Key.of("x-b3-parentspanid", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> SAMPLED_METADATA_KEY = Metadata.Key.of("x-b3-sampled", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> FLAGS_ID_METADATA_KEY = Metadata.Key.of("x-b3-flags", ASCII_STRING_MARSHALLER);
    public static final Metadata.Key<String> OT_SPAN_CONTEXT_METADATA_KEY = Metadata.Key.of("x-ot-span-context", ASCII_STRING_MARSHALLER);

    /**
     * 数据集中心存储桶
     */
    public static final String DATASET_CENTER = "dataset-center";

}
