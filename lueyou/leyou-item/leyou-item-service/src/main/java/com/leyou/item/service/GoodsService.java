package com.leyou.item.service;

import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;

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
}
