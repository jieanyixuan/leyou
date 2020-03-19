package com.leyou.item.service.impl;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.service.CategoryService;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 王俊杰
 * 商品分类的业务层
 */

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 根据父类目id查询子类目
     *
     * @param pid parentId
     * @return
     */
    @Override
    public List<Category> queryCategoriesByPid(Long pid) {
        Category record = new Category ();
        record.setParentId (pid);
        return categoryMapper.select (record);
    }

    /**
     * 通过ids查询分类名集合
     *
     * @param ids
     * @return
     */
    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> list = categoryMapper.selectByIdList (ids);
        List<String> names = list.stream ().map (category -> category.getName ()).collect (Collectors.toList ());
        return names;
    }

    /**
     * 根据品牌id查询品牌所属分类
     *
     * @param bid
     */
    @Override
    public List<Category> findCategoriesByBid(Long bid) {
        List<Long> cids = categoryMapper.findCidsByBid (bid);
        return categoryMapper.selectByIdList (cids);
    }

    /**
     * 添加新的商品分类
     *
     * @param category
     */
    @Override
    public void addCategory(Category category) {
        categoryMapper.insert (category);
    }
}
