package com.nilfie.pdf_demo.service;

import com.nilfie.pdf_demo.entity.PDF;
import com.nilfie.pdf_demo.mapper.PdfMapper;
import com.nilfie.pdf_demo.util.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/19 10:41
 * @description
 **/

@Service
public class PdfService {

    @Resource
    private PdfMapper pdfMapper;

    @Value("${templatePath}")
    private String templatePath;

    @Value("${photo}")
    private String photo;

    // 生成文件路径
    String filename = "test.pdf";

    public void createPDF(HttpServletResponse response) {

        PDF pdf1 = pdfMapper.selectById(1);

        Map<String, Object> map = Util.getMap(pdf1);

        Util.getPdf(response, templatePath, photo, map, filename);
    }


}
