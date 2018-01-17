/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.basic.entity.Collection;
import com.thinkgem.jeesite.modules.basic.entity.Gallery;

import java.util.List;

/**
 * 收藏DAO接口
 * @author fandaz
 * @version 2018-01-17
 */
@MyBatisDao
public interface CollectionDao extends CrudDao<Collection> {
    public Collection selectCollectionsByOpenidAndGalleryId(Collection c);

    public void updateCollectionsByOpenidAndGalleryId(Collection c);

    public List<Collection> selectCollectionsByOpenid(String openId);

    public Gallery selectgalleryBygalleryId(String galleryId);
}