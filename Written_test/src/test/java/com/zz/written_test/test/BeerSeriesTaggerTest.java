package com.zz.written_test.test;

import com.zz.written_test.tags.BeerSeriesTagger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZhangZhe
 * @date：2023/6/7 09:40
 */
public class BeerSeriesTaggerTest {
    @Test
    public void testTagBeerSeries() {
        BeerSeriesTagger tagger = new BeerSeriesTagger();
/**
 *  品牌：雪花
 *  商品名称：SNOW雪花纯生啤酒8度500ml*12罐匠心营造易拉罐装整箱黄啤酒500mL*12瓶
 *  系列：清爽
 *  第一关键字（根据商品名匹配）
 *  第二关键字：8°
 *  第三关键字：null
 *  第四关键字: null
 *  映射值：清爽
 */
        // 创建映射词典
        Map<String, String> mappingDictionary = new HashMap<>();
        Map<String, String> mappingDictionary1 = new HashMap<>();

        // 测试案例1：品牌为"其他"
        String productName1 = "菠萝啤整箱装24罐*320ml零酒精果啤果味汽水碳酸饮料夏日饮品";

        // 测试案例2：匹配到第一关键字
        String productName2 ="SNOW雪花纯生啤酒8°500ml*12罐匠心营造易拉罐装整箱黄啤酒500mL*12瓶";
        mappingDictionary.put(productName2, "8度");
        mappingDictionary.put("2", "8°");   //第二关键字
        mappingDictionary.put("雪花", "清爽");
        String series2 = tagger.tagBeerSeries("雪花", productName2, mappingDictionary);
        System.out.println(series2);
        Assertions.assertEquals("清爽", series2);

        //测试案例3：匹配到2,3,4关键字
        String productName3 ="迷失海岸美国进口精酿啤酒巧克力牛奶花生酱迷雾快艇幽灵浑浊IPA美国原装进口17种口味可选 355ml单瓶";
        mappingDictionary1.put(productName2, "花生巧克力牛奶世涛");
        mappingDictionary1.put("2", "花生");      //第2关键字
        mappingDictionary1.put("3", "巧克力");    //第3关键字
        mappingDictionary1.put("4", "牛奶");      //第4关键字
        mappingDictionary1.put("迷失海岸", "花生巧克力牛奶世涛");
        String series3 = tagger.tagBeerSeries("迷失海岸", productName2, mappingDictionary1);
        System.out.println(series3);
        Assertions.assertEquals("花生巧克力牛奶世涛", series3);
    }
}
