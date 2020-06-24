package com.mzl0101.diary.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * ?ռǱ?ǩ?
 * </p>
 *
 * @author mzl
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("diary_label")
public class Label implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "label_uuid", type = IdType.AUTO)
    private Integer labelUuid;

    private Integer labelValue;

    private String lablelText;


}
