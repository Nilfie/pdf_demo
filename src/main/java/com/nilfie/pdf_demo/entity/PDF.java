package com.nilfie.pdf_demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * @author Nilfie
 * @version 1.0
 * @date 2023/6/17 17:37
 * @description
 **/
@Data
@TableName("stuinfo")
public class PDF {
    @TableField("id")
    private int id;
    @TableField("name")
    private String name;
    @TableField("number")
    private String number;
    @TableField("gender")
    private String gender;
    @TableField("zjnumber")
    private String zjnumber;
    @TableField("xh")
    private String xh;
    @TableField("date")
    private String date;
}
