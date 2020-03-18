package com.leyou.item.controller;

import com.leyou.bo.SpuBo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.service.GoodsService;
import com.leyou.pojo.Sku;
import com.leyou.pojo.SpuDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * @author 王俊杰
 * 商品控制器
 */
@Controller
@RequestMapping
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

    /**
     * 更新商品信息
     * @param spuBo
     * @return
     */
    @PutMapping("/goods")
    public ResponseEntity<SpuBo> updateGoods(@RequestBody SpuBo spuBo) {
        this.goodsService.updateGoods(spuBo);
        return ResponseEntity.noContent ().build ();
    }


    /**
     * 根据spuId查询SpuDetail
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("spuId") Long spuId) {
        SpuDetail spuDetail = goodsService.querySpuDetailBySpuId(spuId);
        if (spuDetail == null) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (spuDetail);
    }


    /**
     * 通过spuId查询该商品的sku集合
     * @param spuId
     * @return
     */
    @GetMapping("/sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id")Long spuId) {
        List<Sku> skus = goodsService.querySkusBySpuId(spuId);
        if (CollectionUtils.isEmpty (skus)) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (skus);
    }
}
