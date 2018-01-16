/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.GalleryCategory;

/**
 * 图库分类管理DAO接口
 * @version 2018-01-11
 */
@MyBatisDao
public interface GalleryCategoryDao extends CrudDao<GalleryCategory> {
	
}