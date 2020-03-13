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
}
