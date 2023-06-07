package com.zz.written_test.tags;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ZhangZhe
 * @date：2023/6/7 20:44
 */
public class LowestPriceCalculator {
    public static int calculateLowestPrice(double pagePrice, String promotionInfo) {
        String[] promotions = promotionInfo.split(",");
        int minPrice = (int) pagePrice; // 默认最低到手价为页面价格

        for (String promotion : promotions) {
            String regex = "满(\\d+)减(\\d+)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(promotion);

            if (matcher.find()) {
                int fullPrice = Integer.parseInt(matcher.group(1));
                int discount = Integer.parseInt(matcher.group(2));
                int totalPrice = (int) pagePrice;

                if (totalPrice >= fullPrice) {
                    totalPrice -= discount;
                    minPrice = Math.min(minPrice, totalPrice);
                }
            }
        }

        return minPrice;
    }

    public static void main(String[] args) {
        // 单元测试
        double pagePrice1 = 2069.00;
        String promotionInfo1 = "购买至少1件时可享受优惠,满2149元减130元";
        int lowestPrice1 = calculateLowestPrice(pagePrice1, promotionInfo1);
        System.out.println("页面价格：" + pagePrice1 + "，促销信息：" + promotionInfo1 + "，最低到手价：" + lowestPrice1);

        double pagePrice2 = 1969.00;
        String promotionInfo2 = "购买1-3件时享受单件价¥1969，超出数量以结算价为准,满399减10";
        int lowestPrice2 = calculateLowestPrice(pagePrice2, promotionInfo2);
        System.out.println("页面价格：" + pagePrice2 + "，促销信息：" + promotionInfo2 + "，最低到手价：" + lowestPrice2);

        double pagePrice3 = 1899.00;
        String promotionInfo3 = "满4999减150,满3999减120,满2999减90,满1999减60,满999减30";
        int lowestPrice3 = calculateLowestPrice(pagePrice3, promotionInfo3);
        System.out.println("页面价格：" + pagePrice3 + "，促销信息：" + promotionInfo3 + "，最低到手价：" + lowestPrice3);

        double pagePrice4 = 440.00;
        String promotionInfo4 = "购买至少1件时可享受优惠";
        int lowestPrice4 = calculateLowestPrice(pagePrice4, promotionInfo4);
        System.out.println("页面价格：" + pagePrice4 + "，促销信息：" + promotionInfo4 + "，最低到手价：" + lowestPrice4);

        double pagePrice5 = 529.00;
        String promotionInfo5 = "无";
        int lowestPrice5 = calculateLowestPrice(pagePrice5, promotionInfo5);
        System.out.println("页面价格：" + pagePrice5 + "，促销信息：" + promotionInfo5 + "，最低到手价：" + lowestPrice5);
    }
}
