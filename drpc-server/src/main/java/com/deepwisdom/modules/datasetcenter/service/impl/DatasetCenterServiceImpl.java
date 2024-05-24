package com.deepwisdom.modules.datasetcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.deepwisdom.common.enums.ExceptionCodeEnum;
import com.deepwisdom.common.exception.RRException;
import com.deepwisdom.common.utils.*;
import com.deepwisdom.config.CustomizeThreadPoolConfig;
import com.deepwisdom.modules.datasetcenter.mapper.DatasetCenterMapper;
import com.deepwisdom.modules.datasetcenter.pojo.dto.PageSelectDatasetDTO;
import com.deepwisdom.modules.datasetcenter.pojo.vo.DatasetCenterVO;
import com.deepwisdom.modules.datasetcenter.pojo.vo.datasetcenter.UploadDatasetVO;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import com.deepwisdom.modules.datasetcenter.pojo.entity.DatasetCenterEntity;
import com.deepwisdom.modules.datasetcenter.service.DatasetCenterService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * @ClassName DatasetCenterServiceImpl
 * @Description 数据集中心service层实现类
 * @Author zhouhangeng
 * @Date 2022/9/22 15:44
 */
@Service
public class DatasetCenterServiceImpl extends ServiceImpl<DatasetCenterMapper, DatasetCenterEntity> implements DatasetCenterService {

    @Resource
    private DatasetCenterMapper datasetCenterMapper;

    @Resource
    private MinioUtils minioUtils;

    @Resource
    RedisUtils redisUtils;

    @Resource
    CustomizeThreadPoolConfig customizeThreadPoolConfig;

    @Value("${boto3.endpoint}")
    private String minioEndPoint;

    /**
     * 数据集中心MinIO存储桶名
     */
    private static String BUCKET_NAME = Constant.DATASET_CENTER;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        PageSelectDatasetDTO pageSelectDatasetDTO = ConvertUtil.parseMap2Object(params, PageSelectDatasetDTO.class);
        IPage<DatasetCenterVO> iPage = new Query<DatasetCenterVO>().getPage(params);
        List<DatasetCenterVO> list = datasetCenterMapper.selectPages(iPage, pageSelectDatasetDTO);
        iPage.setRecords(list);
        return new PageUtils(iPage);
    }

    @Override
    public boolean save(DatasetCenterEntity datasetCenterEntity, SysUserEntity user) {
        DatasetCenterEntity entity = this.getOne(new QueryWrapper<DatasetCenterEntity>()
                .eq("modal_type", datasetCenterEntity.getModalType())
                .eq("task_type", datasetCenterEntity.getTaskType())
                .eq("dataset_name", datasetCenterEntity.getDatasetName())
                .eq("is_del", 0)
        );
        if (!Objects.isNull(entity)) {
            throw new RRException(ExceptionCodeEnum.DATASET_CENTER_NAME_EXIST_EXCEPTION.getMsg(), ExceptionCodeEnum.DATASET_CENTER_NAME_EXIST_EXCEPTION.getCode());
        }
        if (null != redisUtils.get(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()))) {
            datasetCenterEntity.setUploadStatus(Integer.parseInt(redisUtils.get(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()))));
        } else {
            datasetCenterEntity.setUploadStatus(2);
            redisUtils.set(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()), 2);
        }
        Date date = new Date(System.currentTimeMillis());
        datasetCenterEntity.setCreateId(user.getUserId());
        datasetCenterEntity.setCreateTime(date);
        datasetCenterEntity.setUpdateId(user.getUserId());
        datasetCenterEntity.setUpdateTime(date);
        return this.save(datasetCenterEntity);
    }

    @Override
    public DatasetCenterVO getById(Integer id) {
        return datasetCenterMapper.selectById(id);
    }

    @Override
    public boolean updateByEntity(DatasetCenterEntity datasetCenterEntity, SysUserEntity user) {
        DatasetCenterEntity entity = this.getOne(new QueryWrapper<DatasetCenterEntity>()
                .eq("modal_type", datasetCenterEntity.getModalType())
                .eq("task_type", datasetCenterEntity.getTaskType())
                .eq("dataset_name", datasetCenterEntity.getDatasetName())
                .eq("is_del", 0)
        );
        if (!Objects.isNull(entity) && !entity.getId().equals(datasetCenterEntity.getId())) {
            throw new RRException(ExceptionCodeEnum.DATASET_CENTER_NAME_EXIST_EXCEPTION.getMsg(), ExceptionCodeEnum.DATASET_CENTER_NAME_EXIST_EXCEPTION.getCode());
        }
        if (null != redisUtils.get(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()))) {
            datasetCenterEntity.setUploadStatus(Integer.parseInt(redisUtils.get(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()))));
        } else {
            datasetCenterEntity.setUploadStatus(2);
            redisUtils.set(RedisKeys.getDatasetUploadStatusKey(datasetCenterEntity.getDatasetDownloadUrl()), 2);
        }
        Date date = new Date(System.currentTimeMillis());
        datasetCenterEntity.setUpdateTime(date);
        datasetCenterEntity.setUpdateId(user == null ? -1:user.getUserId());
        return datasetCenterMapper.update(datasetCenterEntity);
    }

    @Override
    public boolean batchDelete(Integer[] ids, SysUserEntity user) {
        ArrayList<DatasetCenterEntity> list = new ArrayList<>();
        Date date = new Date(System.currentTimeMillis());
        for (Integer id : ids) {
            DatasetCenterEntity entity = new DatasetCenterEntity();
            //置为被删除状态
            entity.setIsDel(1);
            entity.setId(id);
            entity.setUpdateId(user.getUserId());
            entity.setUpdateTime(date);
            list.add(entity);
        }
        return this.updateBatchById(list);
    }

    @Override
    public UploadDatasetVO uploadDataset(MultipartFile file, String modalTypeExplain, String taskTypeExplain, String industryValue, Integer uploadType) throws UnsupportedEncodingException {
        DatasetCenterEntity datasetCenterEntity = new DatasetCenterEntity();
        String uploadTypeExplain = uploadType == 0 ? "手动上传" : "爬虫";
        String filePath = uploadTypeExplain + "/" + modalTypeExplain + "/" + taskTypeExplain + "/" + industryValue;
        String fileURl = URLDecoder.decode(minioEndPoint + "/" + BUCKET_NAME + "/" + filePath + "/" + file.getOriginalFilename(), "utf-8");
        UploadDatasetVO uploadDatasetVO = new UploadDatasetVO();
        uploadDatasetVO.setDatasetName(file.getOriginalFilename());
        uploadDatasetVO.setDatasetDownloadUrl(fileURl);
        try {
            // 异步上传文件
            minioUtils.uploadFileFlow(file, BUCKET_NAME, filePath);
            // 将状态改为已上传
            if (null != redisUtils.get(RedisKeys.getDatasetUploadStatusKey(fileURl))) {
                datasetCenterEntity.setUploadStatus(1);
                DatasetCenterEntity entity = getOne(new QueryWrapper<DatasetCenterEntity>()
                        .eq("upload_type", uploadType)
                        .eq("modal_type_explain", modalTypeExplain)
                        .eq("task_type_explain", taskTypeExplain)
                        .eq("industry_value", industryValue)
                        .eq("dataset_name", file.getOriginalFilename())
                );
                datasetCenterEntity.setId(entity.getId());
                datasetCenterMapper.update(datasetCenterEntity);
            }
            redisUtils.set(RedisKeys.getDatasetUploadStatusKey(fileURl), 1);
        } catch (Exception e) {
            // 将状态改为上传失败
            if (null != redisUtils.get(RedisKeys.getDatasetUploadStatusKey(fileURl))) {
                datasetCenterEntity.setUploadStatus(0);
                DatasetCenterEntity entity = getOne(new QueryWrapper<DatasetCenterEntity>()
                        .eq("upload_type", uploadType)
                        .eq("modal_type_explain", modalTypeExplain)
                        .eq("task_type_explain", taskTypeExplain)
                        .eq("industry_value", industryValue)
                        .eq("dataset_name", file.getOriginalFilename())
                );
                datasetCenterEntity.setId(entity.getId());
                datasetCenterMapper.update(datasetCenterEntity);
            }
            redisUtils.set(RedisKeys.getDatasetUploadStatusKey(fileURl), 0);
            e.printStackTrace();
        }
        return uploadDatasetVO;
    }

}
