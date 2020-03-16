package com.leyou.item.mapper;

import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.base.select.SelectByPrimaryKeyMapper;

import java.util.List;

/**
 * @author 王俊杰
 * 商品分类的dao接口
 */
public interface CategoryMapper extends Mapper<Category>, SelectByIdListMapper<Category,Long> {

    /**
     * 通过品牌id查询品牌所属分类的id集合
     * @return
     */
    @Select ("select category_id from tb_category_brand where brand_id=#{bid}")
    List<Long> findCidsByBid(Long bid);
}
