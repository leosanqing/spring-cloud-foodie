package com.leosanqing.index.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leosanqing.index.mapper.CategoryMapper;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import com.leosanqing.index.service.CategoryService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/5/6 上午11:02
 * @Package: com.leosanqing.index.service.impl
 * @Description: 商品分类服务实现
 * @Version: 1.0
 */
@RestController
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        return lambdaQuery()
                .eq(Category::getType, 1)
                .list();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        return baseMapper.getSubCatList(rootCatId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        return baseMapper.getSixNewItemsLazy(rootCatId);
    }
}
