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
@TableName("sys_storage")
public class SysStorage implements Serializable {
    private static final long serialVersionUID = -5898386438347812223L;
    @TableId(value = "storage_id", type = IdType.AUTO)
    private Long storageId;
    private String storageName;
    private String storageSuffix;
    private String storagePath;
    private String storageType;
    private String storageSize;
    private Date storageCreateTime;
}
