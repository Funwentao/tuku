/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.WeixinUserInfo;

/**
 * 微信用户管理DAO接口
 * @version 2018-01-15
 */
@MyBatisDao
public interface WeixinUserInfoDao extends CrudDao<WeixinUserInfo> {

    //根据openId查询用户等级
    public String getGradeByUserId(String openId);

    //根据openId查询用户名称
    public String getNameByOpenId(String openId);

    //根据openId查询用户头像
    public String getHeadImgUrlByOpenId(String openId);


    //根据用户的openid查询用户详细信息
    public WeixinUserInfo getUserInfoByOpenId(String openId);

    public Integer getNewUserDay();
    public Integer getNewUserMonth();
    public Integer getNewUserAll();

    public Integer getUserHZGradeDay();
    public Integer getUserZSGradeDay();
    public Integer getUserBJGradeDay();
    public Integer getUserZZGradeDay();

    public Integer getUserHZGradeMonth();
    public Integer getUserZSGradeMonth();
    public Integer getUserBJGradeMonth();
    public Integer getUserZZGradeMonth();

    public Integer getUserHZGradeAll();
    public Integer getUserZSGradeAll();
    public Integer getUserBJGradeAll();
    public Integer getUserZZGradeAll();

    public String getUserName(String openId);








	
}