package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.service.BrandService;
import com.leyou.pojo.Brand;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author 王俊杰
 * 品牌查询业务层
 */
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    /**
     * 根据查询条件分页查询并排序查询品牌信息
     *
     * @param key    品牌名模糊查询或者品牌首字母查询
     * @param page   当前页数
     * @param rows   每页显示条数
     * @param sortBy 排序条件
     * @param desc   排序规则
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        //初始化example对象
        Example example =new Example (Brand.class);
        Example.Criteria criteria = example.createCriteria (); //添加条件
        //根据品牌名模糊查询或者品牌首字母查询
        if (StringUtils.isNotBlank (key)) {
            criteria.andLike ("name","%" + key + "%")
                    .orEqualTo ("letter","key");
        }
        //添加分页条件
        PageHelper.startPage (page,rows);
        //添加排序条件
        if (StringUtils.isNotBlank(sortBy)) {
            example.setOrderByClause(sortBy + " " + (desc ? "desc" : "asc"));
        }

        List<Brand> brands = brandMapper.selectByExample (example);

        //封装成PageInfo
        PageInfo<Brand> pageInfo = new PageInfo<> (brands);

        return new PageResult<Brand> (pageInfo.getTotal (),pageInfo.getList ());
    }

    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void insertBrand(Brand brand, List<Long> cids) {
        //先插入品牌信息
        brandMapper.insertSelective (brand);
        //再添加品牌和分类的关联信息
        cids.forEach (cid -> {
            brandMapper.addToCategoryAndBrand (cid,brand.getId ());
        });
    }

    /**
     * 修改品牌信息
     *
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void update(Brand brand, List<Long> cids) {
        brandMapper.updateByPrimaryKeySelective (brand);

        //对于多表的更新操作,就是删除旧的添加新的
        brandMapper.deleteCategoryAndBrandByBid(brand.getId ());
        cids.stream ().forEach (cid -> {
            brandMapper.addToCategoryAndBrand (cid,brand.getId ());
        });
    }

    /**
     * 删除品牌信息
     *
     * @param bid
     */
    @Override
    @Transactional
    public void delete(Long bid) {
        //先删除关联分类信息
        brandMapper.deleteCategoryAndBrandByBid (bid);
        //再删除品牌信息
        brandMapper.deleteByPrimaryKey (bid);
    }
}
