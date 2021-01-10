package com.mzl0101.diary.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mzl
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_node")
public class SysNode implements Serializable {
    private static final long serialVersionUID = -6697850896827777852L;
    @TableId(value = "node_id", type = IdType.AUTO)
    private Long nodeId;
    private String nodeName;
    private Long nodeParentId;
    @TableField(exist = false)
    private List<SysNode> nodes = new ArrayList<SysNode>();

}
