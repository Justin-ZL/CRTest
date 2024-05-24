package com.deepwisdom.modules.datasetcenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.deepwisdom.modules.datasetcenter.pojo.dto.PageSelectDatasetDTO;
import com.deepwisdom.modules.datasetcenter.pojo.entity.DatasetCenterEntity;
import com.deepwisdom.modules.datasetcenter.pojo.vo.DatasetCenterVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName DatasetCenterDao
 * @Description 数据集中心Dao层
 * @Author zhouhangeng
 * @Date 2022/9/22 15:46
 */
@Mapper
public interface DatasetCenterMapper extends BaseMapper<DatasetCenterEntity> {

    /**
     * 分页查询数据集记录列表
     * @param iPage
     * @param pageSelectDatasetDTO
     * @return
     */
    List<DatasetCenterVO> selectPages(IPage<DatasetCenterVO> iPage, @Param("dto") PageSelectDatasetDTO pageSelectDatasetDTO);

    /**
     * 根据id查看数据集记录详情
     * @param id
     * @return
     */
    DatasetCenterVO selectById(Integer id);

    /**
     * 修改数据集记录
     * @param datasetCenterEntity
     * @return
     */
    boolean update(DatasetCenterEntity datasetCenterEntity);
}
