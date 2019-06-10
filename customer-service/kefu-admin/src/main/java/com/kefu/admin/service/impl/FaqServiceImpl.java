package com.kefu.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.dto.FaqPageDto;
import com.kefu.admin.entity.Faq;
import com.kefu.admin.mapper.FaqMapper;
import com.kefu.admin.service.FaqService;
import com.kefu.common.db.util.PageUtils;
import com.kefu.common.vo.PageVo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

/**
 * @author feng
 * @date 2019-06-03
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class FaqServiceImpl extends ServiceImpl<FaqMapper, Faq> implements FaqService {

    @Override
    public PageVo<Faq> getFaqPageList(FaqPageDto faqPageDto) {
        IPage<Faq> page = baseMapper.selectPageList(faqPageDto);
        return PageUtils.getPageVo(page);
    }
}
