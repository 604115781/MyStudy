package com.zjk.javapoi.JavaPoi;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2021/2/24 21:51
 */
public class ExportExcelUtil {
    // 2007 版本以上 最大支持1048576行
    public  final static String  EXCEl_FILE_2007 = "2007";
    // 2003 版本 最大支持65536 行
    public  final static String  EXCEL_FILE_2003 = "2003";

    /**
     *@Description:03版 .xls
     *@Param: title excel名称，也可当sheet页名称使用
     *@Param: headers_1 未合并的第一部分标题
     *@Param: headers_2 合并单元格的大标题
     *@Param: headers_3 不合并的第二部分列名
     *@Param: headers_4 合并单元格的小标题
     *@Param: nums 合并列中每大列占几小列，例如 [3,3]第1,2大列各占3小列
     *@Param: keys 数据集中Map的字段名称
     *@Param: dataset 数据集，此处必须为Map
     *@Param: out 输出流
     *@Param: pattern 如果表格中有时间类型按照这种格式展示
     *@return:void
     *@Author: 张江坤
     *@date: 2021/2/23 13:48
     */
    public static void exportExcel2003(String title, String[] headers_1,String[] headers_2,String[] headers_3, String[] headers_4, int[] nums, List<Map<String,Object>> dataset, List<String> keys, OutputStream out, String pattern) {

        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体（标题跟正文分开）
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        HSSFRow row = sheet.createRow(0);
        //设置未合并的第一部分列的格式
        for(int i=0; i<headers_1.length; i++){
            //占2行1列(因为有合并行，合并列)
            CellRangeAddress cra=new CellRangeAddress(0, 1, i, i);
            sheet.addMergedRegion(cra);
        }
        //为未合并行的第一部分列写入标题
        for(int i=0; i<headers_1.length; i++){
            final Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_1[i]);
        }
        int sum1 = 0;
        int sum2 = 0;
        //合并列的大标题
        for(int i=0; i<headers_2.length; i++){//受理数，办结数
            sum1 += nums[i];
            //占1行，从header_2长度后的那列开始数，画够合并行的宽度
            CellRangeAddress cra=new CellRangeAddress(0, 0, headers_1.length+sum2, headers_1.length+sum1-1);
            sheet.addMergedRegion(cra);
            sum2 += nums[i];
        }
        int sum = 0;
        for(int i=0; i<headers_2.length; i++){
            //给合并列中的大列写入标题,未合并列第一部分宽度 + 合并列各列中小列的宽度
            final Cell cell = row.createCell(headers_1.length+sum);
            cell.setCellStyle(style);
            cell.setCellValue(headers_2[i]);
            sum += nums[i];
        }

        //未合并列的第二部分列设置格式，占2行1列
        for(int i=0; i<headers_3.length; i++){
            CellRangeAddress cra=new CellRangeAddress(0, 1, headers_1.length+sum2+i, headers_2.length+sum2+i);
            sheet.addMergedRegion(cra);
        }
        //给未合并列的第二部分列写入标题
        for(int i=0; i<headers_3.length; i++){
            final Cell cell = row.createCell(headers_1.length+sum2+i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_3[i]);
        }
        //重新创建一行，写入合并列中的小列
        row = sheet.createRow(1);
        for(int i=0; i<headers_4.length; i++){
            final Cell cell = row.createCell(headers_1.length+i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_4[i]);
        }

        // 遍历集合数据，产生数据行
        Iterator<Map<String,Object>> it = dataset.iterator();
        int index = 1;
        Map<String,Object> t;
        HSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        HSSFCell cell;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            t = (Map<String,Object>) it.next();
            for (int i = 0; i < keys.size(); i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    value = t.get(keys.get(i));
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new HSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *@Description:07版 .xlsx
     *@Param: title excel名称，也可当sheet页名称使用
     *@Param: headers_1 未合并的第一部分标题
     *@Param: headers_2 合并单元格的大标题
     *@Param: headers_3 不合并的第二部分列名
     *@Param: headers_4 合并单元格的小标题
     *@Param: nums 合并列中每大列占几小列，例如 [3,3]第1,2大列各占3小列
     *@Param: keys 数据集中Map的字段名称
     *@Param: dataset 数据集，此处必须为Map
     *@Param: out 输出流
     *@Param: pattern 如果表格中有时间类型按照这种格式展示
     *@return:void
     *@Author: 张江坤
     *@date: 2021/2/23 13:48
     */
    public static void exportExcel2007(String title, String[] headers_1,String[] headers_2,String[] headers_3, String[] headers_4, int[] nums, List<Map<String,Object>> dataset, List<String> keys, OutputStream out, String pattern) {

        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(new XSSFColor(Color.gray));
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(new XSSFColor(Color.BLACK));
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(new XSSFColor(Color.WHITE));
        style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体（标题跟正文分开）
        XSSFFont font2 = workbook.createFont();
        font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        XSSFRow row = sheet.createRow(0);
        //设置未合并的第一部分列的格式
        for(int i=0; i<headers_1.length; i++){
            //占2行1列(因为有合并行，合并列)
            CellRangeAddress cra=new CellRangeAddress(0, 1, i, i);
            sheet.addMergedRegion(cra);
        }
        //为未合并行的第一部分列写入标题
        for(int i=0; i<headers_1.length; i++){
            final Cell cell = row.createCell(i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_1[i]);
        }
        int sum1 = 0;
        int sum2 = 0;
        //合并列的大标题
        for(int i=0; i<headers_2.length; i++){//受理数，办结数
            sum1 += nums[i];
            //占1行，从header_2长度后的那列开始数，画够合并行的宽度
            CellRangeAddress cra=new CellRangeAddress(0, 0, headers_1.length+sum2, headers_1.length+sum1-1);
            sheet.addMergedRegion(cra);
            sum2 += nums[i];
        }
        int sum = 0;
        for(int i=0; i<headers_2.length; i++){
            //给合并列中的大列写入标题,未合并列第一部分宽度 + 合并列各列中小列的宽度
            final Cell cell = row.createCell(headers_1.length+sum);
            cell.setCellStyle(style);
            cell.setCellValue(headers_2[i]);
            sum += nums[i];
        }

        //未合并列的第二部分列设置格式，占2行1列
        for(int i=0; i<headers_3.length; i++){
            CellRangeAddress cra=new CellRangeAddress(0, 1, headers_1.length+sum2+i, headers_2.length+sum2+i);
            sheet.addMergedRegion(cra);
        }
        //给未合并列的第二部分列写入标题
        for(int i=0; i<headers_3.length; i++){
            final Cell cell = row.createCell(headers_1.length+sum2+i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_3[i]);
        }
        //重新创建一行，写入合并列中的小列
        row = sheet.createRow(1);
        for(int i=0; i<headers_4.length; i++){
            final Cell cell = row.createCell(headers_1.length+i);
            cell.setCellStyle(style);
            cell.setCellValue(headers_4[i]);
        }

        // 遍历集合数据，产生数据行
        Iterator<Map<String,Object>> it = dataset.iterator();
        int index = 1;
        Map<String,Object> t;
        XSSFRichTextString richString;
        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
        Matcher matcher;
        XSSFCell cell;
        Object value;
        String textValue;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            t = (Map<String,Object>) it.next();
            for (int i = 0; i < keys.size(); i++) {
                cell = row.createCell(i);
                cell.setCellStyle(style2);
                try {
                    value = t.get(keys.get(i));
                    // 判断值的类型后进行强制类型转换
                    textValue = null;
                    if (value instanceof Integer) {
                        cell.setCellValue((Integer) value);
                    } else if (value instanceof Float) {
                        textValue = String.valueOf((Float) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Double) {
                        textValue = String.valueOf((Double) value);
                        cell.setCellValue(textValue);
                    } else if (value instanceof Long) {
                        cell.setCellValue((Long) value);
                    }
                    if (value instanceof Boolean) {
                        textValue = "是";
                        if (!(Boolean) value) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        textValue = sdf.format((Date) value);
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        }
                    }
                    if (textValue != null) {
                        matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            richString = new XSSFRichTextString(textValue);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *@Description:03版后缀.xls，标题可有可无，如无标题则headers传null即可
     *@Param: title excel名称，也可当sheet页名称使用
     *@Param: headers 标题
     *@Param: dataset 数据集
     *@Param: out 输出流
     *@Param: dataset 数据集，此处必须为Map
     *@Param: pattern 如果表格中有时间类型按照这种格式展示
     *@Param: keys 数据集中Map的字段名称
     *@return:void
     *@Author: 张江坤
     *@date: 2021/2/24 21:54
     */
    public static void exportExcel2003(String title, String[] headers, List<Map<String,Object>> dataset, OutputStream out, String pattern,List<String> keys) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(HSSFColor.WHITE.index);
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        HSSFRow row;
        //有无标题，取决于标题是否在数据集中
        if(null != headers){
            // 产生表格标题行
            row = sheet.createRow(0);
            HSSFCell cellHeader;
            for (int i = 0;i < headers.length; i++) {
                cellHeader = row.createCell(i);
                cellHeader.setCellStyle(style);
                cellHeader.setCellValue(new HSSFRichTextString(headers[i]));
            }
        }
        if(!dataset.isEmpty()) {
            // 遍历集合数据，产生数据行
            Iterator<Map<String,Object>> it = dataset.iterator();
            //如果没有标题，则从第0行开始写数据
            int index = null==headers ? -1 : 0;
            Map<String,Object> t;
            Field field;
            HSSFRichTextString richString;
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher;
            HSSFCell cell;
            Object value;
            String textValue;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                t = (Map<String,Object>) it.next();

                for (int i = 0; i < keys.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    try {
                        value = t.get(keys.get(i));
                        // 判断值的类型后进行强制类型转换
                        textValue = null;
                        if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof Float) {
                            textValue = String.valueOf((Float) value);
                            cell.setCellValue(textValue);
                        } else if (value instanceof Double) {
                            textValue = String.valueOf((Double) value);
                            cell.setCellValue(textValue);
                        } else if (value instanceof Long) {
                            cell.setCellValue((Long) value);
                        }
                        if (value instanceof Boolean) {
                            textValue = "是";
                            if (!(Boolean) value) {
                                textValue = "否";
                            }
                        } else if (value instanceof Date) {
                            textValue = sdf.format((Date) value);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            if (value != null) {
                                textValue = value.toString();
                            }
                        }
                        if (textValue != null) {
                            matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                richString = new HSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } finally {
                        // 清理资源
                    }
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *@Description:07版后缀.xlsx，标题可有可无，如无标题则headers传null即可
     *@Param: title excel名称，也可当sheet页名称使用
     *@Param: headers 标题
     *@Param: dataset 数据集
     *@Param: out 输出流
     *@Param: dataset 数据集，此处必须为Map
     *@Param: pattern 如果表格中有时间类型按照这种格式展示
     *@Param: keys 数据集中Map的字段名称
     *@return:void
     *@Author: 张江坤
     *@date: 2021/2/24 21:54
     */
    public static void exportExcel2007(String title, String[] headers, List<Map<String,Object>> dataset, OutputStream out, String pattern,List<String> keys) {
        // 声明一个工作薄
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth(20);
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(new XSSFColor(Color.gray));
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        XSSFFont font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setColor(new XSSFColor(Color.BLACK));
        font.setFontHeightInPoints((short) 11);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(new XSSFColor(Color.WHITE));
        style2.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        XSSFFont font2 = workbook.createFont();
        font2.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        XSSFRow row;
        //有无标题，取决于标题是否在数据集中
        if(null != headers){
            // 产生表格标题行
            row = sheet.createRow(0);
            XSSFCell cellHeader;
            for (int i = 0;i < headers.length; i++) {
                cellHeader = row.createCell(i);
                cellHeader.setCellStyle(style);
                cellHeader.setCellValue(new XSSFRichTextString(headers[i]));
            }
        }
        if(!dataset.isEmpty()) {
            // 遍历集合数据，产生数据行
            Iterator<Map<String,Object>> it = dataset.iterator();
            //如果没有标题，则从第0行开始写数据
            int index = null==headers ? -1 : 0;
            Map<String,Object> t;
            Field field;
            XSSFRichTextString richString;
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher;
            XSSFCell cell;
            Object value;
            String textValue;
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                t = (Map<String,Object>) it.next();

                for (int i = 0; i < keys.size(); i++) {
                    cell = row.createCell(i);
                    cell.setCellStyle(style2);
                    try {
                        value = t.get(keys.get(i));
                        // 判断值的类型后进行强制类型转换
                        textValue = null;
                        if (value instanceof Integer) {
                            cell.setCellValue((Integer) value);
                        } else if (value instanceof Float) {
                            textValue = String.valueOf((Float) value);
                            cell.setCellValue(textValue);
                        } else if (value instanceof Double) {
                            textValue = String.valueOf((Double) value);
                            cell.setCellValue(textValue);
                        } else if (value instanceof Long) {
                            cell.setCellValue((Long) value);
                        }
                        if (value instanceof Boolean) {
                            textValue = "是";
                            if (!(Boolean) value) {
                                textValue = "否";
                            }
                        } else if (value instanceof Date) {
                            textValue = sdf.format((Date) value);
                        } else {
                            // 其它数据类型都当作字符串简单处理
                            if (value != null) {
                                textValue = value.toString();
                            }
                        }
                        if (textValue != null) {
                            matcher = p.matcher(textValue);
                            if (matcher.matches()) {
                                // 是数字当作double处理
                                cell.setCellValue(Double.parseDouble(textValue));
                            } else {
                                richString = new XSSFRichTextString(textValue);
                                cell.setCellValue(richString);
                            }
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } finally {
                        // 清理资源
                    }
                }
            }
        }
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
