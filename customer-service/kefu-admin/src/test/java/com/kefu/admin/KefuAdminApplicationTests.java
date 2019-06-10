package com.kefu.admin;

import com.kefu.admin.mapper.RoleMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class KefuAdminApplicationTests {

	@Autowired
	private RoleMapper roleMapper;

	@Test
	public void test() {
		List<String> roleNameEns = new ArrayList<>();
		roleNameEns.add("ROLE_VISITOR");
		List<Integer> roleIds = roleMapper.getRoleIds(roleNameEns);
		System.out.println(Arrays.toString(roleIds.toArray()));
	}

}
