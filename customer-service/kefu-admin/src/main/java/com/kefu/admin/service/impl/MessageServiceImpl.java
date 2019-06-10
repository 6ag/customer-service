package com.kefu.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kefu.admin.dto.MessageListDto;
import com.kefu.admin.entity.Message;
import com.kefu.admin.mapper.MessageMapper;
import com.kefu.admin.service.MessageService;
import com.kefu.common.db.util.PageUtils;
import com.kefu.common.vo.PageVo;

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
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    /**
     * 获取消息分页数据
     *
     * @param messageListDto 消息分页查询条件
     * @return
     */
    @Override
    public List<Message> findMessageList(MessageListDto messageListDto) {
        return baseMapper.selectMessageList(messageListDto);
    }
}
