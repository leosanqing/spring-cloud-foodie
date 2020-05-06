package com.leosanqing.index.service.impl;

import com.leosanqing.index.mapper.CategoryMapper;
import com.leosanqing.index.mapper.CategoryMapperCustom;
import com.leosanqing.index.pojo.Category;
import com.leosanqing.index.pojo.vo.CategoryVO;
import com.leosanqing.index.pojo.vo.NewItemsVO;
import com.leosanqing.index.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: rtliu
 * @Date: 2020/5/6 上午11:02
 * @Package: com.leosanqing.index.service.impl
 * @Description: 商品分类服务实现
 * @Version: 1.0
 */
@RestController
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> categories = categoryMapper.selectByExample(example);

        return categories;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatId) {
        List subCatList = categoryMapperCustom.getSubCatList(rootCatId);

        return subCatList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);
        return categoryMapperCustom.getSixNewItemsLazy(map);

    }
}
