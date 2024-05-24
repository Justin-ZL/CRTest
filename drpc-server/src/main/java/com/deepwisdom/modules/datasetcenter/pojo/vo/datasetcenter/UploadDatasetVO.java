package com.deepwisdom.modules.datasetcenter.pojo.vo.datasetcenter;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DatasetCenterVO
 * @Description 数据集记录VO
 * @Author zhouhangeng
 * @Date 2022/9/22 15:50
 */
@Data
public class UploadDatasetVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 数据集名称
     */
    private String datasetName;

    /**
     * 文件可下载url
     */
    private String datasetDownloadUrl;


}
