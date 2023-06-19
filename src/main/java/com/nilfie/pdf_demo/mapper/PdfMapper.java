package com.nilfie.pdf_demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nilfie.pdf_demo.entity.PDF;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/19 11:13
 * @description
 **/
@Mapper
public interface PdfMapper extends BaseMapper<PDF> {
}
