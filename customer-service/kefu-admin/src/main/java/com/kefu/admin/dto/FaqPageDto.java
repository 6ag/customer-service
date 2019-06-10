package com.kefu.admin.dto;

import com.kefu.admin.entity.Faq;
import com.kefu.common.db.dto.PageDto;

import java.io.Serializable;

import lombok.Data;

/**
 * @author feng
 * @date 2019-06-03
 */
@Data
public class FaqPageDto extends PageDto<Faq> implements Serializable {
    private static final long serialVersionUID = -8559844664209546046L;

    /**
     * 标题
     */
    private String title;
}
