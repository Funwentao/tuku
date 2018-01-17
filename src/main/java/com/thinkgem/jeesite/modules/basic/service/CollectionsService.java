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
import com.thinkgem.jeesite.modules.basic.entity.Collections;
import com.thinkgem.jeesite.modules.basic.dao.CollectionsDao;

/**
 * 收藏Service
 * @author fandaz
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class CollectionsService extends CrudService<CollectionsDao, Collections> {

	public Collections get(String id) {
		return super.get(id);
	}
	
	public List<Collections> findList(Collections collections) {
		return super.findList(collections);
	}
	
	public Page<Collections> findPage(Page<Collections> page, Collections collections) {
		return super.findPage(page, collections);
	}
	
	@Transactional(readOnly = false)
	public void save(Collections collections) {
		super.save(collections);
	}
	
	@Transactional(readOnly = false)
	public void delete(Collections collections) {
		super.delete(collections);
	}

	@Transactional(readOnly = false)
	public Collections selectCollectionsByOpenidAndGalleryId(Collections collection) {
		return dao.selectCollectionsByOpenidAndGalleryId(collection);
	}

	@Transactional(readOnly = false)
	public void updateCollectionsByOpenidAndGalleryId(Collections collection) {
		dao.updateCollectionsByOpenidAndGalleryId(collection);
	}

	@Transactional(readOnly = false)
	public List<Collections> selectCollectionsByOpenid(String openId){
		return dao.selectCollectionsByOpenid(openId);
	}


	@Transactional(readOnly = false)
	public Gallery selectgalleryBygalleryId(String galleryId){
		return dao.selectgalleryBygalleryId(galleryId);
	}
	
}