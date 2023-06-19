package com.nilfie.pdf_demo.controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.nilfie.pdf_demo.entity.PDF;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Set;


/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/17 17:40
 * @description
 **/
@Controller
public class PdfController {
    @Value("${templatePath}")
    private String templatePath;

    @Value("${photo}")
    private String photo;

    // 生成文件路径
    String filename = "test.pdf";

    @RequestMapping("/create")
    @ResponseBody
    public String createPDF(HttpServletResponse response) {
        PDF pdf = new PDF();
        pdf.setName("xs");
        pdf.setNumber("439868934269");
        pdf.setGender("男");
        pdf.setXh("2001041");
        pdf.setDate("2023-6-17");
        pdf.setZjnumber("543675899020030456");


        HashMap<String, Object> map = new HashMap<>();
        map.put("name", pdf.getName());
        map.put("number", pdf.getNumber());
        map.put("gender", pdf.getGender());
        map.put("zjnumber", pdf.getZjnumber());
        map.put("xh", pdf.getXh());
        map.put("date", pdf.getDate());


        PdfReader reader;
        OutputStream os = null;
        ByteArrayOutputStream baos = null;
        PdfStamper stamper;

        try {
            os = response.getOutputStream();
            // 读入pdf表单
            reader = new PdfReader(templatePath);
            baos = new ByteArrayOutputStream();
            // 根据表单生成一个新的pdf
            stamper = new PdfStamper(reader, baos);
            // 获取pdf表单
            AcroFields formTexts = stamper.getAcroFields();


            // 插入图片
            AcroFields.FieldPosition position = formTexts.getFieldPositions("photo").get(0);
            int pageNo = position.page;
            Rectangle signRect = position.position;
            float x = signRect.getLeft();
            float y = signRect.getBottom();
            // 读图片
            Image image = Image.getInstance(photo);
            // 获取操作的页面
            PdfContentByte under = stamper.getOverContent(pageNo);
            // 根据域的大小缩放图片
            image.scaleToFit(signRect.getWidth(), signRect.getHeight());
            // 添加图片
            image.setAbsolutePosition(x, y);
            under.addImage(image);


            // 设置字体(这里设置为系统字体，你也可以引入其他的字体)，不设置很可能，中文无法显示。
            BaseFont bf = BaseFont.createFont("C:/WINDOWS/Fonts/SIMSUN.ttc,1",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            formTexts.addSubstitutionFont(bf);

            // 数据查询后，值的植入 强烈推荐键值方式，其他方式很容易混
            Set<String> keySet = map.keySet();
            for (String key : keySet) {
                formTexts.setField(key, map.get(key).toString());
            }

            stamper.setFormFlattening(true); // 这个必须设
            stamper.close();

            // 创建一个pdf实例
            Document document = new Document();
            PdfCopy copy = new PdfCopy(document, os);
            document.open();
            PdfImportedPage importedPage = copy.getImportedPage(new PdfReader(baos.toByteArray()), 1);
            copy.addPage(importedPage);
            document.close(); // open和close一套

            // 强制下载
//            response.reset();
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;fileName="
                    + URLEncoder.encode(filename, "UTF-8"));

        } catch (Exception e) {
            e.printStackTrace();
        } finally { // 最后一定要关闭流
            try {
                if (baos != null) {
                    baos.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "成功，请去查看";
    }

}
