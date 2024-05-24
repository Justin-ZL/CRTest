package com.deepwisdom.modules.datasetcenter.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName DatasetCenterEntity
 * @Description 数据集实体类
 * @Author zhouhangeng
 * @Date 2022/9/22 15:28
 */
@Data
@TableName("dataset")
public class DatasetCenterEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键ID
     */
    @TableId
    private Integer id;

    /**
     * 数据集名称
     */
    private String datasetName;

    /**
     * 模态类型id
     */
    private Integer modalType;

    /**
     * 模态类型名称
     */
    private String modalTypeExplain;

    /**
     * 任务类型id
     */
    private Integer taskType;

    /**
     * 任务类型名称
     */
    private String taskTypeExplain;

    /**
     * 行业类型id
     */
    private Integer industryKey;

    /**
     * 行业类型名称
     */
    private String industryValue;

    /**
     * 上传方式，0代表手动上传，1代表爬虫
     */
    private Integer uploadType;

    /**
     * 数据集可下载url
     */
    private String datasetDownloadUrl;

    /**
     * 数据集来源url
     */
    private String datasetSourceUrl;

    /**
     * 数据集备注说明
     */
    private String datasetExplain;

    /**
     * 数据集上传状态
     */
    private Integer uploadStatus;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人id
     */
    private Long updateId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 0代表未删除，1代表删除
     */
    private Integer isDel;

}
