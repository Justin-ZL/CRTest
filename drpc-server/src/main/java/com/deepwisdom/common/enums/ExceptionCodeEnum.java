package com.deepwisdom.common.enums;

/**
 * 错误码枚举类
 * @author zhouhangeng
 * @Email zhouhangeng@fuzhi.ai
 * @date 2022/8/12 16:30
 */
public enum ExceptionCodeEnum {
    // 常见异常
    COMMON_PARM_EMPTY_EXCEPTION(10000, "参数不能为空"),

    // Redis相关异常
    REDIS_SERVICE_EXCEPTION(10100, "Redis服务异常"),

    // MinIO相关异常
    MINIO_FILE_EMPTY_EXCEPTION(10200, "MinIO上传文件为空"),
    MINIO_FILE_UPLOAD_FAIL_EXCEPTION(10201, "MinIO文件上传失败"),
    MINIO_BUCKET_NOT_EXIST_EXCEPTION(10202, "MinIO存储桶不存在"),

    // MySQL相关异常
    MYSQL_SQL_INVALID_STRING_EXCEPTION(10300, "SQL包含非法字符"),

    // 数据集中心相关异常
    DATASET_CENTER_NAME_EXIST_EXCEPTION(20000, "该模态、任务下已存在同名数据集");

    private final int code;

    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ExceptionCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
