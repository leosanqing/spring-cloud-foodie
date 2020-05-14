package com.leosanqing.fastdfs.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: leosanqing
 * @Date: 2020/3/7 下午8:52
 * @Package: com.leosanqing.service
 * @Description: FastDFS服务接口
 */
@RequestMapping("fastdfs-api")
@FeignClient("foodie-fastdfs-service")
public interface FdfsService {
    @RequestMapping("upload")
    String upload(@RequestParam MultipartFile file, @RequestParam String fileExtendsName) throws IOException;
}
