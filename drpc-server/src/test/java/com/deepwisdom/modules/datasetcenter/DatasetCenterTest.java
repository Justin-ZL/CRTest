package com.deepwisdom.modules.datasetcenter;

import com.deepwisdom.common.utils.PageUtils;
import com.deepwisdom.modules.common.entity.SysUserEntity;
import com.deepwisdom.modules.datasetcenter.pojo.entity.DatasetCenterEntity;
import com.deepwisdom.modules.datasetcenter.pojo.vo.DatasetCenterVO;
import com.deepwisdom.modules.datasetcenter.service.DatasetCenterService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DatasetCenterTest
 * @Description TODO
 * @Author zhouhangeng
 * @Date 2022/9/27 10:04
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class DatasetCenterTest {


    @Autowired
    private DatasetCenterService datasetCenterService;

    @Test
    public void listTest() {
        Map<String, Object> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "10");
        PageUtils page = datasetCenterService.queryPage(params);
        log.info("page: {}", page);
    }

    @Test
    public void infoTest() {
        DatasetCenterVO datasetCenterVO = datasetCenterService.getById(9);
        log.info("datasetCenterVO: {}", datasetCenterVO);
    }

    @Test
    public void saveTest() {
        DatasetCenterEntity datasetCenterEntity = new DatasetCenterEntity();
        datasetCenterEntity.setDatasetName("测试数据集");
        datasetCenterEntity.setModalType(0);
        datasetCenterEntity.setModalTypeExplain("表格");
        datasetCenterEntity.setTaskType(1);
        datasetCenterEntity.setTaskTypeExplain("多分类");
        datasetCenterEntity.setIndustryKey(2);
        datasetCenterEntity.setIndustryValue("物流供应链");
        datasetCenterEntity.setUploadType(0);
        datasetCenterEntity.setDatasetDownloadUrl("http://192.168.50.13:9000/dataset-center/手动上传/空白模板/MBB-中文文本分类/城市安防/简介测试视频.mp4");
        datasetCenterEntity.setDatasetExplain("测试111");
        datasetCenterEntity.setDatasetSourceUrl("fafadfaf");
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(1L);
        boolean save = datasetCenterService.save(datasetCenterEntity, sysUserEntity);
        log.info("save: {}", save);
    }

    @Test
    public void updateTest() {
        DatasetCenterEntity datasetCenterEntity = new DatasetCenterEntity();
        datasetCenterEntity.setId(10);
        datasetCenterEntity.setDatasetName("测试数据集2");
        datasetCenterEntity.setModalType(0);
        datasetCenterEntity.setModalTypeExplain("表格");
        datasetCenterEntity.setTaskType(1);
        datasetCenterEntity.setTaskTypeExplain("多分类");
        datasetCenterEntity.setIndustryKey(2);
        datasetCenterEntity.setIndustryValue("物流供应链");
        datasetCenterEntity.setUploadType(0);
        datasetCenterEntity.setDatasetDownloadUrl("http://192.168.50.13:9000/dataset-center/手动上传/表格/多分类/物流供应链/734340668227571712-Default中文.jpg");
        datasetCenterEntity.setDatasetExplain("测试222");
        datasetCenterEntity.setDatasetSourceUrl("测试222");
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(1L);
        boolean update = datasetCenterService.updateByEntity(datasetCenterEntity, sysUserEntity);
        log.info("update: {}", update);
    }

    @Test
    public void deleteTest() {
        Integer[] ids = new Integer[]{10};
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(1L);
        boolean delete = datasetCenterService.batchDelete(ids, sysUserEntity);
        log.info("delete: {}", delete);
    }

    @Test
    public void uploadTest() throws IOException {
        String modalTypeExplain = "视频";
        String taskTypeExplain = "视频传输压缩-视频超分";
        String industryValue = "城市安防";
        Integer uploadType = 0;
        String path = URLDecoder.decode(this.getClass().getResource("/static/测试图片1.jpg").getPath(), "UTF-8");
        File file = new File(path);
        MultipartFile mulFile = new MockMultipartFile(
                "测试图片1.jpg", //文件名
                "测试图片1.jpg", //originalName 相当于上传文件在客户机上的文件名
                ContentType.APPLICATION_OCTET_STREAM.toString(), //文件类型
                new FileInputStream(file) //文件流
        );
        datasetCenterService.uploadDataset(mulFile, modalTypeExplain, taskTypeExplain, industryValue, uploadType);
    }
}
