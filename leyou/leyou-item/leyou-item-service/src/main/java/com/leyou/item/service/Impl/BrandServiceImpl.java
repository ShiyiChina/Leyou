package com.leyou.item.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.pojo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.BrandService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SpuMapper spuMapper;

    /**
     * 根据查询条件分页并排序查询品牌信息
     * @param key
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @return
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer page, Integer rows, String sortBy, Boolean desc) {
        // 初始化example对象
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        // 根据name模糊查询，或者根据首字母查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name", "%" + key + "%").orEqualTo("letter", key);
        }
        // 添加分页条件
        PageHelper.startPage(page,rows);
        // 添加排序条件
        example.setOrderByClause(sortBy + " " + (desc?"desc":"asc"));
        //查找
        List<Brand> brands = brandMapper.selectByExample(example);
        // 包装成pageInfo
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        // 包装成分页结果集返回
        return new PageResult<>(pageInfo.getTotal(),pageInfo.getList());
    }

    /**
     * 新增品牌
     * @param brand
     * @param cids
     */
    @Override
    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        //新增brand
        brandMapper.insertSelective(brand);
        //新增中间表
        cids.forEach(cid->{
            brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }

    /**
     * 修改品牌
     * @param brand
     * @param cids
     */
    @Override
    public void updateBrand(Brand brand, List<Long> cids) {
        //修改品牌表
        brandMapper.updateByPrimaryKey(brand);
        //删除中间表数据
        brandMapper.deleteCategory_brand(brand.getId());
        //添加中间表
        cids.forEach(cid ->{
            brandMapper.insertCategoryAndBrand(cid,brand.getId());
        });
    }

    /**
     * 删除品牌
     * @param id
     */
    @Override
    public Boolean deleteBrand(Long id, Boolean bb) {
//        Long id =  brand.getId();
        Spu spu = new Spu();
        spu.setBrandId(id);
        List<Spu> spus = spuMapper.select(spu);
        if (CollectionUtils.isEmpty(spus)){
            bb = true;
            //删除中间表
            brandMapper.deleteCategory_brand(id);
            //删除品牌
            brandMapper.deleteByPrimaryKey(id);
        }
        return bb;
    }

    /**
     * 根据cid（分类id）查询商品
     * @param cid
     * @return
     */
    @Override
    public List<Brand> queryBrandsBycid(Long cid) {
        List<Brand> list = brandMapper.queryBrandsBycid(cid);
        return list;
    }
}
