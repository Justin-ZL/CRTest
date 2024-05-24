package com.deepwisdom.common.enums;

/**
 * Grpc调用获取host的枚举类
 * @author zhouhangeng
 * @Email zhouhangeng@fuzhi.ai
 * @date 2022/7/7 10:36
 */
public enum GrpcHostEnum {
    LOCAL_DEV_HOST("local", "dev", "localhost"),
    TIANJI_ADMIN_DEV_HOST("tianji_admin", "dev", "dev-tianji-admin-api.act"),
    TIANJI_ADMIN_TEST_HOST("tianji_admin", "test", "dev-tianji-admin-api.act"),
    DEFAULT("default", "dev", "dev-tianji-admin-api.act");

    /**
     * 项目名
     */
    private final String project;

    /**
     * 环境
     */
    private final String env;

    /**
     * 主机
     */
    private final String host;

    private GrpcHostEnum(String project, String env, String host) {
        this.project = project;
        this.env = env;
        this.host = host;
    }

    public String getProject() {
        return project;
    }

    public String getEnv() {
        return env;
    }

    public String getHost() {
        return host;
    }

    public static String getHost(String project, String env) {
        for (GrpcHostEnum value : values()) {
            if (project.equals(value.getProject()) && env.equals(value.getEnv())) {
                return value.getHost();
            }
        }
        return DEFAULT.getHost();
    }

}
