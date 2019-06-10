package com.kefu.admin.netty.handler;

import com.alibaba.fastjson.JSON;
import com.kefu.admin.dto.UserDto;
import com.kefu.admin.dto.UserPageDto;
import com.kefu.admin.entity.Conversation;
import com.kefu.admin.entity.Role;
import com.kefu.admin.entity.Team;
import com.kefu.admin.entity.User;
import com.kefu.admin.netty.protocol.request.LoginRequestPacket;
import com.kefu.admin.netty.protocol.response.LoginResponsePacket;
import com.kefu.admin.netty.utils.ChannelUtil;
import com.kefu.admin.service.ConversationService;
import com.kefu.admin.service.TeamService;
import com.kefu.admin.service.UserService;
import com.kefu.common.db.entity.TimeEntity;
import com.kefu.common.vo.PageVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 登录请求逻辑处理器
 *
 * @author feng
 * @date 2019-04-21
 */
@Slf4j
@ChannelHandler.Sharable
@Component
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    @Autowired
    private UserService userService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private TeamService teamService;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        // 处理登录请求数据包
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();

        // 用户名不能为空
        if (StringUtils.isEmpty(msg.getUsername())) {
            log.info("登录失败,username不能为空");
            loginResponsePacket.setSuccess(false);
            ctx.channel().writeAndFlush(loginResponsePacket);
            return;
        }

        User user = userService.findByUsername(msg.getUsername());
        // 如果没有用户，则新建
        if (user == null) {
            user = new User();
            user.setUsername(msg.getUsername());
            user.setTeamId(msg.getTeamId());

            // TODO:访客权限暂时写死，后面需要抽到配置文件里
            List<String> roleNameEns = new ArrayList<>();
            roleNameEns.add("ROLE_VISITOR");

            UserDto userDto = new UserDto();
            userDto.setUserInfo(user);
            userDto.setRoleNameEns(roleNameEns);
            // 新增用户
            userService.addUser(userDto);
        }

        // 是否是访客
        boolean isVisitor = false;
        if (user.getRoles() != null && user.getRoles().size() > 0) {
            for (Role role : user.getRoles()) {
                if ("ROLE_VISITOR".equals(role.getNameEn())) {
                    isVisitor = true;
                    break;
                }
            }
        }

        // 去查会话表，如果有会话则直接使用上次
        if (isVisitor) {
            List<Conversation> conversations = conversationService.selectListByUserId(user.getId());
            if (conversations != null && conversations.size() > 0) {
                // TODO:测试时间排序
                conversations.sort(Comparator.comparing(TimeEntity::getUpdatedAt));
                Conversation conversation = conversations.get(0);
                Integer contactUserId = 0;
                if (!conversation.getFromUserId().equals(user.getId())) {
                    contactUserId = conversation.getFromUserId();
                }
                if (!conversation.getToUserId().equals(user.getId())) {
                    contactUserId = conversation.getToUserId();
                }
                // 获取会话联系人
                User contact = userService.getById(contactUserId);
                loginResponsePacket.setContact(contact);
            } else {
                if (user.getTeamId() == null || user.getTeamId().equals(0)) {
                    log.info("登录失败,teamId不能为空");
                    loginResponsePacket.setSuccess(false);
                    ctx.channel().writeAndFlush(loginResponsePacket);
                    return;
                }

                UserPageDto userPageDto = new UserPageDto();
                userPageDto.setTeamId(user.getTeamId());
                List<User> userList = userService.findUserPageList(userPageDto).getList();
                if (userList == null || userList.size() == 0) {
                    log.info("登录失败,teamId={}团队没有客服", user.getTeamId());
                    loginResponsePacket.setSuccess(false);
                    ctx.channel().writeAndFlush(loginResponsePacket);
                    return;
                }

                // 过滤掉带有访客的用户
                userList = userList
                        .stream()
                        .filter(user1 -> user1
                                .getRoles()
                                .stream()
                                .anyMatch(role -> !"ROLE_VISITOR".equals(role.getNameEn())))
                        .collect(Collectors.toList());
                log.info("过滤访客userList={}", JSON.toJSONString(userList));

                // 分配客服 TODO:暂时写死
                User contact = userList.get(0);

                // 创建会话
                Conversation conversation = new Conversation();
                conversation.setFromUserId(user.getId());
                conversation.setToUserId(contact.getId());
                conversationService.save(conversation);

                loginResponsePacket.setContact(contact);
            }
        }

        log.info("登录成功,user={}", JSON.toJSONString(user));
        // 保存用户信息和channel对应关系
        ChannelUtil.bindUser(user, ctx.channel());

        loginResponsePacket.setUser(user);
        loginResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

}
