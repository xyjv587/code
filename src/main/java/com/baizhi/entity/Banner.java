package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Banner {
    @ExcelIgnore
    private String id;
    @Excel(name = "标题")
    private String title;
    @Excel(name = "状态",replace = {"展示_y","不展示_n"})
    private String status;
    @Excel(name = "描述")
    private String desc;
    @Excel(name = "创建时间",format = "yyyy-MM-dd",width = 12.0)
    private Date create_date;
    @Excel(name = "图片",type = 2)
    private String img_path;
}
