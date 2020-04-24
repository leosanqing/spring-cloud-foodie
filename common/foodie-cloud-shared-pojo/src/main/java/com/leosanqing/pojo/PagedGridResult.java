package com.leosanqing.pojo;

import lombok.Data;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/4/23 下午2:32
 * @Package: com.leosanqing.pojo
 * @Description:
 * @Version: 1.0
 */
@Data
public class PagedGridResult {
    private int page;            // 当前页数
    private int total;            // 总页数
    private long records;        // 总记录数
    private List<?> rows;        // 每行显示的内容
}
