package com.metabubble.BWC.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.metabubble.BWC.common.R;
import com.metabubble.BWC.entity.User;
import com.metabubble.BWC.service.ConfigService;
import com.metabubble.BWC.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询（含查询全部）
     * @param page  分页码数
     * @param pageSize  分页条数
     * @param name  用户名称
     * @param tel   用户电话号码
     * @param grade 会员信息
     * @author leitianyu999
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name,String tel,String grade){
        //分页构造器
        Page pageSearch = new Page(page,pageSize);
        pageSearch.setSearchCount(true);
        //条件构造器
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name),User::getName,name);
        queryWrapper.like(StringUtils.isNotEmpty(tel),User::getTel,tel);
        queryWrapper.eq(StringUtils.isNotEmpty(grade),User::getGrade,grade);
        //添加排序条件
        queryWrapper.orderByDesc(User::getCreateTime);

        userService.page(pageSearch);


        return R.success(pageSearch);

    }
}
