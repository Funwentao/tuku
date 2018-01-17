/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.basic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.basic.entity.Likes;
import com.thinkgem.jeesite.modules.basic.dao.LikesDao;

/**
 * 点赞Service
 * @author fandaz
 * @version 2018-01-17
 */
@Service
@Transactional(readOnly = true)
public class LikesService extends CrudService<LikesDao, Likes> {

	public Likes get(String id) {
		return super.get(id);
	}
	
	public List<Likes> findList(Likes likes) {
		return super.findList(likes);
	}
	
	public Page<Likes> findPage(Page<Likes> page, Likes likes) {
		return super.findPage(page, likes);
	}
	
	@Transactional(readOnly = false)
	public void save(Likes likes) {
		super.save(likes);
	}
	
	@Transactional(readOnly = false)
	public void delete(Likes likes) {
		super.delete(likes);
	}

	@Transactional(readOnly = false)
	public Likes selectLikesByOpenidAndGalleryId(Likes likes) {
		return dao.selectLikesByOpenidAndGalleryId(likes);
	}


	@Transactional(readOnly = false)
	public void updateLikesByOpenidAndGalleryId(Likes likes) {
		 dao.updateLikesByOpenidAndGalleryId(likes);
	}
	
}