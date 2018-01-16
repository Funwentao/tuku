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

    //新增用户
    public int insertUser(WeixinUserInfo user);

    //查询是否有重复的openid
	
}