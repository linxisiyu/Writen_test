package com.zz.written_test.service;

import com.zz.written_test.dao.BatchTaggingDao;
import com.zz.written_test.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ZhangZhe
 * @date：2023/6/7 16:22
 */
@Service
public class BatchTaggingService {
    @Autowired
    private BatchTaggingDao batchTaggingDao;

    public List<Product> batchTagAndSaveSeries(Long id, Integer start, Integer limit) {
        return batchTaggingDao.batchFind(id, start, limit);
    }

    private void createBeerDataList(Product product) {
        batchTaggingDao.makeTags(product);
    }

    private void tagAndSaveSeries(Long id, Product product) {
        batchTaggingDao.updateProduct(id, product);
    }
}

