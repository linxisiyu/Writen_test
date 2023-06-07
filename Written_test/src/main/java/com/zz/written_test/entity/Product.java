package com.zz.written_test.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ZhangZhe
 * @date：2023/6/7 18:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String brand;
    private String productName;
    private String series;
    private String key1;
    private String key2;
    private String key3;
    private String key4;
    private String mapping;
}
