package com.kefu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kefu.admin.dto.FaqPageDto;
import com.kefu.admin.entity.Faq;
import com.kefu.common.vo.PageVo;

/**
 * @author feng
 * @date 2019-06-03
 */
public interface FaqService extends IService<Faq> {

    PageVo<Faq> getFaqPageList(FaqPageDto faqPageDto);

}
