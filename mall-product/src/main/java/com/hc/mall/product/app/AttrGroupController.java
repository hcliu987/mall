package com.hc.mall.product.app;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.hc.mall.product.entity.AttrAttrgroupRelationEntity;
import com.hc.mall.product.entity.AttrEntity;
import com.hc.mall.product.service.AttrService;
import com.hc.mall.product.service.CategoryService;
import com.hc.mall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hc.mall.product.entity.AttrGroupEntity;
import com.hc.mall.product.service.AttrGroupService;
import com.hc.mall.common.utils.PageUtils;
import com.hc.mall.common.utils.R;


/**
 * 属性分组
 *
 * @author liuhaicheng
 * @email hc.liu987@gmail.com
 * @date 2024-03-11 16:54:43
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;


    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AttrService attrService;

    @PostMapping("/attr/relation")
    public R attrRelation(@RequestBody List<AttrAttrgroupRelationEntity> relationEntities){
        attrService.saveRelationBatch(relationEntities);
        return R.ok();
    }

    /**
     * 获取分类下所有分组&关联属性
     */
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrByCatelogId(@PathVariable("catelogId")Long catelogId){
        List<AttrGroupWithAttrsVo> groupWithAttrVos = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data", groupWithAttrVos);
    }


    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoretion(@PathVariable("attrgroupId")Long attrgroupId,
                          @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(params, attrgroupId);
        return  R.ok().put("page",page);

    }

    @PostMapping("/attr/relation/delete")
    public R attrRelationDelete(@RequestBody AttrAttrgroupRelationEntity[] vos ){
        attrService.deleteRelation(vos);
        return R.ok();
    }
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId){
        List<AttrEntity> data=  attrService.getRelationAttr(attrgroupId);
        return  R.ok().put("data",data);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = attrGroupService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCatelogPath(catelogId);
        return R.ok().put("attrGroup", attrGroup);
    }

    @RequestMapping("/list/{catelogId}")
    public R List(@RequestParam Map<String, Object> params, @PathVariable Long catelogId) {
        //PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
