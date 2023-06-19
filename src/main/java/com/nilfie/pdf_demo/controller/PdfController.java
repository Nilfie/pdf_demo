package com.nilfie.pdf_demo.controller;

import com.nilfie.pdf_demo.service.PdfService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/17 17:40
 * @description
 **/
@Controller
public class PdfController {

    @Resource
    private PdfService pdfService;

    @RequestMapping("/create")
    public void creatPdf(HttpServletResponse response){
        pdfService.createPDF(response);
    }

}
