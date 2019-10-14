package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    private String id;
    private String title;
    private String content;
    private Date createDate;
    private String author;
    private String status;
}
