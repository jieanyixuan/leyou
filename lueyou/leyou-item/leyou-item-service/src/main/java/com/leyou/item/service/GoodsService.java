package com.leyou.item.service;

import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpuDetail;

import java.util.List;

/**
 * @author 王俊杰
 * 商品业务层接口
 */
public interface GoodsService {
    /**
     * 根据条件分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer page, Integer rows);

    /**
     * 添加新商品
     * @param spuBo
     */
    void saveGoods(SpuBo spuBo);

    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 通过spuId查询该商品的sku集合
     * @param spuId
     * @return
     */
    List<Sku> querySkusBySpuId(Long spuId);

    /**
     * 更新商品信息
     * @param spuBo
     * @return
     */
    void updateGoods(SpuBo spuBo);
}
