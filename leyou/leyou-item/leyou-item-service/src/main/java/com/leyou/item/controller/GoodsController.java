package com.leyou.item.controller;


import com.leyou.common.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 根据查询条件分页并排序查询商品信息
     * @param key
     * @param saleable
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false)String key,
            @RequestParam(value = "saleable", required = false)Boolean saleable,
            @RequestParam(value = "page", defaultValue = "1")Integer page,
            @RequestParam(value = "rows", defaultValue = "5")Integer rows
    ){
        PageResult<SpuBo> pageResult = goodsService.querySpuBoByPage(key,saleable,page,rows);
        if (CollectionUtils.isEmpty(pageResult.getItems())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 添加商品spu
     * @param spuBo
     * @return
     */
    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody SpuBo spuBo){
        goodsService.saveGoods(spuBo);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据spuid查询商品spuDetail（修改商品时回显）
     * @param spuId
     * @return
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable Long spuId){
        SpuDetail spuDetail = goodsService.querySpuDetailBySpuId(spuId);
        return ResponseEntity.ok(spuDetail);
    }

    /**
     * 根据spuid查询商品sku（修改商品时回显）
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkusBySpuId(@RequestParam("id") Long id){
        List<Sku> skus = goodsService.querySkusBySpuId(id);
        if (CollectionUtils.isEmpty(skus)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(skus);
    }

    /**
     * 修改商品spu
     * @param spu
     */
    @PutMapping("goods")
    public ResponseEntity<Void> updateGoods(@RequestBody SpuBo spu){
        goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 删除商品spu
     * @param spuId
     * @return
     */
    @DeleteMapping("goods/delete/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable("id") Long spuId){
        goodsService.deleteGoods(spuId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("goods/saleable")
    public ResponseEntity<Void> updataSaleable(@RequestBody Spu spu){
        goodsService.updataSaleable(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<Spu> querySpuById(@PathVariable("id") Long id){
        Spu spu = goodsService.querySpuById(id);
        return ResponseEntity.ok(spu);
    }



}
