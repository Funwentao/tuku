package com.thinkgem.jeesite.modules.basic.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.modules.basic.entity.Services;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;
import com.thinkgem.jeesite.modules.basic.service.GalleryService;
import com.thinkgem.jeesite.modules.basic.service.OrdersService;
import com.thinkgem.jeesite.modules.basic.service.WeixinUserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by fandaz on 2018/1/20
 */
@Controller
@RequestMapping(value = "${adminPath}/basic/statistics")
public class StatisticsController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private WeixinUserInfoService weixinUserInfoService;

    @RequiresPermissions("basic:statistics:view")
    @RequestMapping(value = {"list", ""})
    public String list(HttpServletRequest request, HttpServletResponse response, Model model) {

        Integer newUserDay = weixinUserInfoService.getNewUserDay();
        Integer newUserMonth = weixinUserInfoService.getNewUserMonth();
        Integer newUserAll = weixinUserInfoService.getNewUserAll();

        Integer DayHits =galleryService.getDayHits();
        Integer MonthHits =galleryService.getMonthHits();
        Integer AllHits =galleryService.getAllHits();

        Integer OrderDay =ordersService.getOrderDay();
        Integer OrderMonth =ordersService.getOrderMonth();
        Integer OrderAll =ordersService.getOrderAll();

        Integer TotalFeeDay =ordersService.getTotalFeeDay();
        Integer TotalFeeMonth =ordersService.getTotalFeeMonth();
        Integer TotalFeeAll =ordersService.getTotalFeeAll();

        Integer UserBJGradeDay =weixinUserInfoService.getUserBJGradeDay();
        Integer UserBJGradeMonth =weixinUserInfoService.getUserBJGradeMonth();
        Integer UserBJGradeAll =weixinUserInfoService.getUserBJGradeAll();

        Integer UserHZGradeDay =weixinUserInfoService.getUserHZGradeDay();
        Integer UserHZGradeMonth =weixinUserInfoService.getUserHZGradeMonth();
        Integer UserHZGradeAll =weixinUserInfoService.getUserHZGradeAll();

        Integer UserZSGradeDay =weixinUserInfoService.getUserZSGradeDay();
        Integer UserZSGradeMonth =weixinUserInfoService.getUserZSGradeMonth();
        Integer UserZSGradeAll =weixinUserInfoService.getUserZSGradeAll();

        Integer UserZZGradeDay =weixinUserInfoService.getUserZZGradeDay();
        Integer UserZZGradeMonth =weixinUserInfoService.getUserZZGradeMonth();
        Integer UserZZGradeAll =weixinUserInfoService.getUserZZGradeAll();


        model.addAttribute("newUserDay",newUserDay);
        model.addAttribute("newUserMonth",newUserMonth);
        model.addAttribute("newUserAll",newUserAll);
        model.addAttribute("DayHits",DayHits);
        model.addAttribute("MonthHits",MonthHits);
        model.addAttribute("AllHits",AllHits);
        model.addAttribute("OrderDay",OrderDay);
        model.addAttribute("OrderMonth",OrderMonth);
        model.addAttribute("OrderAll",OrderAll);
        model.addAttribute("TotalFeeDay",TotalFeeDay);
        model.addAttribute("TotalFeeMonth",TotalFeeMonth);
        model.addAttribute("TotalFeeAll",TotalFeeAll);
        model.addAttribute("UserBJGradeDay",UserBJGradeDay);
        model.addAttribute("UserBJGradeMonth",UserBJGradeMonth);
        model.addAttribute("UserBJGradeAll",UserBJGradeAll);
        model.addAttribute("UserHZGradeDay",UserHZGradeDay);
        model.addAttribute("UserHZGradeMonth",UserHZGradeMonth);
        model.addAttribute("UserHZGradeAll",UserHZGradeAll);
        model.addAttribute("UserZSGradeDay",UserZSGradeDay);
        model.addAttribute("UserZSGradeMonth",UserZSGradeMonth);
        model.addAttribute("UserZSGradeAll",UserZSGradeAll);
        model.addAttribute("UserZZGradeDay",UserZZGradeDay);
        model.addAttribute("UserZZGradeMonth",UserZZGradeMonth);
        model.addAttribute("UserZZGradeAll",UserZZGradeAll);


        return "modules/basic/statisticsList";
    }
}
