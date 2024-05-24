package com.deepwisdom.modules.datasetcenter.pojo.dto;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName pageSelectDatasetDTO
 * @Description 分页查询数据集记录DTO
 * @Author zhouhangeng
 * @Date 2022/9/26 16:01
 */
@Data
public class PageSelectDatasetDTO {

    /**
     * 数据集名称
     */
    private String datasetName;

    /**
     * 模态类型id
     */
    private Integer modalType;

    /**
     * 任务类型id
     */
    private Integer taskType;

    /**
     * 行业类型id
     */
    private Integer industryKey;

    /**
     * 上传方式，0代表手动上传，1代表爬虫
     */
    private Integer uploadType;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;
}
