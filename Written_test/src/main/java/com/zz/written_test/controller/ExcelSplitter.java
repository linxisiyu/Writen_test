package com.zz.written_test.controller;

import com.mysql.cj.result.Row;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author ZhangZhe
 * @date：2023/6/7 21:09
 */
@RestController
public class ExcelSplitter {
    @PostMapping("/splitExcel")
    public ResponseEntity<byte[]> splitExcel(@RequestBody ExcelSplitRequest request) throws IOException {
        File tempDir = createTempDirectory(); // 创建临时目录

        // 加载Excel文件
        Workbook workbook = WorkbookFactory.create(request.getExcelFile());
        Sheet sheet = workbook.getSheetAt(0); // 假设拆分的数据在第一个Sheet中

        // 获取指定列的数据
        int columnToSplit = getColumnIndex(sheet, request.getColumnName());
        List<List<String>> splitData = getSplitData(sheet, columnToSplit);

        // 拆分Excel并保存为多个文件
        List<File> splitFiles = saveSplitFiles(tempDir, workbook, sheet, columnToSplit, splitData);

        // 创建ZIP文件
        File zipFile = createZipFile(tempDir, splitFiles);

        // 读取ZIP文件内容并返回给客户端
        byte[] zipData = readZipFile(zipFile);

        // 删除临时文件
        cleanupTempFiles(tempDir);

        // 设置HTTP响应头，告诉客户端返回的是ZIP文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", zipFile.getName());

        // 返回ZIP文件数据
        return new ResponseEntity<>(zipData, headers, HttpStatus.OK);
    }

    private File createTempDirectory() throws IOException {
        return Files.createTempDirectory("excel-split").toFile();
    }

    private int getColumnIndex(Sheet sheet, String columnName) {
        Row headerRow = sheet.getRow(0);
        for (int i = 0; i < headerRow.getLastCellNum(); i++) {
            Cell cell = headerRow.getCell(i);
            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        return -1;
    }

    private List<List<String>> getSplitData(Sheet sheet, int columnIndex) {
        List<List<String>> splitData = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(columnIndex);
            String cellValue = cell.getStringCellValue();
            if (cellValue != null && !cellValue.isEmpty()) {
                List<String> rowData = new ArrayList<>();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    rowData.add(row.getCell(j).getStringCellValue());
                }
                splitData.add(rowData);
            }
        }
        return splitData;
    }

    private List<File> saveSplitFiles(File tempDir, Workbook workbook, Sheet sheet, int columnIndex,
                                      List<List<String>> splitData) throws IOException {
        List<File> splitFiles = new ArrayList<>();
        for (List<String> rowData : splitData) {
            Workbook newWorkbook = new XSSFWorkbook();
            Sheet newSheet = newWorkbook.createSheet(sheet.getSheetName());
            int rowIndex = 0;
            for (List<String> splitRowData : splitData) {
                Row newRow = newSheet.createRow(rowIndex++);
                for (int i = 0; i < splitRowData.size(); i++) {
                    Cell newCell = newRow.createCell(i, CellType.STRING);
                    newCell.setCellValue(splitRowData.get(i));
                }
            }
            File splitFile = new File(tempDir, rowData.get(columnIndex) + ".xlsx");
            try (FileOutputStream fos = new FileOutputStream(splitFile)) {
                newWorkbook.write(fos);
            }
            splitFiles.add(splitFile);
        }
        return splitFiles;
    }

    private File createZipFile(File tempDir, List<File> splitFiles) throws IOException {
        File zipFile = new File(tempDir, "splitFiles.zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File splitFile : splitFiles) {
                ZipEntry zipEntry = new ZipEntry(splitFile.getName());
                zos.putNextEntry(zipEntry);
                try (FileInputStream fis = new FileInputStream(splitFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        zos.write(buffer, 0, bytesRead);
                    }
                }
                zos.closeEntry();
            }
        }
        return zipFile;
    }

    private byte[] readZipFile(File zipFile) throws IOException {
        try (FileInputStream fis = new FileInputStream(zipFile)) {
            return fis.readAllBytes();
        }
    }

    private void cleanupTempFiles(File tempDir) {
        File[] files = tempDir.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        tempDir.delete();
    }
}
