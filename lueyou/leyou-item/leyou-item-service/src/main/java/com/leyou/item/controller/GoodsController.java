package com.leyou.item.controller;

import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author 王俊杰
 * 商品控制器
 */
@Controller
@RequestMapping()
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
    @GetMapping("/spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key",required = false) String key,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows
    ) {
        PageResult<SpuBo> result = goodsService.querySpuBoByPage(key,saleable,page,rows);
        if (result == null || CollectionUtils.isEmpty (result.getItems ())) {
            return ResponseEntity.notFound ().build ();
        }

        return ResponseEntity.ok (result);
    }

    /**
     * 添加新商品
     * @param spuBo
     * @return
     * @throws URISyntaxException
     */
    @PostMapping("/goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo) throws URISyntaxException {
        this.goodsService.saveGoods(spuBo);
        return ResponseEntity.created (new URI ("")).build ();
    }
}
