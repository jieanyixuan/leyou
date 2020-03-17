package com.leyou.item.service;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;

import java.util.List;

/**
 * @author 王俊杰
 * 商品规格参数业务层接口
 */
public interface SpecificationService {
    /**
     * 通过分类id查询详情分组
     * @param cid
     * @return
     */
    List<SpecGroup> queryGroupsByCid(Long cid);

    /**
     * 通过分组id,
     * 或分类id,
     * 或是否是搜索字段,
     * 或是否是通用字段
     * 查询参数集合
     */
    List<SpecParam> queryParams(Long gid,Long cid,Boolean searching,Boolean generic);

    /**
     * 添加分组
     * @param specGroup
     */
    void addGroup(SpecGroup specGroup);

    /**
     * 添加参数
     * @param specParam
     */
    void addParam(SpecParam specParam);

    /**
     * 修改参数信息
     * @param specParam
     */
    void updateParam(SpecParam specParam);

    /**
     * 通过参数id删除参数信息
     * @param pid
     */
    void deleteParamByPid(Long pid);

    /**
     * 修改分组信息
     * @param specGroup
     */
    void updateGroup(SpecGroup specGroup);

    /**
     * 通过组id删除分组信息
     *
     * @param gid
     */
    void deleteGroupByGid(Long gid);
}
