package com.zz.written_test.dao;

import com.zz.written_test.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangZhe
 * @date：2023/6/7 18:46
 */
@Mapper
public interface BatchTaggingDao {
    List<Product> batchFind(Long id, Integer start, Integer limit);

    Integer makeTags(Product Product);

    Integer updateProduct(Long id, Product Product);

}
