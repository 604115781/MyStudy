package com.zjk.item.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 张江坤
 * @description:TODO
 * @date :2020/3/16 20:14
 */
@RestController
@Api("SwaggerDemo控制器")
public class SwaggerController {

    @ApiOperation("Swagger演示")
    @GetMapping("/swaggerIndex")
    public String swaggerIndex(String msg){
        return "This is swaggerIndex!" + msg;
    }

    @ApiImplicitParam(name = "itemName",value = "商品名称",required = true,dataType = "String")
    @PostMapping("getItemInfo")
    public String getItemInfo(String itemName){
        return "商品名：" + itemName + "商品价格：" + new BigDecimal(Math.random() * 100).setScale(2,BigDecimal.ROUND_HALF_UP);
    }


}
