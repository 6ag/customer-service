package com.kefu.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.entity.Conversation;
import com.kefu.admin.mapper.ConversationMapper;
import com.kefu.admin.service.ConversationService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author feng
 * @date 2019-06-08
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class ConversationServiceImpl extends ServiceImpl<ConversationMapper, Conversation> implements ConversationService {

    /**
     * 查询会话列表
     *
     * @param userId 用户编号
     * @return
     */
    @Override
    public List<Conversation> selectListByUserId(Integer userId) {
        return baseMapper.selectListByUserId(userId);
    }
}
