package com.leyou.item.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.item.service.CategoryService;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 王俊杰
 * 分类控制器
 */
@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据父id查询子节点
     * @param pid
     * @return
     */
    @GetMapping("/list") //查询用get请求
    public ResponseEntity<List<Category>> queryCategoriesByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid) {
            if (pid == null || pid.longValue () < 0) {

                //HttpStatus.BAD_REQUEST == 400
                //ResponseEntity.status (HttpStatus.BAD_REQUEST).build();
                //以下用ResponseEntity封装好的方法

                //响应400,请求参数错误
                return ResponseEntity.badRequest ().build ();
            }

            List<Category> categories = categoryService.queryCategoriesByPid (pid);

            if (CollectionUtils.isEmpty (categories)) {
                //响应404,网络资源未找到
                return ResponseEntity.notFound ().build ();
            }

            //响应200,请求成功
            return ResponseEntity.ok (categories);

            //实际应该捕捉异常,响应500服务器内部错误,但服务器出错会自己响应500,所以就不需要我们自己捕捉响应了
    }

    /**
     * 根据品牌id查询品牌所属分类
     */
    @GetMapping("/bid/{bid}")
    public ResponseEntity<List<Category>> findCategoriesByBid(@PathVariable("bid") Long bid){
        List<Category> categories = categoryService.findCategoriesByBid(bid);
        if (CollectionUtils.isEmpty (categories)) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (categories);
    }

}
