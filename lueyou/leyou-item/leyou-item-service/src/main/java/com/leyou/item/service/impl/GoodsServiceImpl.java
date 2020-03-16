package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import com.leyou.pojo.Spu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王俊杰
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private CategoryService categoryService;
    /**
     * 根据条件分页查询spu
     *
     * @param key
     * @param saleable
     * @param page 当前页码
     * @param rows 每页显示数量
     * @return
     */
    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows) {

        //构件查询条件
        Example example = new Example (Spu.class);
        Example.Criteria criteria = example.createCriteria ();

        //通过标题模糊查询
        if (StringUtils.isNotBlank (key)) {
            criteria.andLike ("title","%" + key + "%");
        }

        //添加上下架的过滤条件
        if (saleable != null) {
            criteria.andEqualTo ("saleable",saleable);
        }

        //分页
        PageHelper.startPage (page,rows);

        //执行查询
        List<Spu> spus = spuMapper.selectByExample (example);

        //spu集合转化spuBo集合
        List<SpuBo> boList = spus.stream ().map (spu -> {
            SpuBo spuBo = new SpuBo ();
            BeanUtils.copyProperties (spu,spuBo);

            //设置品牌名称bname
            spuBo.setBname ( brandMapper.selectByPrimaryKey (spu.getBrandId ()).getName ());
            //设置分类名cname c1-c2-c3
            List<String> names = categoryService.queryNamesByIds (Arrays.asList (spu.getCid1 (), spu.getCid2 (), spu.getCid3 ()));
            spuBo.setCname (StringUtils.join (names,"-"));
            return spuBo;
        }).collect (Collectors.toList ());

        //创建pageInfo
        PageInfo<SpuBo> pageInfo = new PageInfo<> (boList);


        return new PageResult<SpuBo> (pageInfo.getTotal (),pageInfo.getList ());
    }
}
