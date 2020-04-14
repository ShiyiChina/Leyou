package com.leyou.item.mapper;


import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand (category_id,brand_id) values (#{cid},#{bid})")
    void insertCategoryAndBrand(@Param("cid")Long cid, @Param("bid") Long id);

    @Delete("delete from tb_category_brand where brand_id = #{id}")
    void deleteCategory_brand(Long id);

    @Select("select b.* from tb_brand b inner join tb_category_brand cb " +
            "on b.id = cb.brand_id where category_id = #{cid}")
    List<Brand> queryBrandsBycid(Long cid);
}
