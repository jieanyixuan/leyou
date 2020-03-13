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
     * @param pid
     * @return
     */
    public List<Category> queryCategoriesByPid(Long pid);
}
