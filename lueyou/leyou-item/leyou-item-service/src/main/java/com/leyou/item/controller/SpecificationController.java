package com.leyou.item.controller;

import com.leyou.item.service.SpecificationService;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 王俊杰
 * 规格参数控制器
 *
 */
@Controller
@RequestMapping("/spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    /**
     * 通过分类id查询详情分组
     * @param cid
     * @return
     */
    @GetMapping("/groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid")Long cid) {

        List<SpecGroup> groups = specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty (groups)) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (groups);
    }

    /**
     * 通过分组id查询参数
     * @param gid
     * @return
     */
    @GetMapping("/params")
    public ResponseEntity<List<SpecParam>> queryParamsByGid(@RequestParam("gid")Long gid) {
        List<SpecParam> params = specificationService.queryParamsByGid (gid);
        if (CollectionUtils.isEmpty (params)) {
            return ResponseEntity.notFound ().build ();
        }
        return ResponseEntity.ok (params);
    }


    /**
     * 添加参数分组
     * @return
     */
    @PostMapping("/group")
    public ResponseEntity<Void> addGroup(@RequestBody SpecGroup specGroup) {
        //System.out.println (specGroup);
        specificationService.addGroup(specGroup);
        return ResponseEntity.ok ().build ();
    }

    /**
     * 修改分组信息
     */
    @PutMapping("/group")
    public ResponseEntity<Void> updateGroup(@RequestBody SpecGroup specGroup) {
        specificationService.updateGroup(specGroup);
        return ResponseEntity.ok ().build ();
    }

    /**
     * 删除分组信息
     */
    @DeleteMapping("/group/{gid}")
    public ResponseEntity<Void> deleteGroup(@PathVariable("gid")Long gid) {
        specificationService.deleteGroupByGid (gid);
        return ResponseEntity.ok ().build ();
    }

    /**
     * 添加分组参数
     */
    @PostMapping("/param")
    public ResponseEntity<Void> addParam(@RequestBody SpecParam specParam) {
        specificationService.addParam(specParam);
        return ResponseEntity.ok ().build ();
    }

    /**
     * 更改参数信息
     */
    @PutMapping("/param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam) {
        specificationService.updateParam(specParam);
        return ResponseEntity.ok().build ();
    }

    /**
     * 删除参数信息
     */
    @DeleteMapping("/param/{pid}")
    public ResponseEntity<Void> deleteParam(@PathVariable("pid") Long pid) {
        specificationService.deleteParamByPid(pid);
        return ResponseEntity.ok ().build ();
    }
}
