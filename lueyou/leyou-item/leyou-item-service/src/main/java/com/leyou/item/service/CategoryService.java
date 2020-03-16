package com.leyou.item.service;

import com.leyou.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王俊杰
 * 商品分类的业务层接口
 */
public interface CategoryService {
    /**
     * 根据父类目id查询子类目
     *
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid);

    /**
     * 通过ids查询分类名集合
     *
     * @param ids
     * @return
     */
    public List<String> queryNamesByIds(List<Long> ids);

    /**
     * 根据品牌id查询品牌所属分类
     */
    List<Category> findCategoriesByBid(Long bid);
}