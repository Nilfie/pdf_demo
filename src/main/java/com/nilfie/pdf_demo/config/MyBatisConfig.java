package com.nilfie.pdf_demo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/19 11:13
 * @description
 **/

@Configuration
@MapperScan("com/nilfie/pdf_demo/mapper")
public class MyBatisConfig {
}
