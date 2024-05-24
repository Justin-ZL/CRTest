package com.deepwisdom.common.utils;

import com.deepwisdom.common.enums.ExceptionCodeEnum;
import com.deepwisdom.common.exception.RRException;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.Result;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

/**
 * @ClassName: MinioUtils
 * @Description: Minio操作类
 * @Author: justin(zhanglei @ fuzhi.ai)
 * @Date: 2021/12/1 14:02
 * @Version: 1.0
 */
@Slf4j
@Component
public class MinioUtils {

    @Autowired
    private MinioClient client;

    /**
     * 创建bucket
     * @param bucketName bucket名称
     */
    public void createBucket(String bucketName) {
        try {
            if (!client.bucketExists(bucketName)) {
                client.makeBucket(bucketName);
            }
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        } catch (RegionConflictException e) {
            e.printStackTrace();
        }
    }

    /**
     * @desc 上传文件
     * @param in  文件流
     * @param bucketName 存储桶
     * @param filePath 文件名(路径/文件名)
     * @return
     */
    @Async
    public void uploadFile(InputStream in, String bucketName, String filePath) throws Exception {
            try {
                client.putObject(bucketName, filePath, in, new PutObjectOptions(in.available(), -1));
                in.close();
                log.info(bucketName + '/' + filePath + "上传成功");
            } catch (Exception e) {
                log.error(e.getMessage());
                new RRException(ExceptionCodeEnum.MINIO_FILE_UPLOAD_FAIL_EXCEPTION.getMsg(), ExceptionCodeEnum.MINIO_FILE_UPLOAD_FAIL_EXCEPTION.getCode());
            }
    }

    /**
     * 根据桶名和文件名（路径+文件名，exampl: photo/one.png）删除文件
     * @param bucketName
     * @param objectName
     */
    @SneakyThrows
    public void deleteFile(String bucketName, String objectName){
        Iterable<Result<Item>> list = client.listObjects(bucketName, objectName);
        list.forEach(e -> {
            try {
                client.removeObject(bucketName, e.get().objectName());
            } catch (ErrorResponseException errorResponseException) {
                errorResponseException.printStackTrace();
            } catch (InsufficientDataException insufficientDataException) {
                insufficientDataException.printStackTrace();
            } catch (InternalException internalException) {
                internalException.printStackTrace();
            } catch (InvalidBucketNameException invalidBucketNameException) {
                invalidBucketNameException.printStackTrace();
            } catch (InvalidKeyException invalidKeyException) {
                invalidKeyException.printStackTrace();
            } catch (InvalidResponseException invalidResponseException) {
                invalidResponseException.printStackTrace();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                noSuchAlgorithmException.printStackTrace();
            } catch (XmlParserException xmlParserException) {
                xmlParserException.printStackTrace();
            }
        });
    }

    /**
     *
     * @desc 获取文件可访问的绝对路径，有效期是1天
     * @param bucketName  存储桶
     * @param objectName  存储桶里的对象名称
     * @return List
     */
    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName) {
        return client.presignedGetObject(bucketName, objectName, 60 * 60 * 24);
    }

    /**
     *
     * @desc 根据桶名和文件名（路径+文件名，exampl: photo/one.png）得到预览文件绝对地址，默认有效期7天
     * @param bucketName  存储桶
     * @param objectName  存储桶里的对象名称
     * @return List
     */
    @SneakyThrows
    public String getObjectUrlDefaultExpire(String bucketName, String objectName) {
        return client.presignedGetObject(bucketName, objectName);
    }

    /**
     *
     * @desc 根据桶名和文件名（路径+文件名，exampl: photo/one.png）得到预览文件绝对地址，永久性访问（前提：把存储桶策略设为public）
     * @param bucketName  存储桶
     * @param objectName  存储桶里的对象名称
     * @return List
     */
    @SneakyThrows
    public String getObjectUrlForever(String bucketName, String objectName) {
        String url = client.presignedGetObject(bucketName, objectName);
        // 将访问链接'?'的内容去掉就能得到永久可访问链接
        String foreverUrl = url.substring(0, url.indexOf('?'));
        return foreverUrl;
    }

    /**
     *
     * @desc 获取对象
     * @param bucketName  存储桶
     * @param objectName  存储桶里的对象名称
     * @return List
     */
    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName){
        return client.getObject(bucketName, objectName);
    }

    /**
     * 上传文件全流程，包括创建存储桶、文件名拼接（filePath/fileName）、上传文件、返回文件可访问或下载的绝对路径
     * @param multipartFile 文件
     * @param bucketName 存储桶名
     * @param filePath 文件所在目录路径，example: photo/1
     * @return 文件可访问或下载的绝对路径
     */
    public String uploadFileFlow(MultipartFile multipartFile, String bucketName, String filePath) {
        try {
            if (Objects.isNull(multipartFile)) {
                new RRException(ExceptionCodeEnum.MINIO_FILE_EMPTY_EXCEPTION.getMsg(), ExceptionCodeEnum.MINIO_FILE_EMPTY_EXCEPTION.getCode());
            }
            // 判断存储桶是否存在
            this.createBucket(bucketName);
            // 获取文件名
            String fileName = multipartFile.getOriginalFilename();
//            if (fileName.indexOf('-') > 0) {
//                fileName = fileName.substring(fileName.indexOf('-') + 1);
//            }
            // 生成新的随机文件名，避免上传同名文件，会覆盖掉原文件
//            String newName = SnowflakeUtil.generateId().toString() + "-" + fileName;
            // 拼接文件名（目录/文件名）
            String path = String.format("%s/%s",  filePath, fileName);
            // 文件上传
            this.uploadFile(multipartFile.getInputStream(), bucketName, path);
            // 返回文件永久访问url
            return this.getObjectUrlForever(bucketName, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
