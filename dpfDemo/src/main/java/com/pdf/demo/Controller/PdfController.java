package com.pdf.demo.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
public class PdfController {
    @RequestMapping("/print")
    private String pdfPrint(@RequestParam Map<String,String> params, HttpServletResponse response){
        return  "hello";
    }
}
