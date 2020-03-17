package com.leyou.item.mapper;

import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author 王俊杰
 * 品牌信息的dao接口
 */
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 插入品牌分类关联信息
     * @param cid
     * @param bid
     */
    @Insert ("insert into tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    public void addToCategoryAndBrand(@Param ("cid") Long cid,@Param ("bid") Long bid);

    /**
     * 通过品牌id删除品牌分类关联信息
     * @param bid
     */
    @Delete ("delete from tb_category_brand where brand_id=#{bid}")
    void deleteCategoryAndBrandByBid(Long bid);

    /**
     * 通过分类id查询所属品牌信息
     * @param cid
     * @return
     */
    @Select ("select * from tb_brand where id in (select brand_id from tb_category_brand where category_id=#{cid})")
    List<Brand> findBrandsByCid(Long cid);
}
