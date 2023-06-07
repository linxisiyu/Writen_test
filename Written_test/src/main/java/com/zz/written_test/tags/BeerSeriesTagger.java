package com.zz.written_test.tags;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author ZhangZhe
 * @date：2023/6/7 09:32
 */
public class BeerSeriesTagger {
    /**
     *
     * @param brand 品牌
     * @param productName   商品名称
     * @param mappingDictionary 映射字典
     * @return  系列标签
     */


    public String tagBeerSeries(String brand, String productName, Map<String, String> mappingDictionary) {
        //如果品牌值是其他，那么系列就是其他
        if (StringUtils.hasText(brand) && brand.equalsIgnoreCase("其他")) {
            return "其他";
        }

        // 从商品名称匹配第一关键字
        for (Map.Entry<String, String> entry : mappingDictionary.entrySet()) {
            if (productName.contains(entry.getKey())) {
                return mappingDictionary.get(brand);
            }
        }

        // 匹配第二、第三、第四关键字 要求&
        boolean keyword2Matched = false;
        boolean keyword3Matched = false;
        boolean keyword4Matched = false;

        //关键字映射：2 -- > 花生， 3 -- > 巧克力, 4 --> 牛奶
        for (Map.Entry<String, String> entry : mappingDictionary.entrySet()) {
            String keyword = entry.getKey();
            if (productName.contains(keyword)) {
                if (!keyword2Matched && keyword.equals("2")) {
                    keyword2Matched = true;
                } else if (!keyword3Matched && keyword.equals("3")) {
                    keyword3Matched = true;
                } else if (!keyword4Matched && keyword.equals("4")) {
                    keyword4Matched = true;
                }

                if (keyword2Matched && keyword3Matched && keyword4Matched) {
                    return mappingDictionary.get(brand);
                }
            }
        }

        // 最终品牌有值，但是系列标签未打上，返回"品牌+其他"
        return brand + "其他";
    }
}
