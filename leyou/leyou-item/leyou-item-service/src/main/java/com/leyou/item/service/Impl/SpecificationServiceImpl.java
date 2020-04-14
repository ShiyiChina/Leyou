package com.leyou.item.service.Impl;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * 根据分类id查询分组
     * @param cid
     * @return
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        return specGroupMapper.select(specGroup);
    }

    /**
     * 新增分组
     * @param specGroup
     * @return
     */
    @Override
    public void saveGroup(SpecGroup specGroup) {
        specGroupMapper.insert(specGroup);
    }

    /**
     * 修改分组名称
     * @param specGroup
     * @return
     */
    @Override
    public void updateGroup(SpecGroup specGroup) {
        specGroupMapper.updateByPrimaryKey(specGroup);
    }

    /**
     * 删除分组
     * @param id
     * @return
     */
    @Override
    public void deleteGroup(Long id) {
        specGroupMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据分组id查询该分组下的规格参数
     * @param cid
     * @param gid
     * @param generic
     * @param searching
     * @return
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setGeneric(generic);
        specParam.setSearching(searching);
        return specParamMapper.select(specParam);
    }

    /**
     * 新增参数（商品类型的参数）（SpecParam）
     * @param specParam
     * @return
     */
    @Override
    public void saveParam(SpecParam specParam) {
        specParamMapper.insert(specParam);
    }

    /**
     * 修改规格参数
     * @param specParam
     * @return
     */
    @Override
    public void updateParam(SpecParam specParam) {
        specParamMapper.updateByPrimaryKey(specParam);
    }

    /**
     * 根据id删除商品类型的参数
     * @param id
     * @return
     */
    @Override
    public void deleteParam(Long id) {
        specParamMapper.deleteByPrimaryKey(id);
    }

}
