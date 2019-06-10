package com.kefu.admin.controller.chat;

import com.alibaba.fastjson.JSON;
import com.kefu.admin.dto.MessageListDto;
import com.kefu.admin.service.MessageService;
import com.kefu.common.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息
 *
 * @author feng
 * @date 2019-06-08
 */
@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/list")
    public ResultVo getMessagePageList(@RequestBody MessageListDto messageListDto) {
        log.info("获取消息数据,{}", JSON.toJSONString(messageListDto));
        return ResultVo.success(messageService.findMessageList(messageListDto));
    }
}
