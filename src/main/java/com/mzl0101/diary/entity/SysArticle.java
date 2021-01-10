package com.mzl0101.diary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_article")
public class SysArticle implements Serializable {
    private static final long serialVersionUID = -3259922056415770657L;
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;
    private String articleTitle;
    private Date articleDate;
    private String articleCategories;
    private String articleTags;
    private String articleContent;
    private Date articleCreateTime;
}
