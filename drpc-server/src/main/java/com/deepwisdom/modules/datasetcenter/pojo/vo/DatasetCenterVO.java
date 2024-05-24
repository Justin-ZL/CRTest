package com.deepwisdom.modules.datasetcenter.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName DatasetCenterVO
 * @Description 数据集记录VO
 * @Author zhouhangeng
 * @Date 2022/9/22 15:50
 */
@Data
public class DatasetCenterVO implements Serializable {

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
     * 数据集上传状态
     */
    private Integer uploadStatus;

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
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

}
