package com.nilfie.pdf_demo;

import com.nilfie.pdf_demo.entity.PDF;
import com.nilfie.pdf_demo.mapper.PdfMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class PdfDemoApplicationTests {

    @Resource
    private PdfMapper mapper;
    @Test
    void contextLoads() {
//        List<PDF> pdfs = mapper.selectList(null);
//        for (int i = 0; i < pdfs.size(); i++) {
//            System.out.println(pdfs.get(i));
//        }

        List<PDF> pdfs1 = mapper.selectByMap(null);
        for (int i = 0; i < pdfs1.size(); i++) {
            System.out.println(pdfs1.get(i));
        }
    }

}
