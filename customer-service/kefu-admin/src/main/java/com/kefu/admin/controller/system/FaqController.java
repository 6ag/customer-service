package com.kefu.admin.controller.system;

import com.kefu.admin.dto.FaqPageDto;
import com.kefu.admin.entity.Faq;
import com.kefu.admin.service.FaqService;
import com.kefu.common.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 常见问题
 *
 * @author feng
 * @date 2019-06-03
 */
@RestController
@RequestMapping("/faq")
public class FaqController {

    @Autowired
    private FaqService faqService;

    @PostMapping
    public ResultVo addFaq(@RequestBody Faq faq) {
        faqService.save(faq);
        return ResultVo.success();
    }

    @DeleteMapping("/{faqId}")
    public ResultVo deleteFaq(@PathVariable Integer faqId) {
        faqService.removeById(faqId);
        return ResultVo.success();
    }

    @PutMapping
    public ResultVo updateFaq(@RequestBody Faq faq) {
        faqService.updateById(faq);
        return ResultVo.success();
    }

    @GetMapping("/{faqId}")
    public ResultVo getFaq(@PathVariable Integer faqId) {
        return ResultVo.success(faqService.getById(faqId));
    }

    @PostMapping("/list")
    public ResultVo getFaqList(@RequestBody FaqPageDto faqPageDto) {
        return ResultVo.success(faqService.getFaqPageList(faqPageDto));
    }

}
