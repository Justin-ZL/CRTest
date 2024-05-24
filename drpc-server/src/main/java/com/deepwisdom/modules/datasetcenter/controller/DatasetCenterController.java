package com.deepwisdom.modules.datasetcenter.controller;

import com.deepwisdom.common.annotation.SysLog;
import com.deepwisdom.common.utils.PageUtils;
import com.deepwisdom.common.utils.R;
import com.deepwisdom.modules.common.controller.BaseController;
import com.deepwisdom.modules.datasetcenter.pojo.entity.DatasetCenterEntity;
import com.deepwisdom.modules.datasetcenter.pojo.vo.DatasetCenterVO;
import com.deepwisdom.modules.datasetcenter.pojo.vo.datasetcenter.UploadDatasetVO;
import com.deepwisdom.modules.datasetcenter.service.DatasetCenterService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @ClassName DatasetCenterController
 * @Description 数据集中心控制层
 * @Author zhouhangeng
 * @Date 2022/9/22 15:35
 */
@RestController
@RequestMapping("/dataset")
public class DatasetCenterController extends BaseController {

    @Resource
    private DatasetCenterService datasetCenterService;

    /**
     * 分页列表
     */
    @RequestMapping("/pageList")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = datasetCenterService.queryPage(params);
        return R.ok().put("data", page);
    }

    /**
     * 新增
     */
    @SysLog("新增数据集记录")
    @RequestMapping("/save")
    @RequiresPermissions("basic:dataset:save")
    public R save(@RequestBody DatasetCenterEntity datasetCenterEntity) {
        return datasetCenterService.save(datasetCenterEntity, getUser()) ? R.ok() : R.error();
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info")
    public R info(@RequestParam Integer id) {
        DatasetCenterVO datasetCenterVO = datasetCenterService.getById(id);
        return R.ok().put("data", datasetCenterVO);
    }

    /**
     * 修改
     */
    @SysLog("修改数据集记录")
    @RequestMapping("/update")
    @RequiresPermissions("basic:dataset:update")
    public R update(@RequestBody DatasetCenterEntity datasetCenterEntity) {
        return datasetCenterService.updateByEntity(datasetCenterEntity, getUser()) ? R.ok() : R.error();
    }

    /**
     * 删除
     */
    @SysLog("删除数据集记录")
    @RequestMapping("/delete")
    @RequiresPermissions("basic:dataset:delete")
    public R delete(@RequestBody Integer[] ids) {
        return datasetCenterService.batchDelete(ids, getUser()) ? R.ok() : R.error();
    }

    /**
     * 数据集文件上传
     */
    @RequestMapping("/upload")
    public R upload(@RequestBody MultipartFile file, String modalTypeExplain, String taskTypeExplain, String industryValue, Integer uploadType) throws UnsupportedEncodingException {
        UploadDatasetVO uploadDatasetVO = datasetCenterService.uploadDataset(file, modalTypeExplain, taskTypeExplain, industryValue, uploadType);
        return R.ok().put("data", uploadDatasetVO);
    }
}
