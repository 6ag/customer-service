package com.kefu.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kefu.admin.dto.FaqPageDto;
import com.kefu.admin.entity.Faq;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FaqMapper extends BaseMapper<Faq> {

    IPage<Faq> selectPageList(FaqPageDto faqPageDto);
}
