package com.neo.common.uilts;

import com.easyjob.exception.BusinessException;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description excel工具类
 * @Author 程序员老罗
 * @Date 2023/9/23 21:34
 * @ClassName
 * @MethodName
 * @Params
 */
public class ExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    public static List<List<String>> readExcel(MultipartFile file, String[] title, Integer startRowIndex) {
        InputStream is = null;
        Integer rowIndex = 0;
        try {
            is = file.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            List<List<String>> listData = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                throw new BusinessException("excel文件解析失败");
            }
            for (int rowNumIndex = 0; rowNumIndex <= sheet.getLastRowNum(); rowNumIndex++) {
                rowIndex = rowNumIndex;
                if (rowNumIndex < startRowIndex) {
                    continue;
                }
                List<String> rowData = new ArrayList<>();
                Row row = sheet.getRow(rowNumIndex);
                if (row == null) {
                    continue;
                }
                int maxColCount = title.length;
                Boolean allEmpty = true;
                if (row.getLastCellNum() < maxColCount) {
                    throw new BusinessException("请按照模板文件上传数据");
                }
                for (int colIndex = 0; colIndex < maxColCount; colIndex++) {
                    Cell cell = row.getCell(colIndex);
                    String cellValue = getCellValue(cell);
                    rowData.add(cellValue);
                    if (!StringTools.isEmpty(cellValue)) {
                        allEmpty = false;
                    }
                }
                if (rowNumIndex == startRowIndex) {
                    String dataTitle = rowData.stream().collect(Collectors.joining("_"));
                    String titleStr = Arrays.asList(title).stream().collect(Collectors.joining("_"));
                    if (!dataTitle.equalsIgnoreCase(titleStr)) {
                        throw new BusinessException("请按照模板文件上传数据");
                    }
                }
                if (allEmpty) {
                    continue;
                }
                if (rowNumIndex > startRowIndex) {
                    listData.add(rowData);
                }
            }
            return listData;
        } catch (BusinessException e) {
            logger.error("文件解析错误,第：{}行", rowIndex + 1, e);
            throw e;
        } catch (Exception e) {
            logger.error("文件解析错误,第：{}行", rowIndex + 1, e);
            throw new BusinessException("文件第" + (rowIndex + 1) + "行解析错误");
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("流关闭失败", e);
                }
            }
        }
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            DecimalFormat df = new DecimalFormat("#.##");
            return df.format(cell.getNumericCellValue());
        } else {
            String value = cell.getStringCellValue();
            return StringTools.isEmpty(value) ? "" : value.trim();
        }
    }
}
