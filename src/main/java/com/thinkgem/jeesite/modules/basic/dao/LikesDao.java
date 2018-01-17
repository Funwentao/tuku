/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.Likes;

/**
 * 点赞DAO接口
 * @author fandaz
 * @version 2018-01-17
 */
@MyBatisDao
public interface LikesDao extends CrudDao<Likes> {
	public Likes selectLikesByOpenidAndGalleryId(Likes likes);

	public void updateLikesByOpenidAndGalleryId(Likes likes);
}