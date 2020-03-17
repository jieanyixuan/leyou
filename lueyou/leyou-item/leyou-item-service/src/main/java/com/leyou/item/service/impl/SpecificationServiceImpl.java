package com.leyou.item.service.impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.service.SpecificationService;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 王俊杰
 * 规格参数业务层
 */
@Service
@Transactional
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 通过分类id查询详情分组
     *
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup ();
        specGroup.setCid (cid);
        return specGroupMapper.select (specGroup);
    }

    /**
     * 通过分组id,
     * 或分类id,
     * 或是否是搜索字段,
     * 或是否是通用字段
     * 查询参数集合
     *
     * @param gid
     * @param cid
     * @param searching
     * @param generic
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam ();
        specParam.setGroupId (gid);
        specParam.setCid (cid);
        specParam.setSearching (searching);
        specParam.setGeneric (generic);
        return specParamMapper.select (specParam);
    }

    /**
     * 添加分组
     *
     * @param specGroup
     */
    @Override
    public void addGroup(SpecGroup specGroup) {
        specGroupMapper.insertSelective (specGroup);
    }

    /**
     * 添加参数
     *
     * @param specParam
     */
    @Override
    public void addParam(SpecParam specParam) {
        specParamMapper.insertSelective (specParam);
    }

    /**
     * 修改参数信息
     *
     * @param specParam
     */
    @Override
    public void updateParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKeySelective (specParam);
    }

    /**
     * 删除参数信息
     *
     * @param pid
     */
    @Override
    public void deleteParamByPid(Long pid) {
        specParamMapper.deleteByPrimaryKey (pid);
    }

    /**
     * 修改分组信息
     *
     * @param specGroup
     */
    @Override
    public void updateGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey (specGroup);
    }

    /**
     * 通过组id删除分组信息
     *
     * @param gid
     */
    @Override
    public void deleteGroupByGid(Long gid) {
        this.deleteParamByGid (gid);
        specGroupMapper.deleteByPrimaryKey (gid);

    }

    /**
     * 通过gid删除该分组下的所有参数信息
     */
    public void deleteParamByGid(Long gid) {
        SpecParam param = new SpecParam ();
        param.setGroupId (gid);
        specParamMapper.delete (param);
    }

}
