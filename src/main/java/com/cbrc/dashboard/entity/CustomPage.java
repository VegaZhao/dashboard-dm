package com.cbrc.dashboard.entity;

import com.baomidou.mybatisplus.plugins.Page;
import lombok.Data;

import java.util.List;

/**
 * 由此对象将page对象转换成json对象，传到前台处理
 * 由于jqgrid框架定义的page对象里面的字段和mybatisplus的不一样
 * 所以这个由这个中间对象来转换
 *
 * @param <T>
 */

@Data
public class CustomPage<T> {

    //当前页数
    private int page;

    //每页显示数量
    private int pagesize;

    //总条数
    private int total;

    //数据列表
    private List<T> records;

    //总页数
    private int totalPage;

    //排序字段
    private String orderByField;

    //是否升序
    private boolean isAsc;


    public CustomPage() {
    }

    public CustomPage(Page<T> page) {
        this.page = page.getCurrent();
        this.pagesize = page.getSize();
        this.total = (int) page.getTotal();
        this.records = page.getRecords();
        this.totalPage = (int) page.getPages();
        this.orderByField = page.getOrderByField();
        this.isAsc = page.isAsc();
    }

    /**
     * 支持普通Page
     *
     * @param page
     */
    public CustomPage(org.springframework.data.domain.Page<T> page) {
        this.page = page.getNumber();
        this.pagesize = page.getSize();
        this.total = (int) page.getTotalElements();
        this.records = page.getContent();
        this.totalPage = page.getTotalPages();
    }

    /**
     * 支持普通Page
     * 能够封装结果
     *
     * @param page
     * @param rows
     */
    public CustomPage(org.springframework.data.domain.Page page, List<T> rows) {
        this.page = page.getNumber();
        this.pagesize = page.getSize();
        this.total = (int) page.getTotalElements();
        this.records = rows;
        this.totalPage = page.getTotalPages();
    }

    public CustomPage(Page page, List<T> rows) {
        this.page = page.getCurrent();
        this.pagesize = page.getSize();
        this.total = (int) page.getTotal();
        this.records = rows;
        this.totalPage = (int) page.getPages();
        this.orderByField = page.getOrderByField();
        this.isAsc = page.isAsc();
    }

    public static <K> CustomPage<K> getSpringCustomPage(org.springframework.data.domain.Page page, List<K> rows) {
        return new CustomPage(page, rows);
    }

    public static <K> CustomPage<K> getCustomPage(Page page, List<K> rows) {
        return new CustomPage<>(page, rows);
    }
}
