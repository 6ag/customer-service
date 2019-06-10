package com.kefu.common.db.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kefu.common.vo.PageVo;
import com.kefu.common.vo.ResultVo;

import java.util.List;

/**
 * 分页工具类
 *
 * @author feng
 * @date 2019-05-23
 */
public class PageUtils {

    public static <T> PageVo<T> getPageVo(IPage<T> page) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setList(page.getRecords());
        pageVo.setTotalCount(page.getTotal());
        pageVo.setPageSize(page.getSize());
        pageVo.setCurrentPage(page.getCurrent());
        pageVo.setTotalPage(page.getPages());
        return pageVo;
    }

    public static <T> PageVo<T> getPageVo(IPage<T> page, List<T> list) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setList(list);
        pageVo.setTotalCount(page.getTotal());
        pageVo.setPageSize(page.getSize());
        pageVo.setCurrentPage(page.getCurrent());
        pageVo.setTotalPage(page.getPages());
        return pageVo;
    }

    public static <T> PageVo<T> getPageVo(List<T> list, long currentPage, long totalPage, long pageSize, long totalCount) {
        PageVo<T> pageVo = new PageVo<>();
        pageVo.setList(list);
        pageVo.setTotalCount(totalCount);
        pageVo.setPageSize(pageSize);
        pageVo.setCurrentPage(currentPage);
        pageVo.setTotalPage(totalPage);
        return pageVo;
    }

    public static <T> ResultVo getPageResult(IPage<T> page) {
        return ResultVo.success(getPageVo(page));
    }

}
