package com.leosanqing.index.service;

import com.leosanqing.index.pojo.Carousel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: leosanqing
 * @Date: 2020/5/6 上午10:30
 * @Package: com.leosanqing.index.service
 * @Description: 首页服务接口
 * @Version: 1.0
 */
@RequestMapping("carousel-api")
public interface CarouselService {
    /**
     * 查询所有需要播放的图
     *
     * @param isShow
     * @return
     */
    @PostMapping("queryAll")
    List<Carousel> queryAll(@RequestParam Integer isShow);
}
