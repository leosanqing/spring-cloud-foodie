package com.leosanqing.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/4/23 下午2:32
 * @Package: com.leosanqing.pojo
 * @Description:
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagedGridResult {
    /**
     * 当前页数
     */
    private int page;
    /**
     * 总页数
     */
    private int total;
    /**
     * 总记录数
     */
    private long records;
    /**
     * 每行显示的内容
     */
    private List<?> rows;
}
