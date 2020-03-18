package com.leyou.item.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.service.CategoryService;
import com.leyou.item.service.GoodsService;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.base.insert.InsertSelectiveMapper;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

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

    /**
     * 添加新商品
     *
     * @param spuBo
     */
    @Override
    @Transactional
    public void saveGoods(SpuBo spuBo) {
        //新增spu
        spuBo.setId(null);
        spuBo.setSaleable(true);
        spuBo.setValid(true);
        spuBo.setCreateTime(new Date ());
        spuBo.setLastUpdateTime(spuBo.getCreateTime());
        this.spuMapper.insertSelective(spuBo);
        //新增spuDetail
        SpuDetail spuDetail = spuBo.getSpuDetail();
        spuDetail.setSpuId(spuBo.getId());
        this.spuDetailMapper.insertSelective(spuDetail);

        saveSkuAndStock (spuBo);


    }


    /**
     * 保存sku和库存信息
     * @param spuBo
     */
    private void saveSkuAndStock(SpuBo spuBo) {
        spuBo.getSkus ().forEach (sku -> {
            //新增sku
            sku.setSpuId(spuBo.getId());
            sku.setCreateTime(new Date ());
            sku.setLastUpdateTime(sku.getCreateTime());
            this.skuMapper.insertSelective (sku);

            //新增库存
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            this.stockMapper.insertSelective(stock);
        });
    }

    /**
     * 根据spuId查询SpuDetail
     *
     * @param spuId
     * @return
     */
    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        return spuDetailMapper.selectByPrimaryKey (spuId);
    }

    /**
     * 通过spuId查询该商品的sku集合
     *
     * @param spuId
     * @return
     */
    @Override
    public List<Sku> querySkusBySpuId(Long spuId) {
        Sku sku = new Sku ();
        sku.setSpuId (spuId);
        List<Sku> skus = skuMapper.select (sku);
        skus.forEach (s -> {
            Stock stock = stockMapper.selectByPrimaryKey (s.getId ());
            s.setStock (stock.getStock ());
        });
        return skus;
    }

    /**
     * 更新商品信息
     *
     * @param spuBo
     * @return
     */
    @Override
    public void updateGoods(SpuBo spuBo) {
        Sku sku = new Sku ();
        sku.setSpuId (spuBo.getId ());
        List<Sku> skus = skuMapper.select (sku);
        skus.forEach (s -> {
            //删除库存信息
            stockMapper.deleteByPrimaryKey (s.getId ());
            //删除sku
            skuMapper.deleteByPrimaryKey (s.getId ());
        });

        //新增sku和库存
        this.saveSkuAndStock (spuBo);
        //更新spu和spuDetail
        spuBo.setCreateTime (null);
        spuBo.setLastUpdateTime (new Date ());
        spuBo.setValid (null);
        spuBo.setSaleable (null);
        spuMapper.updateByPrimaryKeySelective (spuBo);
        spuDetailMapper.updateByPrimaryKeySelective (spuBo.getSpuDetail ());

    }
}
