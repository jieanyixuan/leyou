package com.leyou.item.controller;

import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 王俊杰
 * 商品控制器
 */
@Controller
@RequestMapping("/spu")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;


    /**
     * 根据条件分页查询spu
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ) {
        System.out.println ("是否上架"+saleable);
        PageResult<SpuBo> result = goodsService.querySpuBoByPage(key,saleable,page,rows);
        if (result == null || CollectionUtils.isEmpty (result.getItems ())) {
            return ResponseEntity.notFound ().build ();
        }

        return ResponseEntity.ok (result);
    }
}
