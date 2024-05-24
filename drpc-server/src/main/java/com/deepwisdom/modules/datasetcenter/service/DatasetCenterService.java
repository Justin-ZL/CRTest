package com.deepwisdom.modules.datasetcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.deepwisdom.common.utils.PageUtils;
import com.deepwisdom.modules.datasetcenter.pojo.vo.DatasetCenterVO;
import com.deepwisdom.modules.datasetcenter.pojo.vo.datasetcenter.UploadDatasetVO;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import com.deepwisdom.modules.datasetcenter.pojo.entity.DatasetCenterEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @ClassName DatasetCenterService
 * @Description 数据集中心service层接口
 * @Author zhouhangeng
 * @Date 2022/9/22 15:39
 */
public interface DatasetCenterService extends IService<DatasetCenterEntity> {

    /**
     * 分页查询数据集记录列表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 新增数据集记录
     * @param datasetCenterEntity
     * @param user
     * @return
     */
    boolean save(DatasetCenterEntity datasetCenterEntity, SysUserEntity user);

    /**
     *根据id查看数据集记录详情
     * @param id
     * @return
     */
    DatasetCenterVO getById(Integer id);

    /**
     * 根据entity修改数据集记录
     * @param datasetCenterEntity
     * @param user
     * @return
     */
    boolean updateByEntity(DatasetCenterEntity datasetCenterEntity, SysUserEntity user);

    /**
     * 批量删除数据集记录
     * @param ids
     * @param user
     * @return
     */
    boolean batchDelete(Integer[] ids, SysUserEntity user);

    /**
     * 上传数据集
     * @param file
     * @param modalTypeExplain
     * @param taskTypeExplain
     * @param industryValue
     * @param uploadTypeExplain
     * @return
     */
    UploadDatasetVO uploadDataset(MultipartFile file, String modalTypeExplain, String taskTypeExplain, String industryValue, Integer uploadTypeExplain) throws UnsupportedEncodingException;
}
