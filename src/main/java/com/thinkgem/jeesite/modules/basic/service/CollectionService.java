/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.service;

import java.util.List;

import com.thinkgem.jeesite.modules.basic.entity.Gallery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.basic.entity.Collection;
import com.thinkgem.jeesite.modules.basic.dao.CollectionDao;

/**
 * 收藏Service
 * @author fandaz
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class CollectionService extends CrudService<CollectionDao, Collection> {

	public Collection get(String id) {
		return super.get(id);
	}
	
	public List<Collection> findList(Collection collection) {
		return super.findList(collection);
	}
	
	public Page<Collection> findPage(Page<Collection> page, Collection collection) {
		return super.findPage(page, collection);
	}
	
	@Transactional(readOnly = false)
	public void save(Collection collection) {
		super.save(collection);
	}
	
	@Transactional(readOnly = false)
	public void delete(Collection collection) {
		super.delete(collection);
	}

	@Transactional(readOnly = false)
	public Collection selectCollectionsByOpenidAndGalleryId(Collection collection) {
		return dao.selectCollectionsByOpenidAndGalleryId(collection);
	}

	@Transactional(readOnly = false)
	public void updateCollectionsByOpenidAndGalleryId(Collection collection) {
		dao.updateCollectionsByOpenidAndGalleryId(collection);
	}

	@Transactional(readOnly = false)
	public List<Collection> selectCollectionsByOpenid(String openId){
		return dao.selectCollectionsByOpenid(openId);
	}


	@Transactional(readOnly = false)
	public Gallery selectgalleryBygalleryId(String galleryId){
		return dao.selectgalleryBygalleryId(galleryId);
	}
	
}