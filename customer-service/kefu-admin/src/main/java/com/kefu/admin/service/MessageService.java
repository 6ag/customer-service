package com.kefu.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kefu.admin.dto.MessageListDto;
import com.kefu.admin.entity.Message;

import java.util.List;

/**
 * @author feng
 * @date 2019-06-08
 */
public interface MessageService extends IService<Message> {

    /**
     * 获取消息数据
     *
     * @param messageListDto 消息查询条件
     * @return
     */
    List<Message> findMessageList(MessageListDto messageListDto);
}
