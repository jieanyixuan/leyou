package com.leyou.item.service;

import com.leyou.common.pojo.PageResult;
import com.leyou.pojo.Brand;

import java.util.List;

/**
 * @author 王俊杰
 * 品牌页面的业务接口
 */
public interface BrandService {
    /**
     * 根据查询条件分页查询并排序查询品牌信息
     * @param key  品牌名模糊查询或者品牌首字母查询
     * @param page 当前页数
     * @param rows 每页显示条数
     * @param sortBy 排序条件
     * @param desc 排序规则
     * @return
     */
    public PageResult<Brand> queryBrandsByPage(String key,Integer page,Integer rows,String sortBy,Boolean desc);

    /**
     * 新增品牌
     * @param brand
     * @param cid
     */
    public void insertBrand(Brand brand, List<Long> cid);
}
