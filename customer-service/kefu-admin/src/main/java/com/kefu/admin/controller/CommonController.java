package com.kefu.admin.controller;

import com.alibaba.fastjson.JSON;
import com.kefu.common.util.LocalJsonUtils;
import com.kefu.common.vo.ResultVo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 公共的一些接口
 *
 * @author feng
 * @date 2019-06-03
 */
@RestController
@RequestMapping("/common")
public class CommonController {

    /**
     * 获取省市数据
     *
     * @return
     */
    @GetMapping("/city")
    public ResultVo getProvinceCityList() {
        String cityJson = LocalJsonUtils.loadLocalJson("city.json");
        return ResultVo.success(JSON.parseArray(cityJson));
    }

    /**
     * 获取行业类型数据
     *
     * @return
     */
    @GetMapping("/industry")
    public ResultVo getIndustryList() {
        String cityJson = LocalJsonUtils.loadLocalJson("industry.json");
        return ResultVo.success(JSON.parseArray(cityJson));
    }
}
