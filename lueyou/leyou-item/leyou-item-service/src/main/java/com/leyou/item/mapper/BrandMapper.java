package com.leyou.item.mapper;

import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 王俊杰
 * 品牌信息的dao接口
 */
public interface BrandMapper extends Mapper<Brand> {

    @Insert ("insert into tb_category_brand(category_id,brand_id) values(#{cid},#{bid})")
    public void addToCategoryAndBrand(@Param ("cid") Long cid,@Param ("bid") Long bid);

}
