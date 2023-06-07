package com.zz.written_test.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;


/**
 * @author ZhangZhe
 * @date：2023/6/7 20:22
 */
public class testExtractQuantity {
    @Test
    public void testExtractQuantity() {
        String[] productNames = {
                "黑狮啤酒 啤酒黑狮白啤听装 500mL*12听罐整箱 雪花匠心营造500ml*12罐",
                "SNOW雪花纯生啤酒8度500ml*12罐匠心营造易拉罐装整箱黄啤酒 500mL*12瓶",
                "【啤酒周边纪念品】百威啤酒5款355毫升红色经典限量版空铝瓶",
                "北京 广州仓直发 坦克伯爵精酿 百香果味 10度百香果小麦白啤酒330ML×12瓶 整箱装 果香十足",
                "泰山传说官方经典正品德式进口工艺酿造世涛黑啤酒1L*12桶装包邮泡沫丰富饱满口味持久浓郁甘甜爽口营养丰富",
                "青西金琥珀拉格啤酒青岛特产精酿啤酒5L装熟啤酒节青岛特产全麦大桶装精酿拉格啤酒",
                "青岛特产精酿原浆啤酒全麦白啤蓝宝石酿酒师浑浊2升桶装促销包邮",
                "(1.35L*6桶)俄罗斯进口波罗的海远东古典啤酒 远东烈性啤酒 大麦黄啤整箱啤酒 远东古典1.35升*6桶(口感适中)",
                "自由落体哈密瓜水果艾尔国产精酿果啤微醺酒果味酒女士低度酒饮料"
        };

        int[] expectedQuantities = {500, 500, 355, 330, 1000, 5000, 2000, 1350, 0};

        for (int i = 0; i < productNames.length; i++) {
        }
    }
}

