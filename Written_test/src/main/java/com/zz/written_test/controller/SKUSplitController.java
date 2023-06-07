package com.zz.written_test.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author ZhangZhe
 * @date：2023/6/7 22:29
 */
@RestController
public class SKUSplitController {
    @PostMapping("/skuSplit")
    public byte[] skuSplit(@RequestBody List<SKU> skuList) throws IOException {
        // 创建新的工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("SKU分裂后");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("sku_id");
        headerRow.createCell(1).setCellValue("sku名称");
        headerRow.createCell(2).setCellValue("sku现价");
        headerRow.createCell(3).setCellValue("sku原价");
        headerRow.createCell(4).setCellValue("sku库存");

        // 填充数据
        int rowIndex = 1;
        for (SKU sku : skuList) {
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue(sku.getSkuId());
            row.createCell(1).setCellValue(sku.getSkuName());
            row.createCell(2).setCellValue(sku.getSkuCurrentPrice());
            row.createCell(3).setCellValue(sku.getSkuOriginalPrice());
            row.createCell(4).setCellValue(sku.getSkuStock());
        }

        // 保存分裂后的Excel文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);

        return outputStream.toByteArray();
    }

    // SKU类
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SKU {
        private String skuId;
        private String skuName;
        private double skuCurrentPrice;
        private double skuOriginalPrice;
        private int skuStock;
    }
}
