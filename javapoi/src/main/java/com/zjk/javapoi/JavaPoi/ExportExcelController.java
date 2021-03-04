package com.zjk.javapoi.JavaPoi;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2021/2/24 22:43
 */
@Controller
@RequestMapping("/javapoi")
public class ExportExcelController {

    @RequestMapping("/exportExcel/{fileName}")
    public void exportExcle(@PathVariable("fileName") String fileName, HttpServletResponse response){
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "UTF-8") + ".xlsx");//03版后缀为.xls 07版后缀为.xlsx
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<Map<String,Object>> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            Map<String,Object> map = new HashMap<>();
            map.put("one","第"+(i+1)+"行的值");
            map.put("two","第"+(i+1)+"行的值");
            map.put("three","第"+(i+1)+"行的值");
            map.put("four","第"+(i+1)+"行的值");
            map.put("five","第"+(i+1)+"行的值");
            map.put("six","第"+(i+1)+"行的值");
            map.put("seven","第"+(i+1)+"行的值");
            map.put("eight","第"+(i+1)+"行的值");
            map.put("nine","第"+(i+1)+"行的值");
            map.put("ten","第"+(i+1)+"行的值");
            list.add(map);
        }
        List<String> keys = Arrays.asList("one","two","three","four","five","six","seven","eight","nine","ten");
        String[] headers = {"第1列","第2列","第3列","第4列","第5列","第6列","第7列","第8列","第9列","第10列"};
        String[] headers_1 = {"部门","姓名"};
        String[] headers_2 = {"受理量","办结量"};
        String[] headers_3 = {"退件","补件"};
        String[] headers_4 = {"承诺件","即办件","总量","承诺件","即办件","总量"};
        int[] num = {3,3};//合并单元格各占列数
        try {
            //以下调用一次执行一个
            //03版单一标题
            //ExportExcelUtil.exportExcel2003(fileName, headers, list, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss",keys);
            //07版无标题
            //ExportExcelUtil.exportExcel2007(fileName, null, list, response.getOutputStream(), "yyyy-MM-dd hh:mm:ss",keys);
            //03版单元格合并
            //ExportExcelUtil.exportExcel2003(fileName,headers_1,headers_2,headers_3,headers_4,num,list,keys,response.getOutputStream(),"yyyy-MM-dd hh:mm:ss");
            //07版单元格合并
            ExportExcelUtil.exportExcel2007(fileName,headers_1,headers_2,headers_3,headers_4,num,list,keys,response.getOutputStream(),"yyyy-MM-dd hh:mm:ss");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
