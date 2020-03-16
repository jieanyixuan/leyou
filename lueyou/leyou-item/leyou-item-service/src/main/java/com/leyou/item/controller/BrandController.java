package com.leyou.item.controller;

import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.BrandService;
import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王俊杰
 * 品牌控制器
 */
@Controller
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;
    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false)String sortBy,
            @RequestParam(value = "desc", required = false)Boolean desc
    ) {
        PageResult<Brand> brands = brandService.queryBrandsByPage (key, page, rows, sortBy, desc);
        if (CollectionUtils.isEmpty (brands.getItems ())) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (brands);
    }

    /**
     * 添加品牌
     */
    @PostMapping
    public ResponseEntity<Void> insert(Brand brand,@RequestParam("cids") List<Long> cids) {
        brandService.insertBrand (brand,cids);
        return ResponseEntity.ok ().build ();
    }

    /**
     * 修改品牌信息
     */
    @PutMapping
    public ResponseEntity<Void> update( Brand brand,@RequestParam("cids") List<Long> cids) {

       brandService.update(brand,cids);

        return ResponseEntity.ok ().build ();
    }
    /**
     * 删除品牌信息
     */
    @DeleteMapping ("/{bid}")
    public ResponseEntity<Void> delete(@PathVariable("bid")Long bid) {
        brandService.delete(bid);
        return ResponseEntity.ok ().build ();
    }
}
