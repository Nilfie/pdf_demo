package com.nilfie.pdf_demo.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.nilfie.pdf_demo.entity.PDF;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/19 11:31
 * @description
 **/
public class Util {
    public static Map<String, Object> getMap(PDF pdf) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", pdf.getName());
        map.put("number", pdf.getNumber());
        map.put("gender", pdf.getGender());
        map.put("zjnumber", pdf.getZjnumber());
        map.put("xh", pdf.getXh());
        map.put("date", pdf.getDate());
        return map;
    }

    public static void getPdf(HttpServletResponse response, String templatePath,
                              String photo, Map<String, Object> map,
                              String filename) {

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
            response.setHeader("Content-Disposition",
                    "attachment;fileName="
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
    }

}
