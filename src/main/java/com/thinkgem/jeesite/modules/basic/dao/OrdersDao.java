/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.Orders;

/**
 * 订单表DAO接口
 * @version 2018-01-10
 */
@MyBatisDao
public interface OrdersDao extends CrudDao<Orders> {



    public Integer getOrderDay();
    public Integer getOrderMonth();
    public Integer getOrderAll();

    public Integer getTotalFeeDay();
    public Integer getTotalFeeMonth();
    public Integer getTotalFeeAll();
	
}